package cl.kibernumacademy.reservas.service;

import cl.kibernumacademy.reservas.model.Cancha;
import cl.kibernumacademy.reservas.model.TipoDeporte;
import cl.kibernumacademy.reservas.repository.CanchaRepository;
//import cl.kibernumacademy.reservas.exception.ReservaException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class CanchaService {
    
    private final CanchaRepository canchaRepository;
    
    public CanchaService(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }
    
    /**
     * Registra una nueva cancha
     * @param nombre nombre de la cancha
     * @param tipoDeporte tipo de deporte
     * @param horariosDisponibles horarios disponibles
     * @return la cancha registrada
     */
    public Cancha registrarCancha(String nombre, TipoDeporte tipoDeporte, List<LocalTime> horariosDisponibles) {
        // TODO: Implementar validaciones y lógica de negocio
        Cancha cancha = new Cancha(nombre, tipoDeporte, horariosDisponibles);
        return canchaRepository.save(cancha);
    }
    
    /**
     * Busca una cancha por nombre
     * @param nombre nombre de la cancha
     * @return Optional con la cancha si existe
     */
    public Optional<Cancha> buscarPorNombre(String nombre) {
        return canchaRepository.findByNombre(nombre);
    }
    
    /**
     * Obtiene canchas por tipo de deporte
     * @param tipoDeporte tipo de deporte
     * @return lista de canchas
     */
    public List<Cancha> obtenerPorTipoDeporte(TipoDeporte tipoDeporte) {
        return canchaRepository.findByTipoDeporte(tipoDeporte);
    }
    
    /**
     * Obtiene todas las canchas
     * @return lista de todas las canchas
     */
    public List<Cancha> obtenerTodasLasCanchas() {
        return canchaRepository.findAll();
    }
}