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
     * @param vehiculo Objeto de tipo Vehiculo que se desea crear en la base de datos.
     * @return true si se crea exitosamente, false si no se puede crear (matrícula ya existe).
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
     * Elimina un registro de vehículo de la base de datos.
     *
     * @param vehiculo Objeto de tipo Vehiculo que se desea eliminar de la base de datos.
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
     * Actualiza un registro de vehículo en la base de datos.
     *
     * @param vehiculo Objeto de tipo Vehiculo que se desea actualizar en la base de datos.
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
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados.
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
     * Valida si la matrícula de un vehículo está registrada en la base de datos.
     *
     * @param matricula Matrícula del vehículo a validar.
     * @return true si la matrícula está registrada en la base de datos, false si no.
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
            System.out.println(ex.getMessage());
        }
        return valido;
    }

    /**
     * Recupera todos los vehículos registrados en la base de datos que pertenecen a un país específico.
     *
     * @param pais País de los vehículos a recuperar.
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados en el país especificado.
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
            System.out.println(e.getMessage());
        }
        return lista;
    }

    /**
     * Recupera todos los vehículos registrados en la base de datos que son de un tipo específico.
     *
     * @param tipoVehiculo Tipo de vehículos a recuperar.
     * @return ArrayList de Vehiculo que contiene todos los vehículos registrados del tipo especificado.
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
