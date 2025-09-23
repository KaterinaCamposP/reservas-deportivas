package cl.kibernumacademy.reservas.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ValidationUtil {
    
    /**
     * Valida que un String no sea nulo o vacío
     * @param valor el valor a validar
     * @param campo nombre del campo (para mensajes de error)
     * @throws IllegalArgumentException si es inválido
     */
    public static void validarStringNoVacio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede ser nulo o vacío");
        }
    }
    
    /**
     * Valida que un objeto no sea nulo
     * @param objeto el objeto a validar
     * @param campo nombre del campo (para mensajes de error)
     * @throws IllegalArgumentException si es nulo
     */
    public static void validarNoNulo(Object objeto, String campo) {
        if (objeto == null) {
            throw new IllegalArgumentException(campo + " no puede ser nulo");
        }
    }
    
    /**
     * Valida que una lista no sea nula o vacía
     * @param lista la lista a validar
     * @param campo nombre del campo (para mensajes de error)
     * @throws IllegalArgumentException si es inválida
     */
    public static void validarListaNoVacia(List<?> lista, String campo) {
        if (lista == null || lista.isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede ser nulo o vacío");
        }
    }
    
    /**
     * Valida que una fecha/hora sea futura
     * @param fechaHora la fecha/hora a validar
     * @param campo nombre del campo (para mensajes de error)
     * @throws IllegalArgumentException si no es futura
     */
    public static void validarFechaFutura(LocalDateTime fechaHora, String campo) {
        validarNoNulo(fechaHora, campo);
        if (!fechaHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(campo + " debe ser una fecha futura");
        }
    }
    
    /**
     * Valida que un horario esté dentro del rango permitido (ej: 6:00 - 23:00)
     * @param hora la hora a validar
     * @throws IllegalArgumentException si está fuera del rango
     */
    public static void validarHorarioPermitido(LocalTime hora) {
        validarNoNulo(hora, "Hora");
        LocalTime horaMinima = LocalTime.of(6, 0);
        LocalTime horaMaxima = LocalTime.of(23, 0);
        
        if (hora.isBefore(horaMinima) || hora.isAfter(horaMaxima)) {
            throw new IllegalArgumentException("El horario debe estar entre 06:00 y 23:00");
        }
    }
}