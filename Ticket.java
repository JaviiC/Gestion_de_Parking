package GESTION_DE_PARKING;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un Ticket de estacionamiento para un vehículo en el parking.
 *
 * @version 1.0
 * @see TicketDAO
 */
public class Ticket {

    private final Integer ID;
    private final String MATRICULA;
    private final Integer NUM_PLAZA;
    private final LocalDateTime FECHA_ENTRADA;
    private LocalDateTime fechaSalida;

    /**
     * Constructor para un Ticket con todos los atributos especificados.
     *
     * @param ID           El identificador del Ticket.
     * @param MATRICULA    La matrícula del vehículo asociado al Ticket.
     * @param NUM_PLAZA    El número de la plaza de estacionamiento asignada.
     * @param FECHA_ENTRADA La fecha y hora de entrada del vehículo al parking.
     * @param fechaSalida  La fecha y hora de salida prevista del vehículo del parking.
     */
    public Ticket(Integer ID, String MATRICULA, Integer NUM_PLAZA, LocalDateTime FECHA_ENTRADA, LocalDateTime fechaSalida) {
        this.ID = ID;
        this.MATRICULA = MATRICULA;
        this.NUM_PLAZA = NUM_PLAZA;
        this.FECHA_ENTRADA = FECHA_ENTRADA;
        this.fechaSalida = fechaSalida;
    }

    /**
     * Constructor para un Ticket sin ID especificado (usualmente para tickets nuevos).
     *
     * @param MATRICULA    La matrícula del vehículo asociado al Ticket.
     * @param NUM_PLAZA    El número de la plaza de estacionamiento asignada.
     * @param FECHA_ENTRADA La fecha y hora de entrada del vehículo al parking.
     * @param fechaSalida  La fecha y hora de salida prevista del vehículo del parking.
     */
    public Ticket(String MATRICULA, Integer NUM_PLAZA, LocalDateTime FECHA_ENTRADA, LocalDateTime fechaSalida){
        ID = 0;
        this.MATRICULA = MATRICULA;
        this.NUM_PLAZA = NUM_PLAZA;
        this.FECHA_ENTRADA = FECHA_ENTRADA;
        this.fechaSalida = fechaSalida;
    }

    /**
     * Obtiene el ID del Ticket.
     *
     * @return El ID del Ticket.
     */
    public Integer getID() {
        return ID;
    }

    /**
     * Obtiene la matrícula del vehículo asociado al Ticket.
     *
     * @return La matrícula del vehículo.
     */
    public String getMATRICULA() {
        return MATRICULA;
    }

    /**
     * Obtiene el número de la plaza de estacionamiento asignada al Ticket.
     *
     * @return El número de la plaza.
     */
    public Integer getNUM_PLAZA() {
        return NUM_PLAZA;
    }

    /**
     * Obtiene la fecha y hora de entrada del vehículo al parking.
     *
     * @return La fecha y hora de entrada.
     */
    public LocalDateTime getFECHA_ENTRADA() {
        return FECHA_ENTRADA;
    }

    /**
     * Obtiene la fecha y hora de salida prevista del vehículo del parking.
     *
     * @return La fecha y hora de salida prevista.
     */
    public LocalDateTime getFECHA_SALIDA() {
        return fechaSalida;
    }

    /**
     * Establece la fecha y hora de salida del vehículo del parking.
     *
     * @param fechaSalida La fecha y hora de salida a establecer.
     * @throws RuntimeException Si la fecha proporcionada es anterior a la fecha de entrada.
     */
    public void setFechaSalida(LocalDateTime fechaSalida){
        if (validaFecha(fechaSalida))
            this.fechaSalida = fechaSalida;
        else
            throw new RuntimeException("La fecha proporcionada no puede ser anterior a la de entrada");
    }

    /**
     * Valida si la fecha de salida es posterior a la fecha de entrada.
     *
     * @param fechaSalida La fecha de salida a validar.
     * @return true si la fecha de salida es posterior a la fecha de entrada, false en caso contrario.
     */
    public boolean validaFecha(LocalDateTime fechaSalida){
        return fechaSalida.isAfter(FECHA_ENTRADA);
    }

    /**
     * Compara este Ticket con otro objeto para determinar si son iguales.
     *
     * @param o El objeto con el que comparar.
     * @return true si son el mismo objeto o tienen el mismo ID, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ID, ticket.ID);
    }

    /**
     * Calcula el código hash de este Ticket basado en su ID.
     *
     * @return El código hash del Ticket.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }

    /**
     * Retorna una representación en formato de cadena de este Ticket.
     *
     * @return Una cadena que representa el Ticket con sus atributos.
     */
    @Override
    public String toString() {
        return "\n ===========================" +
                "\n|           TICKET          |" +
                "\n =========================== " +
                "\n    Id: " + ID +
                "\n    Matrícula: " + MATRICULA +
                "\n    Nº plaza: " + NUM_PLAZA +
                "\n    Inicio: " + FECHA_ENTRADA +
                "\n    Salida: " + fechaSalida +
                "\n ===========================";
    }
}
