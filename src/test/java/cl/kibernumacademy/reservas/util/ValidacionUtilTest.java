package cl.kibernumacademy.reservas.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("Tests para ValidationUtil")
class ValidationUtilTest {
    
    @Test
    @DisplayName("Debería validar string no vacío correctamente")
    void deberiaValidarStringNoVacioCorrectamente() {
        // Arrange
        String valorValido = "Texto válido";
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarStringNoVacio(valorValido, "Campo"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para string nulo")
    void deberiaLanzarExcepcionParaStringNulo() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarStringNoVacio(null, "Campo"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Campo no puede ser nulo o vacío");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para string vacío")
    void deberiaLanzarExcepcionParaStringVacio() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarStringNoVacio("", "Campo"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Campo no puede ser nulo o vacío");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para string con solo espacios")
    void deberiaLanzarExcepcionParaStringConSoloEspacios() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarStringNoVacio("   ", "Campo"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Campo no puede ser nulo o vacío");
    }
    
    @Test
    @DisplayName("Debería validar objeto no nulo correctamente")
    void deberiaValidarObjetoNoNuloCorrectamente() {
        // Arrange
        Object objeto = new Object();
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarNoNulo(objeto, "Objeto"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para objeto nulo")
    void deberiaLanzarExcepcionParaObjetoNulo() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarNoNulo(null, "Objeto"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Objeto no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería validar lista no vacía correctamente")
    void deberiaValidarListaNoVaciaCorrectamente() {
        // Arrange
        List<String> lista = Arrays.asList("elemento1", "elemento2");
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarListaNoVacia(lista, "Lista"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para lista nula")
    void deberiaLanzarExcepcionParaListaNula() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarListaNoVacia(null, "Lista"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Lista no puede ser nulo o vacío");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para lista vacía")
    void deberiaLanzarExcepcionParaListaVacia() {
        // Arrange
        List<String> listaVacia = Collections.emptyList();
        
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarListaNoVacia(listaVacia, "Lista"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Lista no puede ser nulo o vacío");
    }
    
    @Test
    @DisplayName("Debería validar fecha futura correctamente")
    void deberiaValidarFechaFuturaCorrectamente() {
        // Arrange
        LocalDateTime fechaFutura = LocalDateTime.now().plusHours(1);
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarFechaFutura(fechaFutura, "Fecha"));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para fecha pasada")
    void deberiaLanzarExcepcionParaFechaPasada() {
        // Arrange
        LocalDateTime fechaPasada = LocalDateTime.now().minusHours(1);
        
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarFechaFutura(fechaPasada, "Fecha"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Fecha debe ser una fecha futura");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para fecha nula al validar fecha futura")
    void deberiaLanzarExcepcionParaFechaNulaAlValidarFechaFutura() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarFechaFutura(null, "Fecha"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Fecha no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería validar horario permitido correctamente")
    void deberiaValidarHorarioPermitidoCorrectamente() {
        // Arrange
        LocalTime horarioValido1 = LocalTime.of(6, 0);  // Límite inferior
        LocalTime horarioValido2 = LocalTime.of(12, 0); // Medio día
        LocalTime horarioValido3 = LocalTime.of(23, 0); // Límite superior
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarHorarioPermitido(horarioValido1));
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarHorarioPermitido(horarioValido2));
        assertThatNoException()
            .isThrownBy(() -> ValidationUtil.validarHorarioPermitido(horarioValido3));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para horario muy temprano")
    void deberiaLanzarExcepcionParaHorarioMuyTemprano() {
        // Arrange
        LocalTime horarioMuyTemprano = LocalTime.of(5, 59);
        
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarHorarioPermitido(horarioMuyTemprano))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("El horario debe estar entre 06:00 y 23:00");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para horario muy tardío")
    void deberiaLanzarExcepcionParaHorarioMuyTardio() {
        // Arrange
        LocalTime horarioMuyTardio = LocalTime.of(23, 1);
        
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarHorarioPermitido(horarioMuyTardio))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("El horario debe estar entre 06:00 y 23:00");
    }
    
    @Test
    @DisplayName("Debería lanzar excepción para horario nulo")
    void deberiaLanzarExcepcionParaHorarioNulo() {
        // Act & Assert
        assertThatThrownBy(() -> 
            ValidationUtil.validarHorarioPermitido(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Hora no puede ser nulo");
    }
    
    @Test
    @DisplayName("Debería validar múltiples campos en secuencia")
    void deberiaValidarMultiplesCamposEnSecuencia() {
        // Arrange
        String nombre = "Cancha Principal";
        List<LocalTime> horarios = Arrays.asList(LocalTime.of(10, 0), LocalTime.of(14, 0));
        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1);
        
        // Act & Assert - No debe lanzar excepción
        assertThatNoException().isThrownBy(() -> {
            ValidationUtil.validarStringNoVacio(nombre, "Nombre");
            ValidationUtil.validarListaNoVacia(horarios, "Horarios");
            ValidationUtil.validarFechaFutura(fechaFutura, "Fecha de reserva");
            for (LocalTime horario : horarios) {
                ValidationUtil.validarHorarioPermitido(horario);
            }
        });
    }
}