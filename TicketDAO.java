package GESTION_DE_PARKING;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Esta clase proporciona métodos para acceder y manipular datos de tickets en una base de datos.
 * Utiliza una conexión JDBC para interactuar con la base de datos subyacente.
 *
 * @version 1.0
 */
public class TicketDAO {

    private final Connection CONEXION;

    /**
     * Constructor de la clase TicketDAO que inicializa la conexión a la base de datos.
     *
     * @param conexion La conexión JDBC a la base de datos.
     */
    public TicketDAO(Connection conexion){
        CONEXION = conexion;
    }

    /**
     * Crea un nuevo ticket en la base de datos.
     *
     * @param ticket El objeto Ticket a crear en la base de datos.
     */
    public void creaTicket(Ticket ticket){

        String sentencia = "INSERT INTO ticket (matricula, numeroPlaza, fechaEntrada)" +
                "VALUES (?, ?, ?)";
        try{
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setString(1, ticket.getMATRICULA());
            miPrep.setInt(2, ticket.getNUM_PLAZA());
            // Se formatea el LocalDateTime a Timestamp ya que el PreparedStatement no puede recuperar LocalDateTime
            miPrep.setTimestamp(3, Timestamp.valueOf(ticket.getFECHA_ENTRADA()));

            miPrep.executeUpdate();

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Elimina un ticket de la base de datos.
     *
     * @param ticket El objeto Ticket a eliminar de la base de datos.
     * @throws RuntimeException Si el Ticket no está registrado en la base de datos.
     */
    public void eliminaTicket(Ticket ticket){

        String sentencia = "DELETE FROM ticket WHERE id = ?";

        if (validaTicket(ticket.getID())) {
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setInt(1, ticket.getID());

                miPrep.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else
            throw new RuntimeException("Ha habido un error. El Ticket: " + ticket + " no se encuentra registrado en la base de datos.");
    }

    /**
     * Actualiza la fecha de salida de un ticket en la base de datos.
     *
     * @param ticket El objeto Ticket con la nueva fecha de salida a actualizar en la base de datos.
     */
    public void actualizaTicket(Ticket ticket){

        String sentencia = "UPDATE ticket SET fechaSalida = ? WHERE id = ?";

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            // Se formatea el LocalDateTime a Timestamp ya que el PreparedStatement no puede recuperar LocalDateTime
            miPrep.setTimestamp(1, Timestamp.valueOf(ticket.getFECHA_SALIDA()));
            miPrep.setInt(2, ticket.getID());

            miPrep.executeUpdate();

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Obtiene todos los tickets almacenados en la base de datos.
     *
     * @return Una lista de todos los tickets almacenados en la base de datos.
     */
    public ArrayList<Ticket> getAllTickets(){

        ArrayList<Ticket> tickets = new ArrayList<>();
        String sentencia = "SELECT * FROM ticket";

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            ResultSet miRes = miPrep.executeQuery();

            while (miRes.next()) {
                Timestamp fechaSalidaTimestamp = miRes.getTimestamp("fechaSalida");
                LocalDateTime fechaSalida = fechaSalidaTimestamp == null ? null : fechaSalidaTimestamp.toLocalDateTime();
                Ticket ticket = new Ticket(
                        miRes.getInt("id"),
                        miRes.getString("matricula"),
                        miRes.getInt("numeroPlaza"),
                        miRes.getTimestamp("fechaEntrada").toLocalDateTime(),
                        fechaSalida
                );
                tickets.add(ticket);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tickets;
    }

    /**
     * Valida si un ticket tiene un historial registrado en la base de datos.
     *
     * @param idTicket El ID del historial a validar.
     * @return true si el historial está registrado en la base de datos, false en caso contrario.
     */
    private boolean validaTicket(Integer idTicket){

        String sentencia = "SELECT * FROM ticket WHERE id = ?";
        boolean valido = false;

        try{
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setInt(1, idTicket);

            ResultSet miRes = miPrep.executeQuery();

            if (miRes.next())
                valido = true;

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return valido;
    }

}
