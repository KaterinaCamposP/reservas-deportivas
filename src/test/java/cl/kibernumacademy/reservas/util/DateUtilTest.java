package cl.kibernumacademy.reservas.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@DisplayName("Tests para DateUtil")
class DateUtilTest {
    
    @Test
    @DisplayName("Debería formatear fecha correctamente")
    void deberiaFormatearFechaCorrectamente() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 12, 15);
        
        // Act
        String resultado = DateUtil.formatearFecha(fecha);
        
        // Assert
        assertThat(resultado).isEqualTo("15/12/2024");
    }
    
    @Test
    @DisplayName("Debería retornar string vacío para fecha nula")
    void deberiaRetornarStringVacioParaFechaNula() {
        // Act
        String resultado = DateUtil.formatearFecha(null);
        
        // Assert
        assertThat(resultado).isEmpty();
    }
    
    @Test
    @DisplayName("Debería formatear hora correctamente")
    void deberiaFormatearHoraCorrectamente() {
        // Arrange
        LocalTime hora = LocalTime.of(14, 30);
        
        // Act
        String resultado = DateUtil.formatearHora(hora);
        
        // Assert
        assertThat(resultado).isEqualTo("14:30");
    }
    
    @Test
    @DisplayName("Debería formatear hora con ceros a la izquierda")
    void deberiaFormatearHoraConCerosIzquierda() {
        // Arrange
        LocalTime hora = LocalTime.of(9, 5);
        
        // Act
        String resultado = DateUtil.formatearHora(hora);
        
        // Assert
        assertThat(resultado).isEqualTo("09:05");
    }
    
    @Test
    @DisplayName("Debería retornar string vacío para hora nula")
    void deberiaRetornarStringVacioParaHoraNula() {
        // Act
        String resultado = DateUtil.formatearHora(null);
        
        // Assert
        assertThat(resultado).isEmpty();
    }
    
    @Test
    @DisplayName("Debería formatear fecha y hora correctamente")
    void deberiaFormatearFechaHoraCorrectamente() {
        // Arrange
        LocalDateTime fechaHora = LocalDateTime.of(2024, 12, 15, 16, 45);
        
        // Act
        String resultado = DateUtil.formatearFechaHora(fechaHora);
        
        // Assert
        assertThat(resultado).isEqualTo("15/12/2024 16:45");
    }
    
    @Test
    @DisplayName("Debería retornar string vacío para fecha-hora nula")
    void deberiaRetornarStringVacioParaFechaHoraNula() {
        // Act
        String resultado = DateUtil.formatearFechaHora(null);
        
        // Assert
        assertThat(resultado).isEmpty();
    }
    
    @Test
    @DisplayName("Debería detectar fecha futura correctamente")
    void deberiaDetectarFechaFuturaCorrectamente() {
        // Arrange
        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaPasada = LocalDateTime.now().minusDays(1);
        
        // Act & Assert
        assertThat(DateUtil.esFechaFutura(fechaFutura)).isTrue();
        assertThat(DateUtil.esFechaFutura(fechaPasada)).isFalse();
    }
    
    @Test
    @DisplayName("Debería retornar false para fecha-hora nula al verificar si es futura")
    void deberiaRetornarFalseParaFechaHoraNulaAlVerificarSiEsFutura() {
        // Act & Assert
        assertThat(DateUtil.esFechaFutura(null)).isFalse();
    }
    
    @Test
    @DisplayName("Debería detectar si fecha es hoy")
    void deberiaDetectarSiFechaEsHoy() {
        // Arrange
        LocalDate hoy = LocalDate.now();
        LocalDate ayer = LocalDate.now().minusDays(1);
        LocalDate mañana = LocalDate.now().plusDays(1);
        
        // Act & Assert
        assertThat(DateUtil.esHoy(hoy)).isTrue();
        assertThat(DateUtil.esHoy(ayer)).isFalse();
        assertThat(DateUtil.esHoy(mañana)).isFalse();
    }
    
    @Test
    @DisplayName("Debería retornar false para fecha nula al verificar si es hoy")
    void deberiaRetornarFalseParaFechaNulaAlVerificarSiEsHoy() {
        // Act & Assert
        assertThat(DateUtil.esHoy(null)).isFalse();
    }
}