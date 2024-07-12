package GESTION_DE_PARKING;

import java.util.Objects;

/**
 * Clase que representa una plaza de aparcamiento en un sistema de gestión de parking.
 * Cada plaza tiene un número único, estado de disponibilidad y opcionalmente una matrícula de vehículo asociada.
 *
 * @version 1.0
 * @see PlazaDAO
 */
public class Plaza {

    private final Integer NUMERODEPLAZA;
    private boolean disponible;
    private String matriculaVehiculo;

    /**
     * Constructor que inicializa una plaza con todos los atributos.
     *
     * @param NUMERODEPLAZA Número único de la plaza.
     * @param disponible    Indica si la plaza está disponible para ser utilizada.
     * @param matriculaVehiculo Matrícula del vehículo asignado a la plaza (puede ser null si la plaza está disponible).
     */
    public Plaza(Integer NUMERODEPLAZA, boolean disponible, String matriculaVehiculo){
        this.NUMERODEPLAZA = NUMERODEPLAZA;
        this.disponible = disponible;
        this.matriculaVehiculo = matriculaVehiculo;
    }

    /**
     * Constructor que inicializa una plaza con el número único, con estado inicial disponible y sin matrícula asignada (null).
     *
     * @param NUMERODEPLAZA Número único de la plaza.
     */
    public Plaza(Integer NUMERODEPLAZA){
        this.NUMERODEPLAZA = NUMERODEPLAZA;
        disponible = true;
        matriculaVehiculo = null;
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
     * @return Matrícula del vehículo (puede ser null si la plaza está disponible).
     */
    public String getMatriculaVehiculo(){
        return matriculaVehiculo;
    }

    /**
     * Verifica si la plaza está disponible.
     *
     * @return true si la plaza está disponible, false si está ocupada.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece el estado de disponibilidad de la plaza.
     *
     * @param disponible true para la plaza disponible, y false para ocupada.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Asigna la matrícula del vehículo a la plaza.
     *
     * @param matriculaVehiculo Matrícula del vehículo a asignar.
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
