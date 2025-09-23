package cl.kibernumacademy.reservas.service;

import cl.kibernumacademy.reservas.model.Reserva;
import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.EstadoReserva;
import cl.kibernumacademy.reservas.repository.ReservaRepository;
import cl.kibernumacademy.reservas.exception.CanchaNoDisponibleException;
import cl.kibernumacademy.reservas.exception.ReservaNoEncontradaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaService {
    
    private final ReservaRepository reservaRepository;
    
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
    
    /**
     * Crea una nueva reserva
     * @param cancha la cancha a reservar
     * @param fechaHora fecha y hora de la reserva
     * @param nombreUsuario nombre del usuario
     * @return la reserva creada
     * @throws CanchaNoDisponibleException si la cancha no está disponible
     */
    public Reserva crearReserva(Cancha cancha, LocalDateTime fechaHora, String nombreUsuario) {
        // TODO: Implementar validación de disponibilidad
        if (!esCanchaDisponible(cancha, fechaHora)) {
            throw new CanchaNoDisponibleException("La cancha no está disponible en el horario solicitado");
        }
        
        Reserva reserva = new Reserva(cancha, fechaHora, nombreUsuario);
        return reservaRepository.save(reserva);
    }
    
    /**
     * Modifica una reserva existente
     * @param id ID de la reserva
     * @param nuevaFechaHora nueva fecha y hora
     * @return la reserva modificada
     * @throws ReservaNoEncontradaException si no se encuentra la reserva
     */
    public Reserva modificarReserva(Long id, LocalDateTime nuevaFechaHora) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new ReservaNoEncontradaException("No se encontró la reserva con ID: " + id));
        
        // TODO: Implementar validación de disponibilidad para la nueva fecha
        reserva.setFechaHora(nuevaFechaHora);
        return reservaRepository.save(reserva);
    }
    
    /**
     * Cancela una reserva
     * @param id ID de la reserva a cancelar
     * @throws ReservaNoEncontradaException si no se encuentra la reserva
     */
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new ReservaNoEncontradaException("No se encontró la reserva con ID: " + id));
        
        reserva.cancelar();
        reservaRepository.save(reserva);
    }
    
    /**
     * Verifica si una cancha está disponible en una fecha/hora específica
     * @param cancha la cancha
     * @param fechaHora la fecha y hora
     * @return true si está disponible, false en caso contrario
     */
    public boolean esCanchaDisponible(Cancha cancha, LocalDateTime fechaHora) {
        List<Reserva> reservasExistentes = reservaRepository.findByCanchaAndFechaHora(cancha, fechaHora);
        return reservasExistentes.stream()
            .noneMatch(r -> r.getEstado() == EstadoReserva.ACTIVA);
    }
    
    /**
     * Calcula el número de reservas por día
     * @param fecha la fecha
     * @return número de reservas en esa fecha
     */
    public long calcularReservasPorDia(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha).size();
    }
    
    /**
     * Obtiene todas las reservas activas
     * @return lista de reservas activas
     */
    public List<Reserva> obtenerReservasActivas() {
        return reservaRepository.findByEstado(EstadoReserva.ACTIVA);
    }
}