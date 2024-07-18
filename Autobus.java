package GESTION_DE_PARKING;

/**
 * Representa un objeto Autobús que hereda de la clase Vehículo.
 * Un Autobús tiene un plus de dimensión que se aplica en el precio por minuto (€/min).
 *
 * @version 1.0
 */
public class Autobus extends Vehiculo {

    /**
     * Plus del precio extra que se aplicará al Precio(€)/Minuto del Vehículo.
     */
    private final double PLUSDIMENSION = 0.25;

    /**
     * Constructor de Autobús que inicializa con el tipo de vehículo y aplica el plus de dimensión.
     *
     * @param pais País de registro del Autobús.
     */
    public Autobus(Paises pais)  {
        super(TipoVehiculo.Autobus, pais);
        plusDimension(PLUSDIMENSION);
    }

    /**
     * Constructor de Autobús que inicializa con la matrícula.
     *
     * @param matricula Matrícula del Autobús.
     */
    public Autobus(String matricula){
        super(matricula.toUpperCase(), TipoVehiculo.Autobus);
        plusDimension(PLUSDIMENSION);
    }

    /**
     * Constructor de Autobús que inicializa con la matrícula, tipo de vehículo y precio.
     *
     * @param matricula Matrícula del Autobús.
     * @param precio Precio por minuto del Autobús.
     * @throws Exception Si la matrícula no es válida para el país especificado.
     */
    //Este constructor es más para cuando recuperemos el vehículo de la base de datos
    public Autobus(String matricula, double precio) throws Exception {
        super(matricula.toUpperCase(), TipoVehiculo.Autobus, precio);
    }

}
