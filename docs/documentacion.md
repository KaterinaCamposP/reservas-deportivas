# Sistema de Reservas Deportivas

Sistema desarrollado para CodeWave que permite gestionar reservas en centros deportivos comunitarios.

## 🏆 Características Principales

- **Gestión de Canchas**: Registro y administración de canchas deportivas
- **Sistema de Reservas**: Crear, modificar y cancelar reservas
- **Prevención de Conflictos**: Verificación automática de disponibilidad
- **Métricas**: Cálculo de reservas por día y estadísticas
- **Arquitectura Limpia**: Implementado con principios SOLID y TDD

## 🛠 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal
- **JUnit 5**: Framework de testing unitario
- **Mockito**: Librería para mocking y verificaciones
- **JaCoCo**: Herramienta de medición de cobertura
- **AssertJ**: Assertions fluidas para tests más legibles
- **Maven**: Gestión de dependencias y build

## 📁 Estructura del Proyecto

```
src/
├── main/java/cl/kibernumacademy/reservas/
│   ├── model/          # Entidades del dominio
│   ├── service/        # Lógica de negocio
│   ├── repository/     # Interfaces de acceso a datos
│   ├── exception/      # Excepciones personalizadas
│   └── util/          # Utilidades comunes
└── test/java/cl/kibernumacademy/reservas/
    ├── model/         # Tests unitarios de entidades
    ├── service/       # Tests unitarios de servicios
    └── integration/   # Tests de integración
```

## 🚀 Cómo Ejecutar

### Prerrequisitos
- Java 17 o superior
- Maven 3.8+
- Visual Studio Code con Extension Pack for Java

### Comandos Básicos

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar todos los tests
mvn test

# Generar reporte de cobertura
mvn jacoco:report

# Ver reporte de cobertura (en target/site/jacoco/index.html) 
# start para Windows y open para macOS/Linux
cd target

start .\site\jacoco\index.html

open target/site/jacoco/index.html

```

## 📊 Métricas de Calidad

### Cobertura de Código
- **Objetivo**: Mínimo 80% de cobertura de líneas
- **Herramienta**: JaCoCo
- **Reporte**: Generado automáticamente en `target/site/jacoco/`

### Principios Aplicados
- **SOLID**: Single Responsibility, Dependency Inversion
- **TDD**: Test-Driven Development desde el inicio
- **Clean Code**: Nombres descriptivos, funciones pequeñas
- **DRY**: Don't Repeat Yourself en validaciones

## 🧪 Estrategia de Testing

### Tests Unitarios
- **Modelos**: Validación de constructores, getters, setters
- **Servicios**: Lógica de negocio con mocks
- **Utilidades**: Funciones de formateo y validación

### Tests de Integración
- **Flujos completos**: Desde registro de cancha hasta reserva
- **Casos de conflicto**: Validación de reglas de negocio
- **Manejo de errores**: Verificación de excepciones

## 🔧 Configuración de Desarrollo

### VS Code Settings
```json
{
    "java.configuration.updateBuildConfiguration": "interactive",
    "java.test.report.position": "sideView",
    "files.exclude": {
        "**/target": true
    }
}
```

### Maven Plugins Configurados
- **maven-compiler-plugin**: Compilación con Java 17
- **maven-surefire-plugin**: Ejecución de tests
- **jacoco-maven-plugin**: Medición de cobertura

## 📋 Casos de Uso Principales

1. **Registrar Cancha**
   ```java
   CanchaService.registrarCancha(nombre, tipoDeporte, horarios)
   ```

2. **Crear Reserva**
   ```java
   ReservaService.crearReserva(cancha, fechaHora, usuario)
   ```

3. **Verificar Disponibilidad**
   ```java
   ReservaService.esCanchaDisponible(cancha, fechaHora)
   ```

4. **Cancelar Reserva**
   ```java
   ReservaService.cancelarReserva(reservaId)
   ```

## 🐛 Manejo de Errores

### Excepciones Personalizadas
- `CanchaNoDisponibleException`: Cancha ocupada en el horario
- `ReservaNoEncontradaException`: Reserva inexistente
- `ReservaException`: Excepción base para el dominio
