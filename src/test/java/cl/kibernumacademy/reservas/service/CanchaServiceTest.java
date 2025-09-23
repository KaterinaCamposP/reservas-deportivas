package cl.kibernumacademy.reservas.service;

import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.TipoDeporte;
import cl.kibernumacademy.reservas.repository.CanchaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para CanchaService")
class CanchaServiceTest {
    
    @Mock
    private CanchaRepository canchaRepository;
    
    private CanchaService canchaService;
    private List<LocalTime> horarios;
    
    @BeforeEach
    void setUp() {
        canchaService = new CanchaService(canchaRepository);
        horarios = Arrays.asList(
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            LocalTime.of(15, 0)
        );
    }
    
    @Test
    @DisplayName("Debería registrar cancha correctamente")
    void deberiaRegistrarCanchaCorrectamente() {
        // Arrange
        String nombre = "Cancha Principal";
        TipoDeporte tipo = TipoDeporte.FUTBOL;
        Cancha canchaEsperada = new Cancha(nombre, tipo, horarios);
        
        when(canchaRepository.save(any(Cancha.class))).thenReturn(canchaEsperada);
        
        // Act
        Cancha resultado = canchaService.registrarCancha(nombre, tipo, horarios);
        
        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo(nombre);
        assertThat(resultado.getTipoDeporte()).isEqualTo(tipo);
        assertThat(resultado.getHorariosDisponibles()).isEqualTo(horarios);
        
        // Verify
        verify(canchaRepository, times(1)).save(any(Cancha.class));
    }
    
    @Test
    @DisplayName("Debería buscar cancha por nombre")
    void deberiaBuscarCanchaPorNombre() {
        // Arrange
        String nombre = "Cancha Test";
        Cancha canchaEsperada = new Cancha(nombre, TipoDeporte.TENIS, horarios);
        
        when(canchaRepository.findByNombre(nombre)).thenReturn(Optional.of(canchaEsperada));
        
        // Act
        Optional<Cancha> resultado = canchaService.buscarPorNombre(nombre);
        
        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo(nombre);
        
        // Verify
        verify(canchaRepository, times(1)).findByNombre(nombre);
    }
    
    @Test
    @DisplayName("Debería retornar Optional vacío cuando no encuentra cancha")
    void deberiaRetornarOptionalVacioCuandoNoEncuentraCancha() {
        // Arrange
        String nombre = "Cancha Inexistente";
        when(canchaRepository.findByNombre(nombre)).thenReturn(Optional.empty());
        
        // Act
        Optional<Cancha> resultado = canchaService.buscarPorNombre(nombre);
        
        // Assert
        assertThat(resultado).isEmpty();
        
        // Verify
        verify(canchaRepository, times(1)).findByNombre(nombre);
    }
    
    @Test
    @DisplayName("Debería obtener canchas por tipo de deporte")
    void deberiaObtenerCanchasPorTipoDeporte() {
        // Arrange
        TipoDeporte tipo = TipoDeporte.BASQUETBOL;
        List<Cancha> canchasEsperadas = Arrays.asList(
            new Cancha("Cancha 1", tipo, horarios),
            new Cancha("Cancha 2", tipo, horarios)
        );
        
        when(canchaRepository.findByTipoDeporte(tipo)).thenReturn(canchasEsperadas);
        
        // Act
        List<Cancha> resultado = canchaService.obtenerPorTipoDeporte(tipo);
        
        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(c -> c.getTipoDeporte().equals(tipo));
        
        // Verify
        verify(canchaRepository, times(1)).findByTipoDeporte(tipo);
    }
    
    @Test
    @DisplayName("Debería obtener todas las canchas")
    void deberiaObtenerTodasLasCanchas() {
        // Arrange
        List<Cancha> canchasEsperadas = Arrays.asList(
            new Cancha("Cancha 1", TipoDeporte.FUTBOL, horarios),
            new Cancha("Cancha 2", TipoDeporte.TENIS, horarios),
            new Cancha("Cancha 3", TipoDeporte.VOLEIBOL, horarios)
        );
        
        when(canchaRepository.findAll()).thenReturn(canchasEsperadas);
        
        // Act
        List<Cancha> resultado = canchaService.obtenerTodasLasCanchas();
        
        // Assert
        assertThat(resultado).hasSize(3);
        assertThat(resultado).containsExactlyElementsOf(canchasEsperadas);
        
        // Verify
        verify(canchaRepository, times(1)).findAll();
    }
}
