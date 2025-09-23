package cl.kibernumacademy.reservas.integration;

import cl.kibernumacademy.reservas.model.*;
import cl.kibernumacademy.reservas.service.CanchaService;
import cl.kibernumacademy.reservas.service.ReservaService;
import cl.kibernumacademy.reservas.repository.CanchaRepository;
import cl.kibernumacademy.reservas.repository.ReservaRepository;
import cl.kibernumacademy.reservas.exception.CanchaNoDisponibleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Integración para el Sistema de Reservas")
class ReservaIntegrationTest {
    
    @Mock
    private CanchaRepository canchaRepository;
    
    @Mock
    private ReservaRepository reservaRepository;
    
    private CanchaService canchaService;
    private ReservaService reservaService;
    
    @BeforeEach
    void setUp() {
        canchaService = new CanchaService(canchaRepository);
        reservaService = new ReservaService(reservaRepository);
    }
    
    @Test
    @DisplayName("Flujo completo: registrar cancha y crear reserva")
    void flujoCompletoRegistrarCanchaYCrearReserva() {
        // Arrange
        String nombreCancha = "Cancha Principal";
        TipoDeporte tipoDeporte = TipoDeporte.FUTBOL;
        List<LocalTime> horarios = Arrays.asList(LocalTime.of(10, 0));
        LocalDateTime fechaHora = LocalDateTime.of(2024, 12, 15, 10, 0);
        String nombreUsuario = "Juan Pérez";
        
        Cancha cancha = new Cancha(nombreCancha, tipoDeporte, horarios);
        Reserva reserva = new Reserva(cancha, fechaHora, nombreUsuario);
        
        // Mock comportamientos
        when(canchaRepository.save(any(Cancha.class))).thenReturn(cancha);
        when(canchaRepository.findByNombre(nombreCancha)).thenReturn(Optional.of(cancha));
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList());
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        
        // Act
        // 1. Registrar cancha
        Cancha canchaRegistrada = canchaService.registrarCancha(nombreCancha, tipoDeporte, horarios);
        
        // 2. Buscar cancha registrada
        Optional<Cancha> canchaEncontrada = canchaService.buscarPorNombre(nombreCancha);
        
        // 3. Crear reserva
        Reserva reservaCreada = reservaService.crearReserva(
            canchaEncontrada.get(), fechaHora, nombreUsuario);
        
        // Assert
        assertThat(canchaRegistrada).isNotNull();
        assertThat(canchaRegistrada.getNombre()).isEqualTo(nombreCancha);
        
        assertThat(canchaEncontrada).isPresent();
        assertThat(canchaEncontrada.get().getTipoDeporte()).isEqualTo(tipoDeporte);
        
        assertThat(reservaCreada).isNotNull();
        assertThat(reservaCreada.getCancha().getNombre()).isEqualTo(nombreCancha);
        assertThat(reservaCreada.getEstado()).isEqualTo(EstadoReserva.ACTIVA);
        
        // Verify interacciones
        verify(canchaRepository, times(1)).save(any(Cancha.class));
        verify(canchaRepository, times(1)).findByNombre(nombreCancha);
        verify(reservaRepository, times(1)).findByCanchaAndFechaHora(any(Cancha.class), any(LocalDateTime.class));
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
    
    @Test
    @DisplayName("Flujo conflicto: intentar reservar cancha ya ocupada")
    void flujoConflictoIntentarReservarCanchaYaOcupada() {
        // Arrange
        Cancha cancha = new Cancha("Cancha Test", TipoDeporte.TENIS, 
            Arrays.asList(LocalTime.of(14, 0)));
        LocalDateTime fechaHora = LocalDateTime.of(2024, 12, 15, 14, 0);
        
        // Reserva existente
        Reserva reservaExistente = new Reserva(cancha, fechaHora, "Usuario Existente");
        
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList(reservaExistente));
        
        // Act & Assert
        assertThatThrownBy(() -> 
            reservaService.crearReserva(cancha, fechaHora, "Usuario Nuevo"))
            .isInstanceOf(CanchaNoDisponibleException.class)
            .hasMessageContaining("no está disponible");
        
        // Verify que no se guardó la nueva reserva
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
    
    @Test
    @DisplayName("Flujo modificación: cambiar horario de reserva existente")
    void flujoModificacionCambiarHorarioReservaExistente() {
        // Arrange
        Long reservaId = 1L;
        Cancha cancha = new Cancha("Cancha Voleibol", TipoDeporte.VOLEIBOL, 
            Arrays.asList(LocalTime.of(16, 0), LocalTime.of(18, 0)));
        LocalDateTime fechaOriginal = LocalDateTime.of(2024, 12, 15, 16, 0);
        LocalDateTime nuevaFecha = LocalDateTime.of(2024, 12, 15, 18, 0);
        
        Reserva reservaExistente = new Reserva(cancha, fechaOriginal, "María García");
        reservaExistente.setId(reservaId);
        
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reservaExistente));
        when(reservaRepository.save(reservaExistente)).thenReturn(reservaExistente);
        
        // Act
        Reserva reservaModificada = reservaService.modificarReserva(reservaId, nuevaFecha);
        
        // Assert
        assertThat(reservaModificada.getFechaHora()).isEqualTo(nuevaFecha);
        assertThat(reservaModificada.getCancha()).isEqualTo(cancha);
        assertThat(reservaModificada.getNombreUsuario()).isEqualTo("María García");
        
        // Verify
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, times(1)).save(reservaExistente);
    }
    
    @Test
    @DisplayName("Flujo cancelación: cancelar reserva y verificar disponibilidad")
    void flujoCancelacionCancelarReservaYVerificarDisponibilidad() {
        // Arrange
        Long reservaId = 2L;
        Cancha cancha = new Cancha("Cancha Basquet", TipoDeporte.BASQUETBOL, 
            Arrays.asList(LocalTime.of(20, 0)));
        LocalDateTime fechaHora = LocalDateTime.of(2024, 12, 15, 20, 0);
        
        Reserva reserva = new Reserva(cancha, fechaHora, "Carlos López");
        reserva.setId(reservaId);
        
        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(reserva)).thenReturn(reserva);
        
        // Mock para verificar disponibilidad después de cancelar
        // Simular que no hay reservas activas después de cancelar
        when(reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora))
            .thenReturn(Arrays.asList()); // Lista vacía = disponible
        
        // Act
        // 1. Cancelar reserva
        reservaService.cancelarReserva(reservaId);
        
        // 2. Verificar que la cancha ahora está disponible
        boolean estaDisponible = reservaService.esCanchaDisponible(cancha, fechaHora);
        
        // Assert
        assertThat(reserva.getEstado()).isEqualTo(EstadoReserva.CANCELADA);
        assertThat(estaDisponible).isTrue();
        
        // Verify
        verify(reservaRepository, times(1)).findById(reservaId);
        verify(reservaRepository, times(1)).save(reserva);
        verify(reservaRepository, times(1)).findByCanchaAndFechaHora(cancha, fechaHora);
    }
}