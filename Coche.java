package GESTION_DE_PARKING;

/**
 * Representa un vehículo tipo Coche en el sistema de gestión de parking.
 * Extiende la clase abstracta Vehículo.
 *
 * @version 1.0
 */
public class Coche extends Vehiculo {

    /**
     * Constructor para crear un objeto Coche con el país de origen especificado.
     *
     * @param pais El país de origen del coche.
     */
    public Coche(Paises pais) {
        super(TipoVehiculo.Coche, pais);
    }

    /**
     * Constructor para crear un objeto Coche con la matrícula y precio de estacionamiento especificados.
     *
     * @param matricula La matrícula del coche.
     * @param precio    El precio de estacionamiento por minuto del coche.
     * @throws Exception Si ocurre un error durante la creación del objeto Coche.
     */
    public Coche(String matricula, double precio) throws Exception {
        super(matricula, TipoVehiculo.Coche, precio);
    }

}