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
 * @author Javier del Cerro
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
     * Precio máximo que se puede imponer a la estancia de un vehículo.
     */
    private final Double PRECIO_MAXIMO_ESTANCIA = 20.0;

    /**
     * Precio máximo que se puede imponer a la estancia de un vehículo grande (que tiene impuesto un plus de dimensión)
     */
    private final Double PRECIO_MAXIMO_ESTANCIA_PLUS = 45.5;

    /**
     * Listado de todos los vehículos registrados en el parking.
     */
    private ArrayList<Vehiculo> vehiculosRegistrados;

    /**
     * Listado de todos los tickets históricos (entradas y salidas) registrados en el parking.
     */
    private  ArrayList<Ticket> historicoTickets;
    /**
     * Constructor de la clase Parking. Inicializa los DAOs necesarios para interactuar con la base de datos,
     * recupera las listas de plazas, vehículos registrados y tickets históricos desde la base de datos,
     * y crea las plazas iniciales si no existen en la base de datos.
     *
     * @param conexion La conexión a la base de datos.
     * @param numero_plazas El número total de plazas que debe tener el parking.
     */
    public Parking(Connection conexion, int numero_plazas) {
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
        } else {
            System.out.println("Ya se definió el tamaño del parking con respecto a la cantidad de plazas disponibles.");
        }
    }

    /**
     * Obtiene el DAO para la gestión de vehículos.
     *
     * @return La instancia de {@link VehiculoDAO} utilizada en este parking.
     */
    public VehiculoDAO getVehiculoDAO() {
        return vehiculoDAO;
    }

    /**
     * Obtiene el DAO para la gestión de plazas.
     *
     * @return La instancia de {@link PlazaDAO} utilizada en este parking.
     */
    public PlazaDAO getPlazaDAO() {
        return plazaDAO;
    }

    /**
     * Obtiene el DAO para la gestión de tickets.
     *
     * @return La instancia de {@link TicketDAO} utilizada en este parking.
     */
    public TicketDAO getTicketDAO() {
        return ticketDAO;
    }

    /**
     * Obtiene el número total de plazas en el parking.
     *
     * @return El número total de plazas del parking.
     */
    public Integer getNumeroDePlazas() {
        return NUMERO_DE_PLAZAS;
    }

    /**
     * Obtiene una lista de todos los vehículos registrados en el parking.
     *
     * @return Una {@link ArrayList} de {@link Vehiculo} con todos los vehículos registrados.
     */
    public ArrayList<Vehiculo> getVehiculosRegistrados() {
        return vehiculosRegistrados;
    }

    /**
     * Obtiene una lista de todas las plazas del parking.
     *
     * @return Una {@link ArrayList} de {@link Plaza} con todas las plazas del parking.
     */
    public ArrayList<Plaza> getPlazas() {
        return listadoPlazas;
    }

    /**
     * Obtiene el historial de tickets del parking, incluyendo entradas y salidas.
     *
     * @return Una {@link ArrayList} de {@link Ticket} con todos los tickets históricos del parking.
     */
    public ArrayList<Ticket> getHistoricoTickets() {
        return historicoTickets;
    }

    /**
     * Obtiene una lista de todas las plazas disponibles en el parking.
     *
     * @return Una {@link List} de {@link Plaza} con las plazas disponibles en el parking.
     */
    public List<Plaza> getAvaiblePlaces() {
        return listadoPlazas.stream()
                .filter(Plaza::isDisponible) // Filtra las plazas disponibles
                .toList();
    }

    /**
     * Crea un vehículo del tipo especificado con la matrícula proporcionada.
     *
     * @param matricula La matrícula del vehículo a crear.
     * @param tipo El tipo de vehículo a crear.
     * @return Un objeto de la clase {@link Vehiculo} correspondiente al tipo especificado.
     * @throws IllegalArgumentException Si el tipo de vehículo es desconocido.
     */
    public Vehiculo creaVehiculoSegunTipo(String matricula, TipoVehiculo tipo) {
        Vehiculo vehiculo;
        switch (tipo) {
            case Autobus -> vehiculo = new Autobus(matricula);
            case Coche -> vehiculo = new Coche(matricula);
            case Moto -> vehiculo = new Moto(matricula);
            case Furgoneta -> vehiculo = new Furgoneta(matricula);
            default -> throw new IllegalArgumentException("Tipo de vehículo desconocido");
        }
        return vehiculo;
    }

    /**
     * Crea un vehículo del tipo especificado con el país proporcionado.
     *
     * @param pais El país asociado con el vehículo a crear.
     * @param tipo El tipo de vehículo a crear.
     * @return Un objeto de la clase {@link Vehiculo} correspondiente al tipo especificado.
     * @throws IllegalArgumentException Si el tipo de vehículo es desconocido.
     */
    public Vehiculo creaVehiculoSegunPais(Paises pais, TipoVehiculo tipo) {
        Vehiculo vehiculo;
        switch (tipo) {
            case Autobus -> vehiculo = new Autobus(pais);
            case Coche -> vehiculo = new Coche(pais);
            case Moto -> vehiculo = new Moto(pais);
            case Furgoneta -> vehiculo = new Furgoneta(pais);
            default -> throw new IllegalArgumentException("Tipo de vehículo desconocido");
        }
        return vehiculo;
    }
    /**
     * Gestiona la entrada de un vehículo al estacionamiento.
     *
     * Verifica si el estacionamiento está completo usando el método {@link #isComplete()}.
     * Si está completo, intenta registrar el vehículo en la base de datos con
     * {@code vehiculoDAO.creaVehiculo(vehiculo)}. Si el registro es exitoso,
     * agrega el vehículo a la lista de vehículos registrados. Si el registro falla
     * o el estacionamiento no está completo, lanza una excepción.
     *
     * @param vehiculo El vehículo que intenta entrar al estacionamiento.
     * @throws IllegalArgumentException si el estacionamiento está completo o si el vehículo no se puede registrar.
     */
    public void entradaParking(Vehiculo vehiculo) {
        //Si está completo no permitira ninguna entrada
        if (isComplete()) {
            // Se crea un nuevo vehículo y se registra en la base de datos (si no está ya registrado)
            boolean isCreated = vehiculoDAO.creaVehiculo(vehiculo);
            if (isCreated)
                vehiculosRegistrados.add(vehiculo);
            else
                throw new IllegalArgumentException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " no se ha registrado en la base de datos.");
        }
        else
            throw new IllegalArgumentException("PARKING COMPLETO");
    }

    /**
     * Registra la salida de un vehículo del parking.
     * <p>
     * Este método verifica que el vehículo esté registrado y actualmente activo (dentro del parking).
     * Si el vehículo no está activo, se actualiza su estado a activo. Si el vehículo no está registrado o ya está activo,
     * se lanza una excepción.
     * </p>
     *
     * @param vehiculo El vehículo que sale del parking y se desea eliminar de los registros.
     * @throws IllegalArgumentException Si el vehículo no está registrado o no se encuentra dentro del parking.
     */
    public void salidaParking(Vehiculo vehiculo) {
        // Para salir del parking, tiene que estar registrado y ACTIVO
        if (estaRegistrado(vehiculo)) {
            // Si no se encuentra dentro se actualiza su entrada y estado activo
            Plaza plaza = getPlazaByVehiculo(vehiculo);
            //Si se encuentra dentro del parking...
            if (vehiculo.isActivo()) {
                //Si se encuentra aparcado, desaparca y sale
                if (plaza != null)
                    desaparcar(plaza);
                vehiculo.setActivo(false);
                vehiculoDAO.actualizaVehiculo(vehiculo);
            } else
                throw new IllegalArgumentException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " no se encuentra dentro en el parking.");
        } else
            throw new IllegalArgumentException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " no se encuentra registrado en el parking.");
    }

    /**
     * Aparca un vehículo en una plaza específica del parking.
     * <p>
     * Verifica que el número de plaza sea válido y que el vehículo esté registrado en el parking.
     * Si la plaza está disponible, se actualizan los datos de la plaza y se crea un ticket sin fecha de salida.
     * </p>
     *
     * @param numeroDePlaza Número de la plaza en la que se desea aparcar el vehículo.
     * @param vehiculo El vehículo que se desea aparcar en la plaza.
     * @throws IllegalArgumentException Si el número de plaza no es válido o si el vehículo no está registrado en el parking.
     * @throws IllegalStateException Si la plaza ya está ocupada o el vehículo ya está aparcado.
     */
    public void aparcar(Integer numeroDePlaza, Vehiculo vehiculo) {
        // Si el nº de plaza es <= 0 o es mayor al máximo existente
        if (numeroDePlaza <= 0 || numeroDePlaza > NUMERO_DE_PLAZAS) {
            throw new IllegalArgumentException("El número de plaza " + numeroDePlaza + " no es válido." +
                    "\nEl parking actual consta de " + NUMERO_DE_PLAZAS + " plazas.");
        }
        // Si el vehículo es nulo, o la lista que registra los vehículos no la contiene
        if (vehiculo == null || !vehiculosRegistrados.contains(vehiculo)) {
            throw new IllegalArgumentException("El vehículo proporcionado con matrícula " + vehiculo.getMATRICULA() + " no ha entrado en el parking.");
        }

        // Se obtiene el objeto plaza a través de su número
        Plaza plaza = obtenerPlaza(numeroDePlaza);

        // Si la plaza está disponible, se actualizan los datos de la plaza y se genera un ticket sin fecha de salida
        if (plaza.isDisponible() && !estaAparcado(vehiculo) && vehiculo.isActivo()) {
            plaza.setDisponible(false);
            plaza.setMatriculaVehiculo(vehiculo.getMATRICULA());

            // Se actualiza la plaza en la base de datos
            plazaDAO.actualizaPlaza(plaza);

            // Se crea un nuevo Ticket sin fecha de salida
            Ticket nuevo = new Ticket(vehiculo.getMATRICULA(), plaza.getNUMERODEPLAZA(), LocalDateTime.now(), null);
            ticketDAO.creaTicket(nuevo);
            //Se recumera el último Ticket generado con la matrícula proporcionada, en este punto, se recupera con un ID != 0
            historicoTickets.add(ticketDAO.getTicketByMatricula(vehiculo.getMATRICULA()));

        } else {
            if (!plaza.isDisponible())
                throw new IllegalStateException("La plaza " + plaza.getNUMERODEPLAZA() + " ya está ocupada por el vehículo con matrícula " + plaza.getMatriculaVehiculo());
            else if(estaAparcado(vehiculo))
                throw new IllegalStateException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " ya se encuentra aparcado en la plaza " + getPlazaByVehiculo(vehiculo).getNUMERODEPLAZA());
            else
                throw new IllegalStateException("El vehículo con matrícula " + vehiculo.getMATRICULA() + " no se encuentra dentro del parking");
        }
    }

    /**
     * Desaparca un vehículo de una plaza específica del parking.
     * <p>
     * Libera una plaza ocupada, actualiza el estado de la plaza y registra la fecha de salida del vehículo en el ticket correspondiente.
     * </p>
     *
     * @param plaza La plaza de la que se desea desaparcar el vehículo.
     * @throws IllegalStateException Si la plaza no tiene un vehículo aparcado.
     */
    public void desaparcar(Plaza plaza) {
        // Si la plaza tiene asignado un vehículo, procede a desaparcarlo
        if (plaza.getMatriculaVehiculo() != null) {
            plaza.setDisponible(true);

            // Se recupera el ticket asociado a la plaza
            int id_ticket = ticketDAO.getTicketByMatricula(plaza.getMatriculaVehiculo()).getID();
            Ticket ticket = null;

            //Bucle para encontrar el ticket del histórico de tickets
            for(Ticket t : historicoTickets){
                if (t.getID() == id_ticket) {
                    ticket = t;
                    break;
                }
            }

            plaza.setMatriculaVehiculo(null);

            // Se actualiza la plaza en la base de datos
            plazaDAO.actualizaPlaza(plaza);

            // Se actualiza la fecha de salida en el ticket correspondiente
            ticket.setFechaSalida(LocalDateTime.now());

            // Se actualiza el ticket en la base de datos para registrar la fecha de salida
            ticketDAO.actualizaTicket(ticket);

        } else
            throw new IllegalStateException("La plaza " + plaza.getNUMERODEPLAZA() + " no tiene un vehículo aparcado.");
    }

//    public Double calculaPrecio(Ticket ticket) {
//
//    }

    /**
     * Obtiene una lista de vehículos que se encuentran actualmente dentro del parking.
     * <p>
     * Filtra los vehículos registrados para obtener aquellos cuyo estado es activo (es decir, actualmente estacionados en el parking).
     * Los vehículos activos son aquellos cuyo atributo {@code activo} es {@code true}.
     * </p>
     *
     * @return Una {@link ArrayList} de {@link Vehiculo} que contiene todos los vehículos activos,
     *         es decir, aquellos que están actualmente dentro del parking.
     */
    public ArrayList<Vehiculo> getVehiculosActivos() {
        // Se recuperan todos los vehículos activos
        List<Vehiculo> vehiculosActivos = vehiculosRegistrados.stream()
                .filter(Vehiculo::isActivo)
                .toList();

        return new ArrayList<>(vehiculosActivos);
    }

    /**
     * Obtiene una lista de vehículos que están actualmente aparcados en el parking.
     * <p>
     * Filtra las plazas ocupadas (no disponibles) y recupera el vehículo correspondiente para cada una de esas plazas.
     * </p>
     *
     * @return Una {@link ArrayList} de {@link Vehiculo} con todos los vehículos que están
     *         aparcados en el parking.
     */
    public ArrayList<Vehiculo> getVehiculosAparcados() {
        // Se recuperan todas las plazas que tienen un vehículo aparcado
        List<Plaza> plazasAparcadas = listadoPlazas.stream()
                .filter(p -> !p.isDisponible())
                .toList();

        ArrayList<Vehiculo> result = new ArrayList<>();

        for (Plaza p : plazasAparcadas) {
            result.add(getVehiculoByMatricula(p.getMatriculaVehiculo()));
        }
        return result;
    }

    /**
     * Busca un vehículo en la lista de vehículos registrados que tenga la matrícula especificada.
     * <p>
     * Si no se encuentra un vehículo con la matrícula proporcionada, se lanza una excepción.
     * </p>
     *
     * @param matricula La matrícula del vehículo que se desea buscar.
     * @return El objeto {@link Vehiculo} que tiene la matrícula especificada.
     * @throws RuntimeException Si no se encuentra ningún vehículo con la matrícula especificada.
     */
    public Vehiculo getVehiculoByMatricula(String matricula) {
        return vehiculosRegistrados.stream()
                .filter(v -> v.getMATRICULA().equalsIgnoreCase(matricula))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se ha encontrado ningún vehículo con matrícula " + matricula));
    }

    /**
     * Obtiene la plaza de aparcamiento asociada a un vehículo dado.
     * <p>
     * Recorre la lista de plazas de aparcamiento y busca la plaza que tiene un vehículo con la matrícula especificada.
     * Si no se encuentra ninguna plaza asociada al vehículo, devuelve {@code null}.
     * </p>
     *
     * @param vehiculo El vehículo cuya plaza de aparcamiento se desea encontrar.
     * @return La plaza de aparcamiento asociada al vehículo dado, o {@code null} si no se encuentra ninguna plaza.
     */
    public Plaza getPlazaByVehiculo(Vehiculo vehiculo) {
        return listadoPlazas.stream()
                .filter(p -> p.getMatriculaVehiculo() != null && p.getMatriculaVehiculo().equalsIgnoreCase(vehiculo.getMATRICULA()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Verifica si el vehículo especificado está registrado en la lista de vehículos registrados.
     * <p>
     * Utiliza un stream para recorrer la colección de vehículos registrados y verifica si existe
     * al menos un vehículo en la lista cuya matrícula coincida con la matrícula del vehículo proporcionado.
     * </p>
     *
     * @param vehiculo El vehículo que se desea verificar si está registrado.
     * @return {@code true} si el vehículo está registrado en la lista, {@code false} en caso contrario.
     */
    private boolean estaRegistrado(Vehiculo vehiculo) {
        return vehiculosRegistrados.stream()
                .anyMatch(v -> v.getMATRICULA().equalsIgnoreCase(vehiculo.getMATRICULA()));
    }

    /**
     * Verifica si un vehículo está aparcado en alguna plaza.
     * <p>
     * Obtiene la plaza asociada al vehículo y verifica si no es nula, indicando que el vehículo está aparcado.
     * </p>
     *
     * @param vehiculo El vehículo que se desea verificar.
     * @return {@code true} si el vehículo está aparcado, {@code false} en caso contrario.
     */
    private boolean estaAparcado(Vehiculo vehiculo) {
        Plaza plaza = getPlazaByVehiculo(vehiculo);
        // Si no es nula, es que está aparcado
        return plaza != null;
    }

    /**
     * Obtiene una plaza específica del parking a partir de su número.
     * <p>
     * Busca en la lista de plazas del parking y devuelve el objeto Plaza correspondiente al número especificado.
     * Si no se encuentra una plaza con el número dado, se lanza una excepción.
     * </p>
     *
     * @param numeroDePlaza El número de la plaza que se desea obtener.
     * @return El objeto Plaza correspondiente al número especificado.
     * @throws IllegalArgumentException Si no se encuentra una plaza con el número especificado.
     */
    public Plaza obtenerPlaza(int numeroDePlaza) {
        try {
            return listadoPlazas.get(numeroDePlaza - 1);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encuentra una plaza con el número " + numeroDePlaza);
        }
    }

    /**
     * Verifica si todos los espacios de estacionamiento (Plazas) en la lista están ocupados.
     *
     * Este método itera y verifica si alguna de las plazas está disponible.
     *
     * @return {@code true} si todos los espacios de estacionamiento están ocupados,
     * {@code false} en caso contrario.
     */
    public boolean isComplete() {
        return listadoPlazas.stream()
                .noneMatch(Plaza::isDisponible);
    }

    /**
     * Muestra el estado actual del parking en la consola.
     * <p>
     * Imprime en la salida estándar información relevante sobre el estado del parking, incluyendo:
     * el número total de plazas, el número de plazas disponibles, la cantidad de vehículos registrados y el historial de tickets.
     * Además, lista los detalles de cada plaza, vehículo y ticket registrado en el sistema.
     * </p>
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
