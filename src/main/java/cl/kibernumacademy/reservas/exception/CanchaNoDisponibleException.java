package cl.kibernumacademy.reservas.exception;

public class CanchaNoDisponibleException extends ReservaException {
    
    public CanchaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
    
    public CanchaNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}