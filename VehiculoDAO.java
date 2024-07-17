package GESTION_DE_PARKING;

import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que gestiona la interacción con la base de datos para la entidad Vehiculo.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los vehículos en la base de datos.
 *
 * @version 1.0
 */
public class VehiculoDAO {

    /**
     * Conexión a la base de datos.
     */
    public final Connection CONEXION;

    /**
     * Constructor de la clase VehiculoDAO.
     *
     * @param conexion Conexión a la base de datos.
     */
    public VehiculoDAO(Connection conexion){
        CONEXION = conexion;
    }

    /**
     * Crea un registro de vehículo en la base de datos.
     *
     * Este método inserta un nuevo registro de vehículo en la base de datos utilizando
     * los valores del objeto Vehículo proporcionado. Si la matrícula del vehículo ya
     * existe en la base de datos, no se creará el registro y se lanzará una excepción.
     *
     * @param vehiculo Objeto de tipo Vehiculo que se desea crear en la base de datos.
     * @return true si se crea exitosamente, false si no se puede crear (matrícula ya existe).
     * @throws RuntimeException Si la matrícula del vehículo ya existe en la base de datos.
     */
    public boolean creaVehiculo(Vehiculo vehiculo){

        boolean created = false;
        if (!encuentraMatricula(vehiculo.getMATRICULA())) {

            String sentencia = "INSERT INTO vehiculo (matricula, tipo, pais, precioEstacionamiento) " +
                    "VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setString(1, vehiculo.getMATRICULA());
                miPrep.setString(2, vehiculo.getTIPO().toString());
                miPrep.setString(3, vehiculo.getPAIS().toString());
                miPrep.setDouble(4, vehiculo.getPRECIO_POR_MINUTO());

                miPrep.executeUpdate();
                created = true;

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else
            throw new RuntimeException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " ya se encuentra registrado en la base de datos.");

        System.out.println(created);
        return created;
    }

    /**
     * Elimina un vehículo de la base de datos basado en su matrícula.
     *
     * Este método busca el vehículo en la base de datos utilizando su matrícula y,
     * si se encuentra, elimina el registro correspondiente. Si la matrícula no está
     * registrada, se lanzará una excepción.
     *
     * @param vehiculo El objeto Vehiculo que se desea eliminar de la base de datos.
     * @throws RuntimeException Si la matrícula del vehículo no está registrada en la base de datos.
     */
    public void eliminaVehiculo(Vehiculo vehiculo) {

        if (encuentraMatricula(vehiculo.getMATRICULA())) {

            String sentencia = "DELETE FROM vehiculo WHERE matricula = ?";
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setString(1, vehiculo.getMATRICULA());

                miPrep.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else
            throw new RuntimeException("La matrícula " + vehiculo.getMATRICULA() + " no se encuentra registrada en la base de datos");
    }

    /**
     * Actualiza el precio de estacionamiento de un vehículo en la base de datos.
     *
     * Este método modifica el registro de un vehículo existente, estableciendo un nuevo precio
     * de estacionamiento para el vehículo identificado por su matrícula. Si la matrícula no se
     * encuentra en la base de datos, se lanzará una excepción.
     *
     * @param vehiculo El objeto Vehiculo que contiene la matrícula y el nuevo precio de estacionamiento.
     * @throws RuntimeException Si el vehículo con la matrícula especificada no está registrado en la base de datos.
     */
    public void actualizaVehiculo(Vehiculo vehiculo){

        String sentencia = "UPDATE vehiculo SET precioEstacionamiento = ? WHERE matricula = ?";

        if (encuentraMatricula(vehiculo.getMATRICULA())){

            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setDouble(1, vehiculo.getPRECIO_POR_MINUTO());
                miPrep.setString(2, vehiculo.getMATRICULA());

                miPrep.executeUpdate();

            } catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        } else
            throw new RuntimeException("Ha habido un error. El vehículo que se quiere actualizar no se encuentra en registrado en la base de datos");
    }

    /**
     * Recupera todos los vehículos registrados en la base de datos.
     *
     * Este método ejecuta una consulta para obtener todos los registros de vehículos
     * almacenados en la tabla de vehículos y los devuelve como una lista de objetos
     * de tipo Vehiculo. Cada tipo de vehículo se instancia según su clasificación.
     *
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados en la base de datos.
     * @throws RuntimeException Si ocurre un error inesperado durante la ejecución de la consulta.
     */
    public ArrayList<Vehiculo> getAllVehicles() {

        ArrayList<Vehiculo> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM vehiculo";

        try{
            Statement miSt = CONEXION.createStatement();
            ResultSet miRes = miSt.executeQuery(sentencia);

            while (miRes.next()){
                switch (TipoVehiculo.valueOf(miRes.getString(2))) {
                    case Autobus -> lista.add(new Autobus(miRes.getString(1), miRes.getDouble(4)));
                    case Coche -> lista.add(new Coche(miRes.getString(1), miRes.getDouble(4)));
                    case Furgoneta -> lista.add(new Furgoneta(miRes.getString(1), miRes.getDouble(4)));
                    case Moto -> lista.add(new Moto(miRes.getString(1), miRes.getDouble(4)));
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lista;
    }

    /**
     * Verifica si una matrícula existe en la base de datos.
     *
     * Este método ejecuta una consulta para comprobar si la matrícula proporcionada
     * está registrada en la tabla de vehículos. Retorna true si la matrícula existe,
     * de lo contrario retorna false.
     *
     * @param matricula La matrícula que se desea verificar.
     * @return true si la matrícula está registrada en la base de datos, false en caso contrario.
     * @throws IllegalStateException Si ocurre un error al acceder a la base de datos.
     */
    public boolean encuentraMatricula(String matricula){
        String sentencia = "SELECT * FROM vehiculo WHERE matricula = ?";
        boolean valido = false;
        try {
            PreparedStatement miPrem = CONEXION.prepareStatement(sentencia);
            miPrem.setString(1, matricula);

            ResultSet miRes = miPrem.executeQuery();

            if(miRes.next())
                valido = true;

        } catch (SQLException ex){
            throw new IllegalStateException("No se ha encontrado la matricula " + matricula + " en la base de datos");
        }
        return valido;
    }

    /**
     * Recupera todos los vehículos registrados en la base de datos que son de un país específico.
     *
     * Este método ejecuta una consulta en la base de datos para obtener todos los vehículos
     * cuyo país coincide con el especificado y devuelve una lista que contiene las instancias
     * correspondientes a cada tipo de vehículo.
     *
     * @param pais El país del cual se desean recuperar los vehículos.
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados del país especificado.
     * @throws RuntimeException Si ocurre un error inesperado durante la ejecución de la consulta.
     */
    public ArrayList<Vehiculo> getCountryGroup(Paises pais){
        ArrayList<Vehiculo> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM vehiculo WHERE pais = ?";

        try (PreparedStatement miSt = CONEXION.prepareStatement(sentencia)){
            miSt.setString(1, String.valueOf(pais));
            ResultSet miRes = miSt.executeQuery();

            while(miRes.next()){
                switch (TipoVehiculo.valueOf(miRes.getString("tipo"))) {
                    case Autobus -> lista.add(new Autobus(miRes.getString(1), miRes.getDouble(4)));
                    case Coche -> lista.add(new Coche(miRes.getString(1), miRes.getDouble(4)));
                    case Furgoneta -> lista.add(new Furgoneta(miRes.getString(1), miRes.getDouble(4)));
                    case Moto -> lista.add(new Moto(miRes.getString(1), miRes.getDouble(4)));
                }
            }

        } catch (Exception e){
            throw new RuntimeException("Error al recuperar vehículos del país " + pais, e);
        }
        return lista;
    }

    /**
     * Recupera todos los vehículos registrados en la base de datos que son de un tipo específico.
     *
     * Este método ejecuta una consulta en la base de datos para obtener todos los vehículos
     * del tipo especificado y devuelve una lista que contiene las instancias correspondientes
     * a cada tipo de vehículo.
     *
     * @param tipoVehiculo Tipo de vehículos a recuperar (por ejemplo, Autobus, Coche, Furgoneta, Moto).
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados del tipo especificado.
     * @throws RuntimeException Si ocurre un error inesperado durante la ejecución de la consulta.
     */
    public ArrayList<Vehiculo> getTypeGroup(TipoVehiculo tipoVehiculo){
        ArrayList<Vehiculo> lista = new ArrayList<>();
        String sentencia = "SELECT * FROM vehiculo WHERE tipo = ?";

        try {
            PreparedStatement miSt = CONEXION.prepareStatement(sentencia);
            miSt.setString(1, tipoVehiculo.toString());
            ResultSet miRes = miSt.executeQuery();

            while (miRes.next()) {
                switch (tipoVehiculo) {
                    case Autobus -> lista.add(new Autobus(miRes.getString(1), miRes.getDouble(4)));
                    case Coche -> lista.add(new Coche(miRes.getString(1), miRes.getDouble(4)));
                    case Furgoneta -> lista.add(new Furgoneta(miRes.getString(1), miRes.getDouble(4)));
                    case Moto -> lista.add(new Moto(miRes.getString(1), miRes.getDouble(4)));
                }
            }
        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

}
