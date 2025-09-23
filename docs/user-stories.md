# Historias de Usuario - Sistema de Reservas Deportivas

## Historia de Usuario Principal

**Como** usuario del centro deportivo comunitario  
**Quiero** poder reservar una cancha deportiva en un horario específico  
**Para** asegurar que tendré disponibilidad para practicar mi deporte favorito

### Criterios de Aceptación

1. **Disponibilidad de Cancha**
   - El sistema debe mostrar solo las canchas disponibles en el horario solicitado
   - Una cancha no puede tener más de una reserva activa al mismo tiempo
   - El usuario debe poder ver los horarios disponibles de cada cancha

2. **Creación de Reserva**
   - El usuario debe proporcionar: nombre, cancha deseada, fecha y hora
   - La reserva se crea con estado "ACTIVA" por defecto
   - El sistema debe confirmar la creación de la reserva exitosamente

3. **Gestión de Reservas**
   - El usuario debe poder modificar la fecha/hora de su reserva (si la nueva fecha está disponible)
   - El usuario debe poder cancelar su reserva
   - El sistema debe actualizar la disponibilidad inmediatamente después de cualquier cambio

### Escenario BDD: Crear Reserva Exitosa

```gherkin
Given que existe una cancha "Cancha Principal" de tipo "FUTBOL" 
And la cancha tiene horarios disponibles [09:00, 11:00, 15:00]
And no hay reservas existentes para el 15/12/2024 a las 09:00
When el usuario "Juan Pérez" intenta crear una reserva para "Cancha Principal" el 15/12/2024 a las 09:00
Then la reserva se crea exitosamente
And el estado de la reserva es "ACTIVA"
And la cancha "Cancha Principal" no está disponible el 15/12/2024 a las 09:00
```

---

## Historia de Usuario Secundaria: Administración de Canchas

**Como** administrador del centro deportivo  
**Quiero** poder registrar y gestionar las canchas disponibles  
**Para** mantener actualizada la oferta de espacios deportivos

### Criterios de Aceptación

1. **Registro de Canchas**
   - Cada cancha debe tener: nombre único, tipo de deporte, horarios disponibles
   - Los tipos de deporte válidos son: FUTBOL, BASQUETBOL, TENIS, VOLEIBOL, PADDLE
   - Los horarios deben estar en formato válido (HH:mm)

2. **Consulta de Canchas**
   - Se debe poder buscar canchas por nombre
   - Se debe poder filtrar canchas por tipo de deporte
   - Se debe poder obtener la lista completa de canchas

3. **Validación de Datos**
   - Nombres de canchas no pueden ser vacíos
   - Debe existir al menos un horario disponible por cancha
   - Los horarios deben estar dentro del rango operativo (06:00 - 23:00)

### Escenario BDD: Registrar Cancha Exitosamente

```gherkin
Given que soy un administrador del sistema
And no existe una cancha con el nombre "Cancha Tenis Norte"
When registro una nueva cancha con nombre "Cancha Tenis Norte", tipo "TENIS" y horarios [08:00, 10:00, 16:00, 18:00]
Then la cancha se registra exitosamente en el sistema
And aparece en la lista de canchas disponibles
And está disponible para reservas en los horarios especificados
```

---

## Historia de Usuario: Prevención de Conflictos

**Como** usuario del sistema  
**Quiero** recibir notificaciones claras cuando una cancha no esté disponible  
**Para** poder elegir alternativas y planificar mejor mi reserva

### Criterios de Aceptación

1. **Detección de Conflictos**
   - El sistema debe verificar disponibilidad antes de crear cualquier reserva
   - Se debe mostrar un mensaje claro cuando una cancha esté ocupada
   - Se debe sugerir horarios alternativos disponibles (si los hay)

2. **Manejo de Errores**
   - Los errores deben ser informativos y user-friendly
   - Se debe distinguir entre "cancha no existe" y "cancha no disponible"
   - El sistema debe recuperarse graciosamente de errores

3. **Integridad de Datos**
   - No se debe permitir doble reserva bajo ninguna circunstancia
   - Los cambios de estado deben ser atómicos
   - Se debe mantener consistencia en la base de datos

### Escenario BDD: Intento de Reserva en Horario Ocupado

```gherkin
Given que existe una cancha "Cancha Futbol Sur"
And ya existe una reserva ACTIVA para "Juan Pérez" el 20/12/2024 a las 14:00
When el usuario "María García" intenta crear una reserva para la misma cancha el 20/12/2024 a las 14:00
Then el sistema rechaza la reserva
And muestra el mensaje "La cancha no está disponible en el horario solicitado"
And la cancha sigue reservada para "Juan Pérez"
And no se crea ninguna reserva para "María García"
```

---

## Métricas y KPIs

### Métricas de Negocio
- **Utilización de canchas**: Número de reservas por cancha por día
- **Tasa de cancelación**: Porcentaje de reservas canceladas
- **Horarios pico**: Franjas horarias con mayor demanda
- **Deportes populares**: Tipos de deporte más reservados

### Métricas Técnicas
- **Tiempo de respuesta**: Creación de reserva < 500ms
- **Disponibilidad**: Sistema disponible 99.9% del tiempo
- **Cobertura de tests**: Mínimo 85% de cobertura de líneas
- **Tasa de errores**: < 1% de transacciones fallidas

---

## Casos Límite y Consideraciones

### Casos Límite Identificados
1. **Reservas simultáneas**: Dos usuarios intentan reservar la misma cancha al mismo tiempo
2. **Modificación de reserva ocupada**: Cambiar a un horario ya ocupado
3. **Cancelación de reserva inexistente**: Intentar cancelar una reserva que no existe
4. **Horarios fuera de rango**: Reservar fuera del horario operativo (06:00-23:00)
5. **Reservas en el pasado**: Intentar crear reservas para fechas/horas pasadas

### Consideraciones de Diseño
- **Principio de responsabilidad única**: Cada clase tiene una responsabilidad clara
- **Inyección de dependencias**: Los servicios reciben sus dependencias por constructor
- **Manejo de excepciones**: Excepciones específicas para diferentes tipos de error
- **Validación temprana**: Validar parámetros en el punto de entrada
- **Estado inmutable**: Los enums proporcionan valores constantes y seguros

---

## Roadmap Futuro

### Fase 2 - Funcionalidades Avanzadas
- Notificaciones por email de confirmación/cancelación
- Reservas recurrentes (semanal, mensual)
- Sistema de lista de espera para horarios ocupados
- Integración con calendario externo (Google Calendar)

### Fase 3 - Características Premium
- Reservas con pago online
- Sistema de puntos de fidelidad
- Rankings de usuarios más activos
- API REST para aplicaciones móviles

### Fase 4 - Análitica y Reporting
- Dashboard administrativo con métricas
- Reportes de utilización por período
- Predicciones de demanda usando ML
- Optimización automática de horarios
