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
    public TicketDAO(Connection conexion) {
        CONEXION = conexion;
    }

    /**
     * Crea un nuevo ticket en la base de datos.
     * <p>
     * Este método inserta un nuevo registro de ticket en la base de datos utilizando
     * los valores del objeto {@link Ticket} proporcionado.
     * La fecha de entrada del ticket se convierte de {@link LocalDateTime} a {@link Timestamp}
     * para ser compatible con la base de datos.
     * </p>
     *
     * @param ticket El objeto {@link Ticket} a crear en la base de datos.
     * @throws IllegalStateException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void creaTicket(Ticket ticket) {
        String sentencia = "INSERT INTO ticket (matricula, numeroPlaza, fechaEntrada) VALUES (?, ?, ?)";
        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setString(1, ticket.getMATRICULA());
            miPrep.setInt(2, ticket.getNUM_PLAZA());
            miPrep.setTimestamp(3, Timestamp.valueOf(ticket.getFECHA_ENTRADA()));

            miPrep.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    /**
     * Elimina un ticket de la base de datos.
     * <p>
     * Este método elimina el registro de un ticket de la base de datos basado en el ID del ticket.
     * Si el ticket no está registrado en la base de datos, se lanza una {@link RuntimeException}.
     * </p>
     *
     * @param ticket El objeto {@link Ticket} a eliminar de la base de datos.
     * @throws RuntimeException Si el ticket no está registrado en la base de datos.
     * @throws IllegalStateException Si ocurre un error al ejecutar la consulta SQL.
     */
    public void eliminaTicket(Ticket ticket) {
        String sentencia = "DELETE FROM ticket WHERE id = ?";

        if (validaTicket(ticket.getID())) {
            try {
                PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
                miPrep.setInt(1, ticket.getID());

                miPrep.executeUpdate();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new RuntimeException("Ha habido un error. El Ticket: " + ticket + " no se encuentra registrado en la base de datos.");
        }
    }

    /**
     * Actualiza la información de un ticket en la base de datos.
     * Este método actualiza la fecha de salida y el precio total del ticket identificado por su ID.
     *
     * @param ticket El ticket que contiene la información actualizada, incluyendo la nueva fecha de salida y el precio total.
     * @throws RuntimeException Si ocurre un error durante la actualización del ticket en la base de datos.
     */
    public void actualizaTicket(Ticket ticket) {
        String sentencia = "UPDATE ticket SET fechaSalida = ?, precioTotal = ? WHERE id = ?";

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setTimestamp(1, Timestamp.valueOf(ticket.getFechaSalida()));
            miPrep.setDouble(2, ticket.getPrecioTotal());
            miPrep.setInt(3, ticket.getID());

            miPrep.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Error en la actualización del Ticket en la base de datos", ex);
        }
    }

    /**
     * Obtiene todos los tickets almacenados en la base de datos.
     * <p>
     * Este método consulta todos los registros de tickets en la base de datos y los devuelve
     * en una lista de objetos {@link Ticket}.
     * </p>
     *
     * @return Una lista de todos los tickets almacenados en la base de datos.
     * @throws IllegalStateException Si ocurre un error al consultar la base de datos.
     */
    public ArrayList<Ticket> getAllTickets() {
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
                        fechaSalida,
                        miRes.getDouble("precioTotal")
                );
                tickets.add(ticket);
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("Error al recuperar todos los tickets de la base de datos", ex);
        }
        return tickets;
    }

    /**
     * Recupera un ticket de la base de datos basado en la matrícula del vehículo.
     * <p>
     * Este método ejecuta una consulta SQL para buscar un ticket en la base de datos que tenga la matrícula proporcionada.
     * Si se encuentra un ticket con la matrícula especificada, se devuelve un objeto {@link Ticket} que representa
     * dicho ticket. En caso contrario, se devuelve {@code null}.
     * </p>
     *
     * @param matricula La matrícula del vehículo asociado al ticket que se desea recuperar.
     *                  No puede ser {@code null}.
     * @return Un objeto {@link Ticket} que representa el ticket asociado a la matrícula proporcionada,
     *         o {@code null} si no se encuentra ningún ticket con esa matrícula en la base de datos.
     * @throws IllegalStateException Si ocurre un error al consultar la base de datos o si no se encuentra ningún ticket
     *                                asociado a la matrícula dada.
     *                                Este error se lanza si se produce una {@link SQLException} durante la ejecución
     *                                de la consulta SQL.
     */
    public Ticket getTicketByMatricula(String matricula) {
        String sentencia = "SELECT * FROM ticket WHERE matricula = ? ORDER BY fechaEntrada DESC";
        Ticket ticket = null;

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setString(1, matricula);

            ResultSet miRes = miPrep.executeQuery();

            if (miRes.next()) {
                Timestamp fechaSalidaTimestamp = miRes.getTimestamp("fechaSalida");
                LocalDateTime fechaSalida = fechaSalidaTimestamp == null ? null : fechaSalidaTimestamp.toLocalDateTime();
                ticket = new Ticket(
                        miRes.getInt("id"),
                        miRes.getString("matricula"),
                        miRes.getInt("numeroPlaza"),
                        miRes.getTimestamp("fechaEntrada").toLocalDateTime(),
                        fechaSalida,
                        miRes.getDouble("precioTotal")
                );
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("No se ha encontrado ningún Ticket asociado a la matrícula " + matricula, ex);
        }
        return ticket;
    }

    /**
     * Valida si un ticket con el ID proporcionado está registrado en la base de datos.
     * <p>
     * Este método consulta la base de datos para verificar si existe un ticket con el ID especificado.
     * </p>
     *
     * @param idTicket El ID del ticket a validar.
     * @return {@code true} si el ticket está registrado en la base de datos, {@code false} en caso contrario.
     * @throws IllegalStateException Si ocurre un error al ejecutar la consulta SQL.
     */
    private boolean validaTicket(Integer idTicket) {
        String sentencia = "SELECT * FROM ticket WHERE id = ?";
        boolean valido = false;

        try {
            PreparedStatement miPrep = CONEXION.prepareStatement(sentencia);
            miPrep.setInt(1, idTicket);

            ResultSet miRes = miPrep.executeQuery();

            if (miRes.next()) {
                valido = true;
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
        return valido;
    }
}
