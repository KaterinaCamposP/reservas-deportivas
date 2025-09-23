package cl.kibernumacademy.reservas.repository;

import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.TipoDeporte;
import java.util.List;
import java.util.Optional;

public interface CanchaRepository {
    
    /**
     * Guarda una cancha en el repositorio
     * @param cancha la cancha a guardar
     * @return la cancha guardada
     */
    Cancha save(Cancha cancha);
    
    /**
     * Busca una cancha por su nombre
     * @param nombre el nombre de la cancha
     * @return Optional con la cancha si existe
     */
    Optional<Cancha> findByNombre(String nombre);
    
    /**
     * Busca canchas por tipo de deporte
     * @param tipoDeporte el tipo de deporte
     * @return lista de canchas del tipo especificado
     */
    List<Cancha> findByTipoDeporte(TipoDeporte tipoDeporte);
    
    /**
     * Obtiene todas las canchas
     * @return lista de todas las canchas
     */
    List<Cancha> findAll();
    
    /**
     * Elimina una cancha por su nombre
     * @param nombre el nombre de la cancha a eliminar
     */
    void deleteByNombre(String nombre);
}