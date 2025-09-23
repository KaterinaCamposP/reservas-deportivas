package cl.kibernumacademy.reservas.repository;

import cl.kibernumacademy.reservas.model.Reserva;
import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.EstadoReserva;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository {
    
    /**
     * Guarda una reserva en el repositorio
     * @param reserva la reserva a guardar
     * @return la reserva guardada
     */
    Reserva save(Reserva reserva);
    
    /**
     * Busca una reserva por su ID
     * @param id el ID de la reserva
     * @return Optional con la reserva si existe
     */
    Optional<Reserva> findById(Long id);
    
    /**
     * Busca reservas por cancha y fecha/hora
     * @param cancha la cancha
     * @param fechaHora la fecha y hora
     * @return lista de reservas
     */
    List<Reserva> findByCanchaAndFechaHora(Cancha cancha, LocalDateTime fechaHora);
    
    /**
     * Busca reservas por fecha
     * @param fecha la fecha
     * @return lista de reservas en esa fecha
     */
    List<Reserva> findByFecha(LocalDate fecha);
    
    /**
     * Busca reservas por estado
     * @param estado el estado de la reserva
     * @return lista de reservas con ese estado
     */
    List<Reserva> findByEstado(EstadoReserva estado);
    
    /**
     * Obtiene todas las reservas
     * @return lista de todas las reservas
     */
    List<Reserva> findAll();
    
    /**
     * Elimina una reserva por su ID
     * @param id el ID de la reserva a eliminar
     */
    void deleteById(Long id);
}