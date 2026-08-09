# Farmawell

Sistema de gestión y fidelización de clientes para una farmacia.

## Funcionalidades

- Importación de ventas desde Excel
- Gestión de clientes
- Gestión de productos
- Historial de compras
- Segmentación de clientes
- Identificación de clientes frecuentes
- Identificación de clientes en riesgo
- Identificación de clientes VIP
- Dashboard de clientes

## Tecnologías

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache POI
- Maven

## Estructura

- `entity/` → Entidades de la base de datos
- `repository/` → Acceso a datos
- `service/` → Lógica de negocio
- `controller/` → Endpoints REST
- `importer/` → Importación de datos desde Excel
- `dto/` → Objetos de transferencia
- `projection/` → Proyecciones para consultas
- `segment/` → Lógica de segmentación

## Importación

Los archivos Excel con información de clientes y ventas son datos privados y no se incluyen en el repositorio.

La importación se realiza mediante:

`POST /importacion`
