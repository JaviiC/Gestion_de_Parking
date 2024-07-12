package GESTION_DE_PARKING;

import java.sql.*;
import java.util.ArrayList;

/**
 * Esta clase gestiona la interacción con la base de datos para la entidad Plaza.
 * Permite crear, eliminar, actualizar y obtener información de las plazas en la base de datos.
 */
public class PlazaDAO {

    private final Connection CONEXION;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     * @param conexion La conexión a la base de datos.
     */
    public PlazaDAO(Connection conexion){
        CONEXION = conexion;
    }

    /**
     * Crea una nueva plaza en la base de datos.
     * @param plaza La plaza a crear.
     */
    public void creaPlaza(Plaza plaza) {

        String sentencia = "INSERT INTO plaza (numero, disponible) " +
                "VALUES (?, ?)";
        try{
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setInt(1, plaza.getNUMERODEPLAZA());
            miPrep.setBoolean(2, plaza.isDisponible());

            miPrep.executeUpdate();

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Elimina una plaza de la base de datos.
     * @param plaza La plaza a eliminar.
     * @throws RuntimeException Si la plaza no está registrada en la base de datos.
     */
    public void eliminaPlaza(Plaza plaza) {

        String sentencia = "DELETE FROM plaza WHERE numero = ?";

        if (validaPlaza(plaza.getNUMERODEPLAZA())) {
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setInt(1, plaza.getNUMERODEPLAZA());
                miPrep.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else
            throw new RuntimeException("El número de plaza " + plaza + " no está registrado en la base de datos.");
    }

    /**
     * Actualiza los datos de una plaza en la base de datos.
     * @param plaza La plaza con los nuevos datos a actualizar.
     */
    public void actualizaPlaza(Plaza plaza){

        String sentencia = "UPDATE plaza SET disponible = ?, matriculaVehiculo = ? WHERE numero = ?";

        try{
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setBoolean(1, plaza.isDisponible());
            miPrep.setString(2, plaza.getMatriculaVehiculo());
            miPrep.setInt(3, plaza.getNUMERODEPLAZA());

            miPrep.executeUpdate();
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Obtiene todas las plazas almacenadas en la base de datos.
     * @return Una lista de todas las plazas almacenadas en la base de datos.
     */
    public ArrayList<Plaza> getAllPlaces(){

        ArrayList<Plaza> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM plaza";

        try{
            Statement miSt = CONEXION.createStatement();
            ResultSet miRes = miSt.executeQuery(sentencia);

            while (miRes.next()){
                lista.add(new Plaza(miRes.getInt(1),
                        miRes.getBoolean(2),
                        miRes.getString(3)));
            }

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return lista;
    }

    /**
     * Valida si una plaza con el número indicado está registrada en la base de datos.
     * @param numPlaza El número de la plaza a validar.
     * @return true si la plaza está registrada, false en caso contrario.
     */
    public boolean validaPlaza(Integer numPlaza){

        String sentencia = "SELECT numero FROM plaza WHERE numero = ?";
        boolean valido = false;

        try{
            PreparedStatement miSt = CONEXION.prepareStatement(sentencia);
            miSt.setInt(1, numPlaza);

            ResultSet miRes = miSt.executeQuery();

            if (miRes.next())
                valido = true;

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return valido;
    }

}
