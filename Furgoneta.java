package GESTION_DE_PARKING;

/**
 * Representa una Furgoneta en el sistema de gestión de parking.
 * Extiende la clase abstracta Vehículo.
 *
 * @version 1.0
 */
public class Furgoneta extends Vehiculo {

    /**
     * Plus del precio extra que se aplica al Precio(€)/Minuto de la Furgoneta.
     */
    private final double PLUSDIMENSION = 0.2;

    /**
     * Constructor para crear una Furgoneta con el país de origen especificado.
     * Aplica un plus adicional al precio de estacionamiento de la Furgoneta.
     *
     * @param pais El país de origen de la furgoneta.
     */
    public Furgoneta(Paises pais)  {
        super(TipoVehiculo.Furgoneta, pais);
        plusDimension(PLUSDIMENSION);
    }

    /**
     * Constructor para crear una Furgoneta con la matrícula y precio de estacionamiento especificados.
     *
     * @param matricula La matrícula de la furgoneta.
     * @param precio    El precio de estacionamiento por minuto de la furgoneta.
     * @throws Exception Si ocurre un error durante la creación de la furgoneta.
     */
    public Furgoneta(String matricula, double precio) throws Exception {
        super(matricula, TipoVehiculo.Furgoneta, precio);
    }

}