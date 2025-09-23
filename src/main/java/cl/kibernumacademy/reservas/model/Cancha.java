package cl.kibernumacademy.reservas.model;

import java.time.LocalTime;
import java.util.List;

public class Cancha {
    private String nombre;
    private TipoDeporte tipoDeporte;
    private List<LocalTime> horariosDisponibles;
    
    // Constructor vacío
    public Cancha() {
    }
    
    // Constructor con parámetros
    public Cancha(String nombre, TipoDeporte tipoDeporte, List<LocalTime> horariosDisponibles) {
        this.nombre = nombre;
        this.tipoDeporte = tipoDeporte;
        this.horariosDisponibles = horariosDisponibles;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public TipoDeporte getTipoDeporte() {
        return tipoDeporte;
    }
    
    public void setTipoDeporte(TipoDeporte tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }
    
    public List<LocalTime> getHorariosDisponibles() {
        return horariosDisponibles;
    }
    
    public void setHorariosDisponibles(List<LocalTime> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }
}