package GESTION_DE_PARKING;

import java.sql.*;
import java.util.ArrayList;

/**
 * Esta clase gestiona la interacción con la base de datos para la entidad Plaza.
 * Permite crear, eliminar, actualizar y obtener información de las plazas en la base de datos.
 *
 * @version 1.0
 */
public class PlazaDAO {

    /**
     * Conexión a la base de datos.
     */
    private final Connection CONEXION;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param conexion La conexión a la base de datos.
     * @throws NullPointerException Si {@code conexion} es {@code null}.
     */
    public PlazaDAO(Connection conexion){
        if (conexion == null) {
            throw new NullPointerException("La conexión no puede ser nula.");
        }
        CONEXION = conexion;
    }

    /**
     * Crea una nueva plaza en la base de datos.
     *
     * @param plaza La plaza a crear.
     * @throws NullPointerException Si {@code plaza} es {@code null}.
     * @throws IllegalArgumentException Si ocurre un error al ejecutar la sentencia SQL.
     */
    public void creaPlaza(Plaza plaza) {

        if (plaza == null) {
            throw new NullPointerException("La plaza no puede ser nula.");
        }

        String sentencia = "INSERT INTO plaza (numero, disponible) VALUES (?, ?)";
        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setInt(1, plaza.getNUMERODEPLAZA());
            miPrep.setBoolean(2, plaza.isDisponible());

            miPrep.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Elimina una plaza de la base de datos.
     *
     * @param plaza La plaza a eliminar.
     * @throws NullPointerException Si {@code plaza} es {@code null}.
     * @throws IllegalArgumentException Si ocurre un error al ejecutar la sentencia SQL.
     * @throws IllegalArgumentException Si el número de {@code plaza} no está registrado.
     */
    public void eliminaPlaza(Plaza plaza) {

        if (plaza == null) {
            throw new NullPointerException("La plaza no puede ser nula.");
        }

        String sentencia = "DELETE FROM plaza WHERE numero = ?";

        if (validaPlaza(plaza.getNUMERODEPLAZA())) {
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setInt(1, plaza.getNUMERODEPLAZA());
                miPrep.executeUpdate();

            } catch (SQLException ex) {
                throw new IllegalArgumentException(ex.getMessage());
            }
        } else {
            throw new IllegalArgumentException("El número de plaza " + plaza.getNUMERODEPLAZA() + " no está registrado en la base de datos.");
        }
    }

    /**
     * Actualiza los datos de una plaza en la base de datos.
     *
     * @param plaza La plaza con los nuevos datos a actualizar.
     * @throws NullPointerException Si {@code plaza} es {@code null}.
     * @throws IllegalArgumentException Si ocurre un error al ejecutar la sentencia SQL.
     */
    public void actualizaPlaza(Plaza plaza){

        if (plaza == null) {
            throw new NullPointerException("La plaza no puede ser nula.");
        }

        String sentencia = "UPDATE plaza SET disponible = ?, matriculaVehiculo = ? WHERE numero = ?";

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setBoolean(1, plaza.isDisponible());
            miPrep.setString(2, plaza.getMatriculaVehiculo());
            miPrep.setInt(3, plaza.getNUMERODEPLAZA());

            miPrep.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    /**
     * Obtiene todas las plazas almacenadas en la base de datos.
     *
     * @return Una lista de todas las plazas almacenadas en la base de datos.
     * @throws IllegalArgumentException Si ocurre un error al ejecutar la sentencia SQL.
     */
    public ArrayList<Plaza> getAllPlaces(){

        ArrayList<Plaza> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM plaza";

        try {
            Statement miSt = CONEXION.createStatement();
            ResultSet miRes = miSt.executeQuery(sentencia);

            while (miRes.next()){
                lista.add(new Plaza(
                        miRes.getInt(1),
                        miRes.getBoolean(2),
                        miRes.getString(3)));
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return lista;
    }

    /**
     * Valida si una plaza con el número indicado está registrada en la base de datos.
     *
     * @param numPlaza El número de la plaza a validar. No debe ser {@code null}.
     * @return {@code true} si la plaza está registrada, {@code false} en caso contrario.
     * @throws NullPointerException Si {@code numPlaza} es {@code null}.
     * @throws IllegalArgumentException Si ocurre un error al ejecutar la sentencia SQL.
     */
    public boolean validaPlaza(Integer numPlaza){

        if (numPlaza == null) {
            throw new NullPointerException("El número de plaza no puede ser nulo.");
        }

        String sentencia = "SELECT numero FROM plaza WHERE numero = ?";
        boolean valido = false;

        try {
            PreparedStatement miSt = CONEXION.prepareStatement(sentencia);
            miSt.setInt(1, numPlaza);

            ResultSet miRes = miSt.executeQuery();

            if (miRes.next()) {
                valido = true;
            }

        } catch (SQLException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        return valido;
    }
}
