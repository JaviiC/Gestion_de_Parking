package GESTION_DE_PARKING;

import java.util.Objects;

/**
 * Clase que representa una plaza de aparcamiento en un sistema de gestión de parking.
 * Cada plaza tiene un número único, un estado de disponibilidad y opcionalmente una matrícula de vehículo asociada.
 *
 * @version 1.0
 * @see PlazaDAO
 */
public class Plaza {

    /**
     * Número único que identifica la plaza de aparcamiento.
     */
    private final Integer NUMERODEPLAZA;

    /**
     * Indica si la plaza está disponible para ser utilizada.
     */
    private boolean disponible;

    /**
     * Matrícula del vehículo asignado a la plaza.
     * Puede ser {@code null} si la plaza está disponible.
     */
    private String matriculaVehiculo;

    /**
     * Constructor que inicializa una plaza con todos los atributos.
     *
     * @param NUMERODEPLAZA Número único de la plaza. No debe ser {@code null}.
     * @param disponible    Indica si la plaza está disponible para ser utilizada.
     * @param matriculaVehiculo Matrícula del vehículo asignado a la plaza. Puede ser {@code null} si la plaza está disponible.
     * @throws NullPointerException Si {@code NUMERODEPLAZA} es {@code null}.
     */
    public Plaza(Integer NUMERODEPLAZA, boolean disponible, String matriculaVehiculo){
        this.NUMERODEPLAZA = Objects.requireNonNull(NUMERODEPLAZA, "El número de la plaza no puede ser null.");
        this.disponible = disponible;
        this.matriculaVehiculo = matriculaVehiculo;
    }

    /**
     * Constructor que inicializa una plaza con el número único, estado inicial disponible y sin matrícula asignada.
     *
     * @param NUMERODEPLAZA Número único de la plaza. No debe ser {@code null}.
     * @throws NullPointerException Si {@code NUMERODEPLAZA} es {@code null}.
     */
    public Plaza(Integer NUMERODEPLAZA){
        this(NUMERODEPLAZA, true, null);
    }

    /**
     * Obtiene el número único de la plaza.
     *
     * @return Número de la plaza.
     */
    public Integer getNUMERODEPLAZA() {
        return NUMERODEPLAZA;
    }

    /**
     * Obtiene la matrícula del vehículo asignado a la plaza.
     *
     * @return Matrícula del vehículo, o {@code null} si la plaza está disponible.
     */
    public String getMatriculaVehiculo(){
        return matriculaVehiculo;
    }

    /**
     * Verifica si la plaza está disponible.
     *
     * @return {@code true} si la plaza está disponible, {@code false} si está ocupada.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece el estado de disponibilidad de la plaza.
     *
     * @param disponible {@code true} para marcar la plaza como disponible, {@code false} para marcarla como ocupada.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Asigna una matrícula de vehículo a la plaza.
     *
     * @param matriculaVehiculo Matrícula del vehículo a asignar. Puede ser {@code null} para indicar que la plaza está disponible.
     */
    public void setMatriculaVehiculo(String matriculaVehiculo){
        this.matriculaVehiculo = matriculaVehiculo;
    }

    /**
     * Compara esta plaza con otro objeto para verificar si son iguales.
     *
     * @param o Objeto a comparar con esta plaza.
     * @return true si ambos objetos son plazas y tienen el mismo número único, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plaza plaza = (Plaza) o;
        return Objects.equals(NUMERODEPLAZA, plaza.NUMERODEPLAZA);
    }

    /**
     * Genera un código hash para la plaza basado en su número único.
     *
     * @return Código hash de la plaza.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(NUMERODEPLAZA);
    }

    /**
     * Devuelve una representación en formato de texto de la plaza.
     *
     * @return Cadena que describe la plaza con su número, estado de disponibilidad y matrícula del vehículo (si está asignada).
     */
    @Override
    public String toString() {
        return "Plaza " + NUMERODEPLAZA + " - " + disponible + " - Vehículo con matrícula: " + matriculaVehiculo + "\n\n";
    }

}
