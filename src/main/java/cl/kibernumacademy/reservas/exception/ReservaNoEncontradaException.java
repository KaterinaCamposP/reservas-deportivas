package cl.kibernumacademy.reservas.exception;

public class ReservaNoEncontradaException extends ReservaException {
    
    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
    
    public ReservaNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}