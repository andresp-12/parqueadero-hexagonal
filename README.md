# Control de parqueadero (Quarkus + Arquitectura Hexagonal)
**Problema:** un parqueadero no controla los cupos ni cobra bien las horas de permanencia.
**Usuarios:** el operario/cajero. **Datos:** cupos (`parking_spots`) y tickets (`parking_tickets`).

## Estructura (paquete `usta.parking`)
- `domain/`: `model`, `service` (FeeCalculator: regla de cobro), `exception`.
- `application/`: `port/in` (casos de uso), `port/out` (lo que el sistema necesita), `usecase` (implementaciones), `exception`.
- `infrastructure/adapter/in/web/`: `resource` (vistas Qute + Bootstrap), `report` (PDF con OpenPDF).
- `infrastructure/adapter/out/persistence/`: `entity`, `mapper`, `repository` (Panache), `adapter` (implementa los puertos).
- `infrastructure/config/`: conecta los casos de uso con CDI.
- Dependencias: infrastructure -> application -> domain.

## Reglas de negocio
Placa de 5 a 7 letras/números; sin ingresos duplicados; cupo asignado automáticamente; cobro por hora o fracción (mínimo 1 hora): carro 3.000 COP/h, moto 1.500 COP/h.

## Ejecutar
1. Iniciar MySQL en XAMPP (usa MariaDB; usuario `root`, sin clave).
2. IntelliJ IDEA: File > Open > `pom.xml` > Open as Project (JDK 17+); ejecutar **Quarkus Dev**, o `./mvnw quarkus:dev`.
3. App: http://localhost:8080 · Swagger: http://localhost:8080/q/swagger-ui · OpenAPI: /q/openapi
4. Opcional: `database/parqueadero.sql` crea tablas y datos desde phpMyAdmin.
