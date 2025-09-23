package cl.kibernumacademy.reservas.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@DisplayName("Tests para la clase Cancha")
class CanchaTest {
    
    private Cancha cancha;
    private List<LocalTime> horarios;
    
    @BeforeEach
    void setUp() {
        horarios = Arrays.asList(
            LocalTime.of(9, 0),
            LocalTime.of(11, 0),
            LocalTime.of(15, 0)
        );
        cancha = new Cancha("Cancha Principal", TipoDeporte.FUTBOL, horarios);
    }
    
    @Test
    @DisplayName("Debería crear una cancha con todos los atributos")
    void deberiaCrearCanchaConTodosLosAtributos() {
        // Arrange & Act ya en setUp()
        
        // Assert
        assertThat(cancha.getNombre()).isEqualTo("Cancha Principal");
        assertThat(cancha.getTipoDeporte()).isEqualTo(TipoDeporte.FUTBOL);
        assertThat(cancha.getHorariosDisponibles()).hasSize(3);
        assertThat(cancha.getHorariosDisponibles()).contains(LocalTime.of(9, 0));
    }
    
    @Test
    @DisplayName("Debería crear cancha vacía con constructor por defecto")
    void deberiaCrearCanchaVaciaConConstructorPorDefecto() {
        // Arrange & Act
        Cancha canchaVacia = new Cancha();
        
        // Assert
        assertThat(canchaVacia.getNombre()).isNull();
        assertThat(canchaVacia.getTipoDeporte()).isNull();
        assertThat(canchaVacia.getHorariosDisponibles()).isNull();
    }
    
    @Test
    @DisplayName("Debería permitir modificar atributos mediante setters")
    void deberiaPermitirModificarAtributosPorSetters() {
        // Arrange
        List<LocalTime> nuevosHorarios = Arrays.asList(LocalTime.of(14, 0));
        
        // Act
        cancha.setNombre("Cancha Modificada");
        cancha.setTipoDeporte(TipoDeporte.TENIS);
        cancha.setHorariosDisponibles(nuevosHorarios);
        
        // Assert
        assertThat(cancha.getNombre()).isEqualTo("Cancha Modificada");
        assertThat(cancha.getTipoDeporte()).isEqualTo(TipoDeporte.TENIS);
        assertThat(cancha.getHorariosDisponibles()).hasSize(1);
    }
    
    @Test
    @DisplayName("Debería manejar lista de horarios vacía")
    void deberiaManejarListaHorariosVacia() {
        // Arrange & Act
        Cancha canchaConHorariosVacios = new Cancha("Test", TipoDeporte.BASQUETBOL, Arrays.asList());
        
        // Assert
        assertThat(canchaConHorariosVacios.getHorariosDisponibles()).isEmpty();
    }
    
    @Test
    @DisplayName("Debería permitir diferentes tipos de deporte")
    void deberiaPermitirDiferentesTiposDeDeporte() {
        // Arrange & Act & Assert
        for (TipoDeporte tipo : TipoDeporte.values()) {
            Cancha canchaTest = new Cancha("Cancha " + tipo, tipo, horarios);
            assertThat(canchaTest.getTipoDeporte()).isEqualTo(tipo);
        }
    }
}
