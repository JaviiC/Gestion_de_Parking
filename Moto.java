package GESTION_DE_PARKING;

/**
 * Representa una Moto en el sistema de gestión de parking.
 * Extiende de la clase Vehículo.
 *
 * @version 1.0
 */
public class Moto extends Vehiculo {

    /**
     * Constructor para la clase Moto que especifica el país de origen.
     * Llama al constructor super() de la clase padre con el Tipo de Vehiculo y el país.
     *
     * @param pais El país de origen para la moto especificada.
     */
    public Moto(Paises pais) {
        super(TipoVehiculo.Moto, pais);
    }

    /**
     * Constructor para la clase Moto que especifica la matrícula y el precio de estacionamiento.
     * Llama al constructor super() de la clase padre con la matrícula, el Tipo de Vehiculo y el precio de estacionamiento.
     *
     * @param matricula La matrícula de la moto.
     * @param precio El precio de estacionamiento por minuto de la moto.
     * @throws Exception Si ocurre algún error durante la creación de la Moto.
     */
    public Moto(String matricula, double precio) throws Exception {
        super(matricula, TipoVehiculo.Moto, precio);
    }

}