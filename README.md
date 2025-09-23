# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Generar reporte de cobertura
mvn jacoco:report

# Ver reporte de cobertura
open target/site/jacoco/index.html

# Ejecutar test específico
mvn test -Dtest=CanchaServiceTest

# Ejecutar en modo debug
mvn test -Dmaven.surefire.debug