package cl.kibernumacademy.reservas.service;

import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.Reserva;
import cl.kibernumacademy.reservas.model.TipoDeporte;
import cl.kibernumacademy.reservas.model.EstadoReserva;
import cl.kibernumacademy.reservas.repository.ReservaRepository;
import cl.kibernumacademy.reservas.exception.CanchaNoDisponibleException;
import cl.kibernumacademy.reservas.exception.ReservaNoEncontradaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ReservaService")
class ReservaServiceTest {
    
    @Mock
    private ReservaRepository reservaRepository;
    
    private ReservaService reservaService;
    private Cancha cancha;
    private LocalDateTime fechaHora;
    
    @BeforeEach
    void setUp() {
        reservaService = new ReservaService(reservaRepository);
        cancha = new Cancha("Cancha Test", TipoDeporte.FUTBOL, 
            Arrays.asList(LocalTime.of(10, 0)));
        fechaHora = LocalDateTime.of(2024, 12, 15, 10, 0);
    }
    
    @Test
    @DisplayName("Debería crear reserva cuando cancha está disponible")
    void deberiaCrearReservaCuandoCanchaEstaDisponible() {
        // Arrange
        String nombreUsuario = "Juan Pérez";
        Reserva reservaEsperada = new Reserva(cancha, fechaHora, nombreUsuario);
        
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList());
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaEsperada);
        
        // Act
        Reserva resultado = reservaService.crearReserva(cancha, fechaHora, nombreUsuario);
        
        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getCancha()).isEqualTo(cancha);
        assertThat(resultado.getFechaHora()).isEqualTo(fechaHora);
        assertThat(resultado.getNombreUsuario()).isEqualTo(nombreUsuario);
        
        // Verify
        verify(reservaRepository, times(1)).findByCanchaAndFechaHora(cancha, fechaHora);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción cuando cancha no está disponible")
    void deberiaLanzarExcepcionCuandoCanchaNoEstaDisponible() {
        // Arrange
        String nombreUsuario = "Juan Pérez";
        Reserva reservaExistente = new Reserva(cancha, fechaHora, "Otro Usuario");
        
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList(reservaExistente));
        
        // Act & Assert
        assertThatThrownBy(() -> 
            reservaService.crearReserva(cancha, fechaHora, nombreUsuario))
            .isInstanceOf(CanchaNoDisponibleException.class)
            .hasMessageContaining("no está disponible");
        
        // Verify
        verify(reservaRepository, times(1)).findByCanchaAndFechaHora(cancha, fechaHora);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
    
    @Test
    @DisplayName("Debería modificar reserva existente")
    void deberiaModificarReservaExistente() {
        // Arrange
        Long id = 1L;
        LocalDateTime nuevaFechaHora = LocalDateTime.of(2024, 12, 16, 14, 0);
        Reserva reservaExistente = new Reserva(cancha, fechaHora, "Juan Pérez");
        reservaExistente.setId(id);
        
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaExistente));
        when(reservaRepository.save(reservaExistente)).thenReturn(reservaExistente);
        
        // Act
        Reserva resultado = reservaService.modificarReserva(id, nuevaFechaHora);
        
        // Assert
        assertThat(resultado.getFechaHora()).isEqualTo(nuevaFechaHora);
        
        // Verify
        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, times(1)).save(reservaExistente);
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al modificar reserva inexistente")
    void deberiaLanzarExcepcionAlModificarReservaInexistente() {
        // Arrange
        Long id = 999L;
        LocalDateTime nuevaFechaHora = LocalDateTime.of(2024, 12, 16, 14, 0);
        
        when(reservaRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> 
            reservaService.modificarReserva(id, nuevaFechaHora))
            .isInstanceOf(ReservaNoEncontradaException.class)
            .hasMessageContaining("No se encontró la reserva con ID: " + id);
        
        // Verify
        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
    
    @Test
    @DisplayName("Debería cancelar reserva existente")
    void deberiaCancelarReservaExistente() {
        // Arrange
        Long id = 1L;
        Reserva reservaExistente = new Reserva(cancha, fechaHora, "Juan Pérez");
        reservaExistente.setId(id);
        
        when(reservaRepository.findById(id)).thenReturn(Optional.of(reservaExistente));
        
        // Act
        reservaService.cancelarReserva(id);
        
        // Assert
        assertThat(reservaExistente.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
        
        // Verify
        verify(reservaRepository, times(1)).findById(id);
        verify(reservaRepository, times(1)).save(reservaExistente);
    }
    
    @Test
    @DisplayName("Debería verificar disponibilidad de cancha correctamente")
    void deberiaVerificarDisponibilidadCanchaCorrectamente() {
        // Arrange - cancha disponible
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList());
        
        // Act & Assert
        assertThat(reservaService.esCanchaDisponible(cancha, fechaHora)).isTrue();
        
        // Arrange - cancha ocupada
        Reserva reservaExistente = new Reserva(cancha, fechaHora, "Otro Usuario");
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList(reservaExistente));
        
        // Act & Assert
        assertThat(reservaService.esCanchaDisponible(cancha, fechaHora)).isFalse();
    }
    
    @Test
    @DisplayName("Debería calcular reservas por día correctamente")
    void deberiaCalcularReservasPorDiaCorrectamente() {
        // Arrange
        LocalDate fecha = LocalDate.of(2024, 12, 15);
        List<Reserva> reservasDelDia = Arrays.asList(
            new Reserva(), new Reserva(), new Reserva()
        );
        
        when(reservaRepository.findByFecha(fecha)).thenReturn(reservasDelDia);
        
        // Act
        long resultado = reservaService.calcularReservasPorDia(fecha);
        
        // Assert
        assertThat(resultado).isEqualTo(3);
        
        // Verify
        verify(reservaRepository, times(1)).findByFecha(fecha);
    }
    
    @Test
    @DisplayName("Debería obtener reservas activas")
    void deberiaObtenerReservasActivas() {
        // Arrange
        List<Reserva> reservasActivas = Arrays.asList(
            new Reserva(cancha, fechaHora, "Usuario1"),
            new Reserva(cancha, fechaHora.plusHours(1), "Usuario2")
        );
        
        when(reservaRepository.findByEstado(EstadoReserva.ACTIVA))
            .thenReturn(reservasActivas);
        
        // Act
        List<Reserva> resultado = reservaService.obtenerReservasActivas();
        
        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).containsExactlyElementsOf(reservasActivas);
        
        // Verify
        verify(reservaRepository, times(1)).findByEstado(EstadoReserva.ACTIVA);
    }
}