package GESTION_DE_PARKING;

import java.util.Objects;

/**
 * Clase abstracta que representa un vehículo en el sistema de gestión de parking.
 * Esta clase implementa la interfaz {@link Comparable} para poder comparar vehículos por país.
 *
 * @version 1.0
 * @see VehiculoDAO
 */
public abstract class Vehiculo implements Comparable<Vehiculo> {

    /**
     * Matrícula identificativa del Vehículo.
     */
    private final String MATRICULA;

    /**
     * Tipo de Vehículo (Coche, Moto, Furgoneta, Autobús, etc).
     */
    private final TipoVehiculo TIPO;

    /**
     * País al que pertenece el Vehículo según su matrícula.
     */
    private final Paises PAIS;

    /**
     * Indica si el vehículo está actualmente activo (se encuentra dentro del parking).
     */
    private boolean activo;

    /**
     * Precio (€)/Minuto que se cobra al Vehículo durante su estacionamiento.
     */
    private double precioPorMinuto = 0.04;

    /**
     * Constructor para vehículos con tipo y país especificados.
     * Genera una matrícula aleatoria para el vehículo según el país.
     *
     * @param tipo Tipo de vehículo.
     * @param pais País al que pertenece la matrícula del vehículo.
     */
    public Vehiculo(TipoVehiculo tipo, Paises pais) {
        MATRICULA = GestionMatriculas.nueva(pais);
        TIPO = tipo;
        PAIS = pais;
        activo = true;
    }

    /**
     * Constructor para vehículos con matrícula y tipo de vehículo especificados.
     *
     * @param matricula Matrícula del vehículo.
     * @param tipo Tipo de vehículo.
     */
    public Vehiculo(String matricula, TipoVehiculo tipo) {
        PAIS = GestionMatriculas.getPais(matricula);
        MATRICULA = matricula;
        TIPO = tipo;
        activo = true;
    }

    /**
     * Constructor para vehículos con matrícula, tipo, precio y estado especificados.
     *
     * @param matricula Matrícula del vehículo.
     * @param tipo Tipo de vehículo.
     * @param precio Precio por minuto de estacionamiento del vehículo.
     * @param activo Indica si el vehículo está actualmente activo.
     * @throws Exception Si la matrícula no pertenece a ningún país registrado.
     */
    public Vehiculo(String matricula, TipoVehiculo tipo, double precio, boolean activo) throws Exception {
        MATRICULA = matricula;
        TIPO = tipo;
        PAIS = GestionMatriculas.getPais(MATRICULA);
        precioPorMinuto = precio;
        this.activo = activo;
    }

    /**
     * Obtiene la matrícula del vehículo.
     *
     * @return Matrícula del vehículo.
     */
    public String getMATRICULA() {
        return MATRICULA;
    }

    /**
     * Obtiene el tipo de vehículo.
     *
     * @return Tipo de vehículo.
     */
    public TipoVehiculo getTIPO() {
        return TIPO;
    }

    /**
     * Obtiene el país al que pertenece la matrícula del vehículo.
     *
     * @return País de la matrícula del vehículo.
     */
    public Paises getPAIS() {
        return PAIS;
    }

    /**
     * Obtiene el precio por minuto de estacionamiento del vehículo.
     *
     * @return Precio por minuto de estacionamiento del vehículo.
     */
    public double getPrecioPorMinuto() {
        return precioPorMinuto;
    }

    /**
     * Verifica si el vehículo está actualmente activo (estacionado en el parking).
     *
     * @return {@code true} si el vehículo está activo, {@code false} en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado de actividad del vehículo.
     *
     * @param activo {@code true} si el vehículo está activo (se encuentra en el parking), {@code false} en caso contrario.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Añade un plus al precio por minuto del vehículo.
     * <p>
     * Este método incrementa el precio por minuto en la cantidad especificada por el parámetro {@code plus}.
     * </p>
     *
     * @param plusDimension Monto adicional a añadir al precio por minuto.
     */
    protected void plusDimension(double plusDimension) {
        precioPorMinuto += plusDimension;
    }

/**
     * Compara este vehículo con otro según el país de su matrícula.
     *
     * @param b Vehículo con el que se compara.
     * @return Valor negativo si este vehículo pertenece a un país anterior al del vehículo b,
     *         valor positivo si es posterior, y cero si son del mismo país.
     */
    @Override
    public int compareTo(Vehiculo b){
        return this.getTIPO().compareTo(b.getTIPO());
    }

    /**
     * Compara este vehículo con otro objeto para determinar si son iguales.
     *
     * @param o Objeto a comparar.
     * @return true si el objeto es igual a este vehículo (misma matrícula), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(MATRICULA, vehiculo.MATRICULA);
    }

    /**
     * Genera un código hash para este vehículo basado en su matrícula.
     *
     * @return Código hash del vehículo.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(MATRICULA);
    }

    /**
     * Retorna una representación en cadena de texto del vehículo.
     *
     * @return Representación del vehículo en cadena de texto.
     */
    @Override
    public String toString() {
        return TIPO + " con matrícula " + MATRICULA + ". Pais: "+ PAIS + ". Precio: " + precioPorMinuto + "€/min. Activo: " + activo;
    }

}
