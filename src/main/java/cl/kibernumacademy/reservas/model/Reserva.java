package cl.kibernumacademy.reservas.model;

import java.time.LocalDateTime;

public class Reserva {
    private Long id;
    private Cancha cancha;
    private LocalDateTime fechaHora;
    private String nombreUsuario;
    private EstadoReserva estado;
    
    // Constructor vacío
    public Reserva() {
        this.estado = EstadoReserva.PENDIENTE;
    }
    
    // Constructor con parámetros
    public Reserva(Cancha cancha, LocalDateTime fechaHora, String nombreUsuario) {
        this.cancha = cancha;
        this.fechaHora = fechaHora;
        this.nombreUsuario = nombreUsuario;
        this.estado = EstadoReserva.ACTIVA;
    }
    
    // Métodos de negocio
    public void cancelar() {
        this.estado = EstadoReserva.CANCELADA;
    }
    
    public void completar() {
        this.estado = EstadoReserva.COMPLETADA;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cancha getCancha() {
        return cancha;
    }
    
    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public EstadoReserva getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}