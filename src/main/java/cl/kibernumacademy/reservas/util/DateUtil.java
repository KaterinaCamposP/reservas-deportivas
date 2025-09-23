package cl.kibernumacademy.reservas.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Convierte una fecha a String
     * @param fecha la fecha
     * @return String con formato dd/MM/yyyy
     */
    public static String formatearFecha(LocalDate fecha) {
        return fecha != null ? fecha.format(DATE_FORMATTER) : "";
    }
    
    /**
     * Convierte una hora a String
     * @param hora la hora
     * @return String con formato HH:mm
     */
    public static String formatearHora(LocalTime hora) {
        return hora != null ? hora.format(TIME_FORMATTER) : "";
    }
    
    /**
     * Convierte una fecha/hora a String
     * @param fechaHora la fecha y hora
     * @return String con formato dd/MM/yyyy HH:mm
     */
    public static String formatearFechaHora(LocalDateTime fechaHora) {
        return fechaHora != null ? fechaHora.format(DATETIME_FORMATTER) : "";
    }
    
    /**
     * Verifica si una fecha/hora está en el futuro
     * @param fechaHora la fecha y hora a verificar
     * @return true si está en el futuro
     */
    public static boolean esFechaFutura(LocalDateTime fechaHora) {
        return fechaHora != null && fechaHora.isAfter(LocalDateTime.now());
    }
    
    /**
     * Verifica si una fecha es hoy
     * @param fecha la fecha a verificar
     * @return true si es hoy
     */
    public static boolean esHoy(LocalDate fecha) {
        return fecha != null && fecha.equals(LocalDate.now());
    }
}