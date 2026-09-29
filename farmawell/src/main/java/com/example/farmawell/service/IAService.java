package com.example.farmawell.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IAService {

    private final CampañaService campañaService;
    private final RecomendacionService recomendacionService;
    private final DashboardService dashboardService;
    private final ProductoService productoService;

    @Value("${anthropic.api.key}")
    private String apiKey; //api key de anthropic 

    private static final String MODEL = "claude-sonnet-4-6";
    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final int MAX_TURNOS = 6;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final RestClient restClient = RestClient.create();

    private static final String SYSTEM_PROMPT = """
            Eres el asistente de datos de Farmawell, una farmacia. Respondes preguntas sobre ventas, \
            clientes y productos usando UNICAMENTE las herramientas disponibles, nunca inventes cifras, \
            nombres de clientes ni códigos de producto.
            Si necesitas el código de un producto y el usuario dio un nombre (ej: "Ensure"), usa primero \
            la herramienta buscar_producto antes de usar cualquier otra que requiera un código.
            Responde en español, de forma clara y directa, como si hablaras con el dueño del negocio.
            Cuando el usuario pida una lista de clientes para contactar, incluye nombre y teléfono de cada uno.
            Si una herramienta no devuelve resultados, dilo explícitamente en vez de inventar datos.
            """;

    private static final String TOOLS_JSON = """
            [
              {
                "name": "clientes_vip",
                "description": "Devuelve los clientes actualmente segmentados como VIP: alta frecuencia de compra, alto gasto y compra reciente.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "clientes_en_riesgo",
                "description": "Devuelve los clientes que llevan tiempo sin comprar y están en riesgo de perderse.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "clientes_recuperables",
                "description": "Devuelve clientes que dejaron de comprar hace tiempo pero antes eran frecuentes, candidatos a campaña de recuperación.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "buscar_producto",
                "description": "Busca productos por nombre o parte del nombre (ej: 'Ensure', 'Pediasure') y devuelve código, descripción y marca. Úsala siempre antes de otra herramienta que pida un código de producto, si el usuario dio un nombre.",
                "input_schema": {
                  "type": "object",
                  "properties": {
                    "texto": { "type": "string", "description": "Nombre o parte del nombre del producto" }
                  },
                  "required": ["texto"]
                }
              },
              {
                "name": "productos_afines",
                "description": "Dado el código de un producto, devuelve los productos que se compran junto a él con más frecuencia en la misma factura.",
                "input_schema": {
                  "type": "object",
                  "properties": {
                    "codigo": { "type": "string", "description": "Código del producto (código TNS)" },
                    "limite": { "type": "integer", "description": "Cantidad máxima a devolver, por defecto 10" }
                  },
                  "required": ["codigo"]
                }
              },
              {
                "name": "clientes_interesados_producto",
                "description": "Devuelve los clientes que han comprado un producto específico, dado su código.",
                "input_schema": {
                  "type": "object",
                  "properties": {
                    "codigo": { "type": "string", "description": "Código del producto (código TNS)" }
                  },
                  "required": ["codigo"]
                }
              },
              {
                "name": "ventas_por_mes",
                "description": "Total vendido y cantidad de facturas por cada mes.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "ventas_por_sede",
                "description": "Total vendido agrupado por sede de la farmacia.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "ventas_por_ciudad",
                "description": "Total vendido agrupado por ciudad del cliente.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "ventas_por_categoria",
                "description": "Total vendido agrupado por categoría/grupo de artículo del producto.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "ventas_por_marca",
                "description": "Total vendido agrupado por marca del producto.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "ticket_promedio_mensual",
                "description": "Valor promedio de factura (ticket promedio) por cada mes.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "clientes_nuevos_por_mes",
                "description": "Cantidad de clientes que hicieron su primera compra en cada mes.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "clientes_perdidos_por_mes",
                "description": "Agrupado por mes de última compra, cantidad de clientes que no han vuelto a comprar desde entonces.",
                "input_schema": { "type": "object", "properties": {} }
              },
              {
                "name": "resumen_general",
                "description": "Resumen general: cantidad total de clientes, productos, ventas y detalles de venta registrados.",
                "input_schema": { "type": "object", "properties": {} }
              }
            ]
            """;

    private static final JsonNode TOOLS = parseTools();

    private static JsonNode parseTools() {
        try {
            return MAPPER.readTree(TOOLS_JSON);
        } catch (Exception e) {
            throw new IllegalStateException("Error parseando definición de herramientas de IA", e);
        }
    }

    public String consultar(String pregunta) {

        ArrayNode messages = MAPPER.createArrayNode();
        messages.add(mensajeTexto("user", pregunta));

        for (int turno = 0; turno < MAX_TURNOS; turno++) {

            JsonNode respuesta = llamarClaude(messages);

            String stopReason = respuesta.path("stop_reason").asText();
            JsonNode content = respuesta.path("content");

            if (!"tool_use".equals(stopReason)) {
                return extraerTexto(content);
            }

            ObjectNode mensajeAsistente = MAPPER.createObjectNode();
            mensajeAsistente.put("role", "assistant");
            mensajeAsistente.set("content", content);
            messages.add(mensajeAsistente);

            ArrayNode resultados = MAPPER.createArrayNode();

            for (JsonNode bloque : content) {
                if ("tool_use".equals(bloque.path("type").asText())) {

                    String nombreHerramienta = bloque.path("name").asText();
                    String toolUseId = bloque.path("id").asText();
                    JsonNode input = bloque.path("input");

                    Object resultado = ejecutarHerramienta(nombreHerramienta, input);

                    ObjectNode toolResult = MAPPER.createObjectNode();
                    toolResult.put("type", "tool_result");
                    toolResult.put("tool_use_id", toolUseId);
                    toolResult.put("content", serializar(resultado));

                    resultados.add(toolResult);
                }
            }

            ObjectNode mensajeUsuario = MAPPER.createObjectNode();
            mensajeUsuario.put("role", "user");
            mensajeUsuario.set("content", resultados);
            messages.add(mensajeUsuario);
        }

        return "No pude completar la consulta en el número de pasos permitidos, intenta reformularla.";
    }

    private JsonNode llamarClaude(ArrayNode messages) {

        ObjectNode body = MAPPER.createObjectNode();
        body.put("model", MODEL);
        body.put("max_tokens", 1500);
        body.put("system", SYSTEM_PROMPT);
        body.set("tools", TOOLS);
        body.set("messages", messages);

        String respuestaTexto = restClient.post()
                .uri(API_URL)
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("Content-Type", "application/json")
                .body(body.toString())
                .retrieve()
                .body(String.class);

        try {
            return MAPPER.readTree(respuestaTexto);
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo respuesta de Claude", e);
        }
    }

    private Object ejecutarHerramienta(String nombre, JsonNode input) {

        return switch (nombre) {
            case "clientes_vip" -> campañaService.obtenerVip();
            case "clientes_en_riesgo" -> campañaService.obtenerEnRiesgo();
            case "clientes_recuperables" -> campañaService.obtenerRecuperables();
            case "buscar_producto" -> productoService.buscarPorNombre(input.path("texto").asText());
            case "productos_afines" -> recomendacionService.obtenerAfinidad(
                    input.path("codigo").asText(),
                    input.path("limite").asInt(10));
            case "clientes_interesados_producto" ->
                campañaService.obtenerPorProducto(input.path("codigo").asText());
            case "ventas_por_mes" -> dashboardService.obtenerVentasPorMes();
            case "ventas_por_sede" -> dashboardService.obtenerVentasPorSede();
            case "ventas_por_ciudad" -> dashboardService.obtenerVentasPorCiudad();
            case "ventas_por_categoria" -> dashboardService.obtenerVentasPorCategoria();
            case "ventas_por_marca" -> dashboardService.obtenerVentasPorMarca();
            case "ticket_promedio_mensual" -> dashboardService.obtenerTicketPromedioMensual();
            case "clientes_nuevos_por_mes" -> dashboardService.obtenerClientesNuevosPorMes();
            case "clientes_perdidos_por_mes" -> dashboardService.obtenerClientesPerdidosPorMes();
            case "resumen_general" -> dashboardService.obtenerResumen();
            default -> Map.of("error", "Herramienta no reconocida: " + nombre);
        };
    }

    private ObjectNode mensajeTexto(String rol, String texto) {
        ObjectNode mensaje = MAPPER.createObjectNode();
        mensaje.put("role", rol);
        mensaje.put("content", texto);
        return mensaje;
    }

    private String extraerTexto(JsonNode content) {

        StringBuilder texto = new StringBuilder();

        for (JsonNode bloque : content) {
            if ("text".equals(bloque.path("type").asText())) {
                texto.append(bloque.path("text").asText());
            }
        }

        return texto.toString();
    }

    private String serializar(Object resultado) {
        try {
            return MAPPER.writeValueAsString(resultado);
        } catch (Exception e) {
            return "Error serializando resultado: " + e.getMessage();
        }
    }
}
