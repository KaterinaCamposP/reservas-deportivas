Se evaluan técnicas de TDD, ATDD, diseño basado en principios SOLID, pruebas unitarias, uso de mocks, medición de cobertura y automatización de tests, desarrollando un módulo funcional de software con pruebas asociadas y buenas prácticas de la industria.

# Comparativa entre JUnit y TestNG

| Característica         | JUnit 5                          | TestNG                          |
|-----------------------|-----------------------------------|---------------------------------|
| **Anotaciones**       | @Test, @BeforeEach, @AfterEach    | @Test, @BeforeMethod, @AfterMethod |
| **Parametrización**   | @ParameterizedTest, @ValueSource  | @DataProvider                   |
| **Integración**       | Fácil con Maven/Gradle, IDEs      | Fácil con Maven/Gradle, IDEs    |
| **Reportes**          | Integración con JaCoCo, surefire  | Integración con surefire, plugins|
| **Flexibilidad**      | Muy flexible, modular, extensible | Flexible, permite dependencias entre tests |
| **Popularidad**       | Muy popular en proyectos Java      | Popular, especialmente en pruebas avanzadas |
| **Soporte**           | Comunidad amplia, documentación   | Comunidad activa, buena documentación |

**Elección recomendada:**

Para este proyecto se utiliza **JUnit 5** por su integración nativa con herramientas modernas (JaCoCo, Maven), su sintaxis clara y modularidad, además de ser el estándar en la industria para proyectos Java recientes. TestNG es útil para pruebas más complejas con dependencias entre tests, pero JUnit cubre todas las necesidades del módulo actual.

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

# 📌 Cumplimiento de Requerimientos

## 1️⃣ TDD y Pruebas Unitarias

**Archivos:**
- `ReservaTest.java`
- `CanchaTest.java`
- `ReservaServiceTest.java`
- `CanchaServiceTest.java`

---

## 2️⃣ Principios de Diseño (SOLID, YAGNI, KISS, DRY)

**Archivos:**
- `ReservaService.java`, `CanchaService.java`
- `Reserva.java`, `Cancha.java`

**Evidencia:**
- Uso de **Inversión de Dependencias** en los servicios.
- Métodos con **Responsabilidad Única**.
- Comentarios en métodos y constructores.
- Estructura modular y **reutilizable (DRY)**.

---

## 3️⃣ Uso de Mocks, Verificaciones y Capturas

**Archivos:**
- `ReservaServiceTest.java`
- `CanchaServiceTest.java`

**Ejemplos:**
```java
@Mock
private ReservaRepository reservaRepository;

verify(reservaRepository, times(1)).save(any(Reserva.class));
when(reservaRepository.findAll()).thenReturn(listaReservas);
```

## 4️⃣ Medición de cobertura (JaCoCo)

- **Archivo:** `index.html` (reporte generado)  
- **Evidencia:**  
  - `documentacion.md` explica cómo generar y revisar el reporte  
  - Objetivo de cobertura **mínimo 80%** documentado  

---

## 5️⃣ ATDD y aceptación

- **Archivo:** `user-stories.md`  
- **Evidencia:**  
  - Historias de usuario con criterios de aceptación claros  
  - Escenarios **BDD (Given, When, Then)** redactados  

---

## 6️⃣ Comparación entre frameworks de testing

- **Archivo:** `README.md` (tabla comparativa agregada)  
- **Evidencia:**  
  - Tabla con diferencias entre frameworks  
  - Justificación de la elección documentada  
