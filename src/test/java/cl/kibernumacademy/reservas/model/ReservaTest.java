package cl.kibernumacademy.reservas.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@DisplayName("Tests para la clase Reserva")
class ReservaTest {
    
    private Cancha cancha;
    private Reserva reserva;
    private LocalDateTime fechaHora;
    
    @BeforeEach
    void setUp() {
        cancha = new Cancha("Cancha Test", TipoDeporte.FUTBOL, 
            Arrays.asList(LocalTime.of(10, 0)));
        fechaHora = LocalDateTime.of(2024, 12, 15, 10, 0);
        reserva = new Reserva(cancha, fechaHora, "Juan Pérez");
    }
    
    @Test
    @DisplayName("Debería crear reserva con estado ACTIVA por defecto")
    void deberiaCrearReservaConEstadoActivaPorDefecto() {
        // Assert
        assertThat(reserva.getCancha()).isEqualTo(cancha);
        assertThat(reserva.getFechaHora()).isEqualTo(fechaHora);
        assertThat(reserva.getNombreUsuario()).isEqualTo("Juan Pérez");
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.ACTIVA);
    }
    
    @Test
    @DisplayName("Debería crear reserva vacía con estado PENDIENTE")
    void deberiaCrearReservaVaciaConEstadoPendiente() {
        // Arrange & Act
        Reserva reservaVacia = new Reserva();
        
        // Assert
        assertThat(reservaVacia.getEstado()).isEqualTo(EstadoReserva.PENDIENTE);
        assertThat(reservaVacia.getCancha()).isNull();
        assertThat(reservaVacia.getFechaHora()).isNull();
        assertThat(reservaVacia.getNombreUsuario()).isNull();
    }
    
    @Test
    @DisplayName("Debería cancelar reserva correctamente")
    void deberiaCancelarReservaCorrectamente() {
        // Act
        reserva.cancelar();
        
        // Assert
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
    }
    
    @Test
    @DisplayName("Debería completar reserva correctamente")
    void deberiaCompletarReservaCorrectamente() {
        // Act
        reserva.completar();
        
        // Assert
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.COMPLETADA);
    }
    
    @Test
    @DisplayName("Debería permitir cambios de estado múltiples")
    void deberiaPermitirCambiosEstadoMultiples() {
        // Act & Assert
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.ACTIVA);
        
        reserva.cancelar();
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
        
        // Cambiar manualmente a completada (caso edge)
        reserva.setEstado(EstadoReserva.COMPLETADA);
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.COMPLETADA);
    }
    
    @Test
    @DisplayName("Debería manejar todos los getters y setters")
    void deberiaManejarTodosLosGettersYSetters() {
        // Arrange
        Cancha nuevaCancha = new Cancha("Nueva Cancha", TipoDeporte.TENIS, Arrays.asList());
        LocalDateTime nuevaFecha = LocalDateTime.of(2024, 12, 20, 14, 0);
        
        // Act
        reserva.setId(123L);
        reserva.setCancha(nuevaCancha);
        reserva.setFechaHora(nuevaFecha);
        reserva.setNombreUsuario("María García");
        reserva.setEstado(EstadoReserva.PENDIENTE);
        
        // Assert
        assertThat(reserva.getId()).isEqualTo(123L);
        assertThat(reserva.getCancha()).isEqualTo(nuevaCancha);
        assertThat(reserva.getFechaHora()).isEqualTo(nuevaFecha);
        assertThat(reserva.getNombreUsuario()).isEqualTo("María García");
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.PENDIENTE);
    }
}