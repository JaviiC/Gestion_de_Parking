package GESTION_DE_PARKING;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones principales de un parking, incluyendo la gestión de vehículos, plazas y tickets.
 * Ofrece métodos para la entrada, salida, aparcamiento y desaparcamiento de vehículos, así como la consulta de plazas disponibles.
 *
 * La clase utiliza DAOs (Data Access Objects) para interactuar con la base de datos, asegurando la persistencia de los datos.
 *
 * @autor Javier del Cerro
 * @version 1.0
 * @see Vehiculo
 * @see Plaza
 * @see Ticket
 * @see VehiculoDAO
 * @see PlazaDAO
 * @see TicketDAO
 */
public class Parking {

    /**
     * DAO para la gestión de vehículos en la base de datos.
     */
    private final VehiculoDAO vehiculoDAO;

    /**
     * DAO para la gestión de plazas en la base de datos.
     */
    private final PlazaDAO plazaDAO;

    /**
     * DAO para la gestión de tickets en la base de datos.
     */
    private final TicketDAO ticketDAO;

    /**
     * Número total de plazas que contiene el parking.
     */
    private final Integer NUMERO_DE_PLAZAS;

    /**
     * Listado de todas las plazas disponibles en el parking.
     */
    private final ArrayList<Plaza> listadoPlazas;

    /**
     * Listado de todos los vehículos registrados en el parking.
     */
    private ArrayList<Vehiculo> vehiculosRegistrados;

    /**
     * Listado de todos los tickets históricos (entradas y salidas) registrados en el parking.
     */
    private  ArrayList<Ticket> historicoTickets;
    /**
     * Constructor de la clase Parking. Inicializa los DAOs para interactuar con la base de datos,
     * obtiene las listas de plazas, vehículos registrados y tickets históricos desde la base de datos,
     * y crea las plazas iniciales si no existen en la base de datos.
     *
     * @param conexion la conexión a la base de datos.
     * @param numero_plazas el número de plazas que debe tener el parking.
     */
    public Parking (Connection conexion, int numero_plazas){
        // Se crean los DAOs para interactuar con la base de datos
        vehiculoDAO = new VehiculoDAO(conexion);
        plazaDAO = new PlazaDAO(conexion);
        ticketDAO = new TicketDAO(conexion);
        NUMERO_DE_PLAZAS = numero_plazas;

        // Se obtienen todas las plazas, vehículos registrados y tickets históricos desde la base de datos
        listadoPlazas = plazaDAO.getAllPlaces();
        vehiculosRegistrados = vehiculoDAO.getAllVehicles();
        historicoTickets = ticketDAO.getAllTickets();

        // Si no hay plazas en la base de datos, se crean las plazas iniciales
        if (listadoPlazas.isEmpty()) {
            for (int i = 1; i <= NUMERO_DE_PLAZAS; i++) {
                Plaza nueva = new Plaza(i);
                listadoPlazas.add(nueva); // Se añade la plaza a la lista de plazas del Parking
                plazaDAO.creaPlaza(nueva); // Se crea la plaza en la base de datos
            }
        } else
            System.out.println("Ya se definió el tamaño del parking con respecto a la cantidad de plazas disponibles.");
    }

    /**
     * Método getter para obtener el DAO de vehículos.
     *
     * @return Instancia del VehiculoDAO utilizada en este parking.
     */
    public VehiculoDAO getVehiculoDAO() {
        return vehiculoDAO;
    }

    /**
     * Método getter para obtener el DAO de plazas.
     *
     * @return Instancia del PlazaDAO utilizada en este parking.
     */
    public PlazaDAO getPlazaDAO() {
        return plazaDAO;
    }

    /**
     * Método getter para obtener el DAO de tickets.
     *
     * @return Instancia del TicketDAO utilizada en este parking.
     */
    public TicketDAO getTicketDAO() {
        return ticketDAO;
    }

    /**
     * Método getter para obtener el número total de plazas del parking.
     *
     * @return Número total de plazas del parking.
     */
    public Integer getNumeroDePlazas() {
        return NUMERO_DE_PLAZAS;
    }

    /**
     * Método para obtener todos los vehículos registrados en el parking.
     *
     * @return ArrayList de Vehiculo con todos los vehículos registrados.
     */
    public ArrayList<Vehiculo> getVehiculosRegistrados(){
        return vehiculosRegistrados;
    }

    /**
     * Método para obtener todas las plazas del parking.
     *
     * @return ArrayList de Plaza con todas las plazas del parking.
     */
    public ArrayList<Plaza> getPlazas(){
        return listadoPlazas;
    }

    /**
     * Método para obtener el histórico de tickets (entradas y salidas) del parking.
     *
     * @return ArrayList de Ticket con todos los tickets históricos del parking.
     */
    public ArrayList<Ticket> getHistoricoTickets(){
        return historicoTickets;
    }

    /**
     * Método para obtener todas las plazas disponibles en el parking.
     *
     * @return List de Plaza con todas las plazas disponibles en el parking.
     */
    public List<Plaza> getAvaiblePlaces(){
        return listadoPlazas.stream()
                .filter(Plaza::isDisponible) // Filtra las plazas disponibles
                .toList();
    }

    /**
     * Método para registrar la entrada de un vehículo al parking.
     *
     * Este método intenta registrar un vehículo en la base de datos si no está ya registrado.
     * Si el registro es exitoso, el vehículo se añade a la lista de vehículos registrados.
     * Si el registro falla, se lanza una excepción.
     *
     * @param vehiculo Vehículo que entra al parking y se desea registrar.
     * @throws IllegalArgumentException Si el vehículo no se puede registrar en la base de datos.
     */
    public void entradaParking(Vehiculo vehiculo){
        // Se crea un nuevo vehículo y se registra en la base de datos (si no está ya registrado)
        boolean isCreated = vehiculoDAO.creaVehiculo(vehiculo);
        if (isCreated)
            vehiculosRegistrados.add(vehiculo);
        else
            throw new IllegalArgumentException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " no se ha registrado en la base de datos.");
    }

    /**
     * Método para registrar la salida de un vehículo del parking.
     *
     * @param vehiculo Vehículo que sale del parking y se desea eliminar de los registros.
     */
    public void salidaParking(Vehiculo vehiculo){
        boolean isRemoved = vehiculosRegistrados.remove(vehiculo);
        if (isRemoved)
            vehiculoDAO.eliminaVehiculo(vehiculo);
    }

    /**
     * Método para aparcar un vehículo en una plaza específica del parking.
     *
     * Este método aparca un vehículo registrado en una plaza disponible del parking.
     * Verifica que el número de plaza sea válido y que el vehículo esté registrado en el parking.
     * Si la plaza está disponible, actualiza la plaza y crea un ticket sin fecha de salida.
     *
     * @param numeroDePlaza Número de la plaza en la que se desea aparcar el vehículo.
     * @param vehiculo Vehículo que se desea aparcar en la plaza.
     * @throws IllegalArgumentException Si el número de plaza no es válido o si el vehículo no está registrado en el parking.
     */
    public void aparcar(Integer numeroDePlaza, Vehiculo vehiculo){

        //Si el nº de plaza es <= 0 o es mayor al máximo existente
        if (numeroDePlaza <= 0 || numeroDePlaza > NUMERO_DE_PLAZAS) {
            throw new IllegalArgumentException("El número de plaza " + numeroDePlaza + " no es válido." +
                    "\nEl parking actual consta de " + NUMERO_DE_PLAZAS + " plazas.");
        }
        //Si el vehículo es nulo, o la lista que registra los vehículos no la contiene
        if (vehiculo == null || !vehiculosRegistrados.contains(vehiculo)) {
            throw new IllegalArgumentException("El vehículo proporcionado con matrícula " + vehiculo.getMATRICULA() + " no ha entrado en el parking.");
        }

        // Se obtiene el objeto plaza a través de su número
        Plaza plaza = obtenerPlaza(numeroDePlaza);

        // Si la plaza está disponible, se actualizan los datos de la plaza y se genera un ticket sin fecha de salida
        if (plaza.isDisponible()) {
            plaza.setDisponible(false);
            plaza.setMatriculaVehiculo(vehiculo.getMATRICULA());

            // Se actualiza la plaza en la base de datos
            plazaDAO.actualizaPlaza(plaza);

            // Se crea un nuevo Ticket sin fecha de salida
            Ticket nuevo = new Ticket(vehiculo.getMATRICULA(), plaza.getNUMERODEPLAZA(), LocalDateTime.now(), null);
            historicoTickets.add(nuevo);
            ticketDAO.creaTicket(nuevo);

            System.out.println("El vehículo con matrícula " + vehiculo.getMATRICULA() + " ha aparcado en la plaza " + numeroDePlaza);
        } else {
            System.out.println("Ha habido un error. El vehículo con matrícula " + vehiculo.getMATRICULA() + " no pudo aparcar en la plaza " + numeroDePlaza);
        }
    }

    /**
     * Método para desaparcar un vehículo de una plaza específica del parking.
     *
     * Este método libera una plaza ocupada, actualiza el estado de la plaza y
     * registra la fecha de salida del vehículo en el ticket correspondiente.
     *
     * @param plaza Plaza de la que se desea desaparcar el vehículo.
     * @throws RuntimeException Si no se encuentra un ticket asociado a la plaza.
     */
    public void desaparcar(Plaza plaza){
        // Si la plaza tiene asignado un vehículo, se procede a desaparcarlo
        if (plaza.getMatriculaVehiculo() != null){
            plaza.setDisponible(true);
            String matricula = plaza.getMatriculaVehiculo();
            plaza.setMatriculaVehiculo(null);

            // Se actualiza el ticket para registrar la fecha de salida de la plaza
            Ticket ticket = historicoTickets.stream()
                    .filter(t -> t.getMATRICULA().equalsIgnoreCase(matricula))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se ha encontrado un ticket asociado a la plaza " + plaza.getNUMERODEPLAZA() + "."));

            ticket.setFechaSalida(LocalDateTime.now());

            // Se actualiza la plaza en la base de datos
            plazaDAO.actualizaPlaza(plaza);

            // Se actualiza el ticket en la base de datos para registrar la fecha de salida
            ticketDAO.actualizaTicket(ticket);
        }
    }
    /**
     * Busca un vehículo en la lista de vehículos registrados que tenga la matrícula especificada.
     *
     * @param matricula la matrícula del vehículo que se desea buscar.
     * @return el objeto {@link Vehiculo} que tiene la matrícula especificada.
     * @throws RuntimeException si no se encuentra ningún vehículo con la matrícula especificada.
     */
    public Vehiculo getVehiculoByMatricula(String matricula){
        return vehiculosRegistrados.stream()
                .filter(v -> v.getMATRICULA().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ningún vehículo con matrícula " + matricula));
    }

    /**
     * Método para obtener una plaza específica del parking a partir de su número.
     *
     * Este método busca en la lista de plazas del parking y devuelve el objeto Plaza
     * correspondiente al número especificado. Si no se encuentra una plaza con el número dado,
     * se lanza una excepción.
     *
     * @param numeroDePlaza Número de la plaza que se desea obtener.
     * @return Objeto Plaza correspondiente al número especificado.
     * @throws IllegalArgumentException Si no se encuentra una plaza con el número especificado.
     */
    public Plaza obtenerPlaza(int numeroDePlaza) {
        return listadoPlazas.stream()
                .filter(p -> p.getNUMERODEPLAZA() == numeroDePlaza)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No se ha encontrado la plaza " + numeroDePlaza));
    }

    /**
     * Muestra el estado actual del parking en la consola.
     *
     * Este método imprime en la salida estándar información relevante sobre el estado del parking,
     * incluyendo el número total de plazas, el número de plazas disponibles, la cantidad de vehículos
     * registrados y el historial de tickets. Además, lista los detalles de cada plaza, vehículo y ticket
     * registrado en el sistema.
     */
    public void showParkingStatus() {
        System.out.println("Estado actual del parking:");
        System.out.println("Número total de plazas: " + NUMERO_DE_PLAZAS);
        System.out.println("Plazas disponibles: " + getAvaiblePlaces().size());
        System.out.println("Vehículos registrados: " + vehiculosRegistrados.size());
        System.out.println("Historial de tickets: " + historicoTickets.size());
        System.out.println("\nPlazas:");
        for (Plaza plaza : listadoPlazas) {
            System.out.println(plaza.toString());
        }
        System.out.println("\nVehículos:");
        for (Vehiculo vehiculo : vehiculosRegistrados) {
            System.out.println(vehiculo.toString());
        }
        System.out.println("\nHistorial de tickets:");
        for (Ticket ticket : historicoTickets) {
            System.out.println(ticket.toString());
        }
    }

}
