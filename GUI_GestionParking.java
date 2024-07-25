package GESTION_DE_PARKING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * {@code GUI_GestionParking} es la clase principal para la gestión del GUI del sistema de gestión de parking.
 * Esta clase extiende de {@link JFrame} e incluye todos los componentes necesarios para desarrollar la interfaz gráfica.
 */

public class GUI_GestionParking extends JFrame {

    private JPanel MainPanel;
    private JPanel HeaderPanel;
    private JComboBox ComboTipo1;
    private JTextField MatriculaDeRegistro;
    private JComboBox ComboTipo2;
    private JComboBox ComboPais;
    private JButton BotonRegistroPorMatricula;
    private JButton BotonRegistroPorNacionalidad;
    private JTextField matriculaEntrada;
    private JTextField matriculaSalida;
    private JPanel BusquedasPanel;
    private JButton botonRegistroSalida;
    private JTextField matriculaAparca;
    private JTextField plazaAparca;
    private JButton BotonRegistroAparca;
    private JTextField plazaDesaparca;
    private JButton botonDesaparca;
    private JButton botonMuestraTodosLosVehiculos;
    private JButton botonMuestraVehiculosAparcados;
    private JButton botonAparcamientosDisponibles;
    private JComboBox comboAvanzadoPais;
    private JButton botonAvanzadoPais;
    private JComboBox comboAvanzadoTipo;
    private JButton botonAvanzadoTipo;
    private JButton botonHistoricoDeTickets;
    private JButton botonTicketsHoy;
    private JComboBox comboBox1;
    private JButton botonMostrarVehiculosActivos;
    private WindowManager windowManager;

    private Parking parking;

    /**
     * Constructor para la clase {@code GUI_GestionParking}.
     *
     * @param windowManager instancia de {@link WindowManager} para manejar las ventanas.
     * @param parking instancia de {@link Parking} para gestionar las operaciones de parking.
     */
    public GUI_GestionParking(WindowManager windowManager, Parking parking) {
        this.windowManager = windowManager;
        this.parking = parking;

        // Crear la interfaz gráfica
        setTitle("Gestión de Parking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setContentPane(MainPanel);
        setVisible(false);

        BotonRegistroPorMatricula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarVehiculoPorMatricula();
            }
        });

        BotonRegistroPorNacionalidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarVehiculoPorNacionalidad();
            }
        });

        botonMuestraTodosLosVehiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTodosLosVehiculos();
            }
        });

        botonRegistroSalida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarSalidaVehiculo();
            }
        });

        BotonRegistroAparca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarAparcamiento();
            }
        });

        botonMuestraVehiculosAparcados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculosAparcados();
            }
        });

        botonDesaparca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desaparcarVehiculo();
            }
        });

        botonMostrarVehiculosActivos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculosActivos();
            }
        });

        botonAvanzadoPais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculosPorPais();
            }
        });

        botonAvanzadoTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculosPorTipo();
            }
        });

        botonHistoricoDeTickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistoricoTickets();
            }
        });

        botonAparcamientosDisponibles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAparcamientosDisponibles();
            }
        });

    }
//______________________________________________________________________________________________________________________

    /**
     * Registra un vehículo en el sistema utilizando su matrícula.
     * <p>
     * El método obtiene la matrícula del campo de texto {@code MatriculaDeRegistro} y el tipo de vehículo
     * seleccionado en {@code ComboTipo1}. A continuación, intenta crear el vehículo según el tipo seleccionado
     * y registrarlo en el sistema de aparcamiento utilizando
     * {@link Parking#creaVehiculoSegunTipo(String, TipoVehiculo)} y {@link Parking#entradaParking(Vehiculo)}.
     * </p>
     * <p>
     * Si no se selecciona un tipo de vehículo válido, muestra un mensaje de advertencia. Si el formato de
     * la matrícula es incorrecto, muestra un mensaje de error. Si el vehículo se registra correctamente,
     * muestra un mensaje de confirmación.
     * </p>
     */
    private void registrarVehiculoPorMatricula() {
        String matricula = MatriculaDeRegistro.getText();
        Vehiculo vehiculo;
        TipoVehiculo tipo = null;

        try{
            tipo = TipoVehiculo.valueOf(ComboTipo1.getSelectedItem().toString());
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Asegurate de seleccionar un Tipo de vehiculo válido");
        }

        try {
            vehiculo = parking.creaVehiculoSegunTipo(matricula, tipo);
            try{
                parking.entradaParking(vehiculo);
                JOptionPane.showMessageDialog(this, "Vehículo registrado: " + vehiculo);
            } catch (Exception e){
                JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage());
            }

        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Asegurate de introducir una matrícula con un formato específico");
        }

    }

    /**
     * Registra un vehículo en el sistema utilizando su país de origen.
     * <p>
     * El método obtiene el tipo de vehículo seleccionado en {@code ComboTipo2} y el país seleccionado en {@code ComboPais}.
     * A continuación, intenta crear el vehículo según el país y el tipo seleccionado utilizando
     * {@link Parking#creaVehiculoSegunPais(Paises, TipoVehiculo)} y registra el vehículo en el sistema de aparcamiento
     * utilizando {@link Parking#entradaParking(Vehiculo)}.
     * </p>
     * <p>
     * Si no se selecciona un tipo de vehículo o país válido, muestra un mensaje de advertencia.
     * Si el vehículo se registra correctamente, muestra un mensaje de confirmación.
     * </p>
     */
    private void registrarVehiculoPorNacionalidad() {
        Paises pais = null;
        TipoVehiculo tipo = null;

        try{
            tipo = TipoVehiculo.valueOf(ComboTipo2.getSelectedItem().toString());
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Asegurate de seleccionar un Tipo de vehiculo válido");
        }

        try{
            pais = Paises.valueOf(ComboPais.getSelectedItem().toString());
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Asegurate de seleccionar un Pais válido");
        }

        Vehiculo vehiculo = parking.creaVehiculoSegunPais(pais, tipo);

        parking.entradaParking(vehiculo);

        // Aquí puedes agregar el código para manejar el vehículo creado,
        // como guardarlo en una base de datos o actualizar la interfaz gráfica.
        JOptionPane.showMessageDialog(this, "Vehículo registrado: " + vehiculo);
    }

    /**
     * Muestra todos los vehículos registrados en el sistema en un cuadro de diálogo.
     * <p>
     * Este método obtiene todos los vehículos a través de {@link Parking#getVehiculoDAO()#getAllVehicles()}, los ordena y
     * los muestra en un {@code JTextArea} dentro de un {@code JScrollPane}. El cuadro de diálogo se muestra utilizando
     * {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object, java.lang.String, int)}.
     * </p>
     */
    private void mostrarTodosLosVehiculos() {
        ArrayList<Vehiculo> listaVehiculos = parking.getVehiculoDAO().getAllVehicles();
        Collections.sort(listaVehiculos);
        // Convertir la lista de vehículos a un formato de texto legible
        StringBuilder vehiculosTexto = new StringBuilder("Lista de todos los vehículos registrados:\n");
        for (Vehiculo vehiculo : listaVehiculos) {
            vehiculosTexto.append(vehiculo.toString()).append("\n");
        }

        // Crear un JTextArea para mostrar la lista de vehículos
        JTextArea textArea = new JTextArea(vehiculosTexto.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0); // Para asegurarse de que el texto se muestre desde el principio

        // Crear un JScrollPane para permitir el desplazamiento vertical
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300)); // Ajusta el tamaño según tus necesidades

        // Mostrar la lista de vehículos en un JOptionPane dentro de un JScrollPane
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de todos los vehículos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Registra la salida de un vehículo del parking.
     * <p>
     * Este método obtiene la matrícula del vehículo a través de {@code matriculaSalida.getText()},
     * busca el vehículo correspondiente utilizando {@link Parking#getVehiculoByMatricula(String)},
     * y luego registra la salida del vehículo utilizando {@link Parking#salidaParking(Vehiculo)}.
     * Si la operación es exitosa, se muestra un mensaje de confirmación utilizando
     * {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object)}. En caso de error,
     * se muestra un mensaje de error.
     * </p>
     */
    private void registrarSalidaVehiculo() {
        String matricula = matriculaSalida.getText();
        try {
            Vehiculo vehiculo = parking.getVehiculoByMatricula(matricula);
            parking.salidaParking(vehiculo);
            JOptionPane.showMessageDialog(this, "El vehículo con matrícula " + vehiculo.getMATRICULA() + " ha salido del parking");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e.getMessage());
        }
    }

    /**
     * Registra el aparcamiento de un vehículo en una plaza específica.
     * <p>
     * Este método obtiene la matrícula del vehículo a través de {@code matriculaAparca.getText()},
     * y el número de plaza a través de {@code plazaAparca.getText()}. Luego, intenta convertir el número
     * de plaza a un entero. Si la conversión falla, muestra un mensaje de error utilizando
     * {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object)} y termina la ejecución.
     * Si la conversión es exitosa, busca el vehículo correspondiente utilizando {@link Parking#getVehiculoByMatricula(String)},
     * y registra el aparcamiento del vehículo utilizando {@link Parking#aparcar(Integer, Vehiculo)}.
     * Si la operación es exitosa, muestra un mensaje de confirmación utilizando
     * {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object)}. En caso de error,
     * muestra un mensaje de error.
     * </p>
     */
    private void registrarAparcamiento() {
        String matricula = matriculaAparca.getText();
        int numeroDePlaza;

        try {
            numeroDePlaza = Integer.parseInt(plazaAparca.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de introducir un número de plaza válido.");
            return;
        }

        try {
            Vehiculo vehiculo = parking.getVehiculoByMatricula(matricula);
            parking.aparcar(numeroDePlaza, vehiculo);
            JOptionPane.showMessageDialog(this, "El vehículo con matrícula " + vehiculo.getMATRICULA() + " ha aparcado en la plaza " + numeroDePlaza);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Muestra una lista de todos los vehículos activos en el parking.
     * <p>
     * Obtiene la lista de vehículos activos mediante {@link Parking#getVehiculosActivos()} y la convierte
     * en una representación de texto. Esta lista se muestra en un {@code JTextArea} dentro de un {@code JScrollPane}
     * para permitir el desplazamiento. La información se presenta en un cuadro de diálogo {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object, java.lang.String, int)}.
     * </p>
     */
    private void mostrarVehiculosActivos() {
        ArrayList<Vehiculo> vehiculosActivos = parking.getVehiculosActivos();

        // Convertir la lista de vehículos a un formato de texto legible
        StringBuilder vehiculosTexto = new StringBuilder("Lista de vehículos activos:\n");
        for (Vehiculo vehiculo : vehiculosActivos) {
            vehiculosTexto.append(vehiculo.toString()).append("\n");
        }

        // Crear un JTextArea para mostrar la lista de vehículos
        JTextArea textArea = new JTextArea(vehiculosTexto.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0); // Para asegurarse de que el texto se muestre desde el principio

        // Crear un JScrollPane para permitir el desplazamiento vertical
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300)); // Ajusta el tamaño según tus necesidades

        // Mostrar la lista de vehículos en un JOptionPane dentro de un JScrollPane
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de vehículos activos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra una lista de todos los vehículos actualmente aparcados en el parking.
     * <p>
     * Obtiene la lista de vehículos aparcados mediante {@link Parking#getVehiculosAparcados()} y la convierte
     * en una representación de texto que incluye la información de la plaza donde está aparcado cada vehículo.
     * Esta lista se muestra en un {@code JTextArea} dentro de un {@code JScrollPane} para permitir el desplazamiento.
     * La información se presenta en un cuadro de diálogo {@link JOptionPane#showMessageDialog(java.awt.Component, java.lang.Object, java.lang.String, int)}.
     * </p>
     */
    private void mostrarVehiculosAparcados() {
        ArrayList<Vehiculo> vehiculosAparcados = parking.getVehiculosAparcados();

        // Convertir la lista de vehículos a un formato de texto legible
        StringBuilder vehiculosTexto = new StringBuilder("Lista de vehículos aparcados:\n");
        for (Vehiculo vehiculo : vehiculosAparcados) {
            vehiculosTexto.append(vehiculo.toString())
                    .append(" Plaza nº")
                    .append(parking.getPlazaByVehiculo(vehiculo).getNUMERODEPLAZA())
                    .append("\n");
        }

        // Crear un JTextArea para mostrar la lista de vehículos
        JTextArea textArea = new JTextArea(vehiculosTexto.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0); // Para asegurarse de que el texto se muestre desde el principio

        // Crear un JScrollPane para permitir el desplazamiento vertical
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300)); // Ajusta el tamaño según tus necesidades

        // Mostrar la lista de vehículos en un JOptionPane dentro de un JScrollPane
        JOptionPane.showMessageDialog(this, scrollPane, "Lista de vehículos aparcados", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Libera una plaza de aparcamiento en el parking.
     * <p>
     * Obtiene el número de la plaza a liberar desde un campo de texto. Si el número no es válido, se muestra un mensaje de error.
     * Si el número es válido, se intenta liberar la plaza correspondiente. Se muestra un mensaje de confirmación si la operación
     * es exitosa o un mensaje de error en caso contrario.
     * </p>
     *
     * @see Parking#obtenerPlaza(int) Para obtener la plaza correspondiente al número.
     * @see Parking#desaparcar(Plaza) Para liberar la plaza.
     */
    private void desaparcarVehiculo() {
        int numeroDePlaza;
        try {
            numeroDePlaza = Integer.parseInt(plazaDesaparca.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de introducir un número de plaza válido.");
            return;
        }

        try {
            Plaza plaza = parking.obtenerPlaza(numeroDePlaza);
            parking.desaparcar(plaza);
            JOptionPane.showMessageDialog(this, "La plaza número " + numeroDePlaza + " se ha quedado libre.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Muestra una lista de vehículos registrados en el parking, filtrados por país.
     * <p>
     * Obtiene el país seleccionado desde un combo box. Si el país seleccionado es inválido, se muestra un mensaje de error.
     * Si el país es válido, se obtienen todos los vehículos del país seleccionado, se ordenan y se presentan en un cuadro de diálogo.
     * </p>
     *
     * @see Parking#getVehiculoDAO() Para acceder al DAO de vehículos.
     * @see VehiculoDAO#getCountryGroup(Paises) Para obtener vehículos filtrados por país.
     * @see Paises Para los valores válidos de países.
     */
    private void mostrarVehiculosPorPais() {
        Paises pais;

        try {
            pais = Paises.valueOf(comboAvanzadoPais.getSelectedItem().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de seleccionar un País válido.");
            return;
        }

        ArrayList<Vehiculo> listaVehiculos = parking.getVehiculoDAO().getCountryGroup(pais);
        Collections.sort(listaVehiculos);
        StringBuilder vehiculosTexto = new StringBuilder("Lista de vehículos del país " + pais + ":\n");
        for (Vehiculo vehiculo : listaVehiculos) {
            vehiculosTexto.append(vehiculo.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(vehiculosTexto.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Lista de vehículos por país", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra una lista de vehículos registrados en el parking, filtrados por tipo de vehículo.
     * <p>
     * Obtiene el tipo de vehículo seleccionado desde un combo box. Si el tipo de vehículo seleccionado es inválido, se muestra un mensaje de error.
     * Si el tipo es válido, se obtienen todos los vehículos del tipo seleccionado, se ordenan y se presentan en un cuadro de diálogo.
     * </p>
     *
     * @see Parking#getVehiculoDAO() Para acceder al DAO de vehículos.
     * @see VehiculoDAO#getTypeGroup(TipoVehiculo) Para obtener vehículos filtrados por tipo.
     * @see TipoVehiculo Para los valores válidos de tipos de vehículos.
     */
    private void mostrarVehiculosPorTipo() {
        TipoVehiculo tipoVehiculo;

        try {
            tipoVehiculo = TipoVehiculo.valueOf(comboAvanzadoTipo.getSelectedItem().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Asegúrate de seleccionar un Tipo de vehículo válido.");
            return;
        }

        ArrayList<Vehiculo> listaVehiculos = parking.getVehiculoDAO().getTypeGroup(tipoVehiculo);
        Collections.sort(listaVehiculos);
        StringBuilder vehiculosTexto = new StringBuilder("Lista de vehículos del tipo " + tipoVehiculo + ":\n");
        for (Vehiculo vehiculo : listaVehiculos) {
            vehiculosTexto.append(vehiculo.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(vehiculosTexto.toString());
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Lista de vehículos por tipo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un historial de tickets en una nueva ventana.
     * <p>
     * Crea un {@code JFrame} que presenta una lista de tickets. Cada ticket se muestra en un panel con detalles como ID, matrícula, número de plaza, fecha de entrada y fecha de salida.
     * Los tickets están organizados verticalmente en un {@code JPanel} y se pueden desplazar dentro de un {@code JScrollPane}.
     * </p>
     *
     * @see Parking#getHistoricoTickets() Para obtener la lista de tickets históricos.
     * @see Ticket Para la estructura de los tickets.
     * @see JScrollPane Para permitir el desplazamiento de la lista de tickets.
     */
    private void mostrarHistoricoTickets() {
        // Crear un JFrame para mostrar el histórico de tickets
        JFrame ventanaHistorico = new JFrame("Histórico de Tickets");
        ventanaHistorico.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Obtener las dimensiones de la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension pantallaSize = toolkit.getScreenSize();

        // Crear un panel principal con un GridBagLayout para mostrar los tickets uno a uno
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Añadir un margen entre los tickets
        gbc.fill = GridBagConstraints.NONE; // No llenar el espacio disponible
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los tickets
        gbc.weightx = 1.0; // Distribuir el espacio horizontalmente
        gbc.weighty = 1.0; // Distribuir el espacio verticalmente

        // Obtener el histórico de tickets desde el parking
        ArrayList<Ticket> historicoTickets = parking.getHistoricoTickets(); // Suponiendo que el método getHistoricoTickets() devuelve el histórico de tickets

        int numTickets = historicoTickets.size();

        // Crear el formateador de fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

        for (int row = 0; row < numTickets; row++) {
            // Crear un panel para el ticket de la fila actual
            JPanel panelTicket = new JPanel();
            panelTicket.setLayout(new BoxLayout(panelTicket, BoxLayout.Y_AXIS));
            panelTicket.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            panelTicket.setBackground(Color.WHITE);
            panelTicket.setPreferredSize(new Dimension(210, 315)); // Tamaño del ticket (ancho x alto)

            Ticket ticket = historicoTickets.get(row);

            // Crear un panel para la información del ticket
            JPanel panelInfo = new JPanel();
            panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
            panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Añadir un margen alrededor de la información
            panelInfo.setBackground(Color.WHITE);

            // Añadir el título del ticket al panelInfo
            JLabel etiquetaTitulo = new JLabel("TICKET ID: " + ticket.getID());
            etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 22)); // Tamaño de fuente para el título
            etiquetaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(etiquetaTitulo);

            // Línea divisoria entre el título y la información del ticket
            JSeparator separadorTitulo = new JSeparator();
            separadorTitulo.setPreferredSize(new Dimension(360, 2)); // Ancho y grosor del separador
            panelInfo.add(separadorTitulo);

            // Añadir la información del ticket al panelInfo
            JLabel etiquetaMatricula = new JLabel("Matrícula:");
            etiquetaMatricula.setFont(new Font("Arial", Font.BOLD, 18)); // Tamaño de fuente para la etiqueta
            etiquetaMatricula.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(etiquetaMatricula);

            JLabel valorMatricula = new JLabel(ticket.getMATRICULA());
            valorMatricula.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente para el contenido
            valorMatricula.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(valorMatricula);

            JLabel etiquetaNumPlaza = new JLabel("Plaza:");
            etiquetaNumPlaza.setFont(new Font("Arial", Font.BOLD, 18)); // Tamaño de fuente para la etiqueta
            etiquetaNumPlaza.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(etiquetaNumPlaza);

            JLabel valorNumPlaza = new JLabel(String.valueOf(ticket.getNUM_PLAZA()));
            valorNumPlaza.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente para el contenido
            valorNumPlaza.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(valorNumPlaza);

            panelInfo.add(Box.createVerticalStrut(18)); // Espacio vertical

            // Línea divisoria entre la información de matrícula/plaza y las fechas
            JSeparator separadorFechas = new JSeparator();
            separadorFechas.setPreferredSize(new Dimension(360, 2)); // Ancho y grosor del separador
            panelInfo.add(separadorFechas);

            panelInfo.add(Box.createVerticalStrut(18)); // Espacio vertical

            // Añadir las fechas al panelInfo
            JLabel etiquetaInicio = new JLabel("Fecha Entrada:");
            etiquetaInicio.setFont(new Font("Arial", Font.BOLD, 18)); // Tamaño de fuente para la etiqueta
            etiquetaInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(etiquetaInicio);

            JLabel valorInicio = new JLabel(ticket.getFECHA_ENTRADA() != null ? ticket.getFECHA_ENTRADA().format(formatter) : "--/--/--   --:--:--");
            valorInicio.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente para el contenido
            valorInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(valorInicio);

            JLabel etiquetaSalida = new JLabel("Fecha Salida:");
            etiquetaSalida.setFont(new Font("Arial", Font.BOLD, 18)); // Tamaño de fuente para la etiqueta
            etiquetaSalida.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(etiquetaSalida);

            JLabel valorSalida = new JLabel(ticket.getFechaSalida() != null ? ticket.getFechaSalida().format(formatter) : "--/--/--   --:--:--");
            valorSalida.setFont(new Font("Arial", Font.PLAIN, 16)); // Tamaño de fuente para el contenido
            valorSalida.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelInfo.add(valorSalida);

            // Agregar el panelInfo al panelTicket
            panelTicket.add(panelInfo);

            // Agregar el panel del ticket al panel principal
            gbc.gridx = 0;
            gbc.gridy = row;
            panelPrincipal.add(panelTicket, gbc);
        }

        // Colocar el panelPrincipal en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setPreferredSize(pantallaSize); // Ajusta el tamaño del JScrollPane al tamaño de la pantalla

        // Configurar el JScrollPane para mejor experiencia de desplazamiento
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Incremento de desplazamiento en píxeles

        // Agregar el JScrollPane al JFrame
        ventanaHistorico.getContentPane().add(scrollPane);

        // Maximizar la ventana para que ocupe toda la pantalla
        ventanaHistorico.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ventanaHistorico.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
        ventanaHistorico.setVisible(true);
    }

    /**
     * Muestra una ventana con la lista de aparcamientos disponibles y ocupados.
     * <p>
     * Crea un {@code JFrame} que presenta un panel con una disposición de plazas de aparcamiento. Cada plaza se muestra en un panel con un color verde si está disponible o rojo si está ocupada.
     * Las plazas están organizadas en filas de 10 con espacio entre cada fila para simular el diseño del aparcamiento. La ventana incluye un {@code JScrollPane} para permitir el desplazamiento.
     * </p>
     *
     * @see Parking#getPlazas() Para obtener la lista de todas las plazas.
     * @see Plaza Para la estructura de las plazas de aparcamiento.
     * @see Vehiculo Para obtener información sobre el vehículo en una plaza ocupada.
     */
    private void mostrarAparcamientosDisponibles() {
        // Crear un JFrame para mostrar las plazas
        JFrame ventanaPlazas = new JFrame("Aparcamientos Disponibles");
        ventanaPlazas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Obtener las dimensiones de la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension pantallaSize = toolkit.getScreenSize();
        ventanaPlazas.setSize(pantallaSize); // Ajustar el tamaño del JFrame al tamaño de la pantalla

        // Crear un panel principal con un GridBagLayout para simular la estructura del parking
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Obtener las plazas desde el parking
        ArrayList<Plaza> plazas = parking.getPlazas(); // Suponiendo que el método getPlazas() devuelve todas las plazas

        int numRows = (int) Math.ceil(plazas.size() / 10.0); // Calcula el número total de filas
        int carrilHeight = 240; // Altura fija para cada carril

        // Crear filas de plazas en el panel principal
        for (int row = 0; row < numRows; row++) {
            // Crear un panel para las plazas de la fila actual
            JPanel panelFila = new JPanel();
            panelFila.setLayout(new GridLayout(1, 10, 5, 5)); // 1 fila, 10 columnas
            panelFila.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelFila.setPreferredSize(new Dimension(pantallaSize.width, 120)); // Altura fija para cada fila

            for (int col = 0; col < 10; col++) {
                int index = row * 10 + col;
                if (index < plazas.size()) {
                    Plaza plaza = plazas.get(index);
                    JPanel panelPlaza = new JPanel();
                    panelPlaza.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelPlaza.setPreferredSize(new Dimension(100, 100)); // Tamaño adecuado para visibilidad
                    panelPlaza.setLayout(new BorderLayout());

                    if (plaza.isDisponible()) {
                        panelPlaza.setBackground(Color.GREEN);
                    } else {
                        panelPlaza.setBackground(Color.RED);
                    }

                    // Agregar un JLabel con el número de plaza en la parte superior
                    JLabel etiquetaNumeroPlaza = new JLabel(String.valueOf(plaza.getNUMERODEPLAZA()), SwingConstants.CENTER);
                    etiquetaNumeroPlaza.setForeground(Color.WHITE);
                    etiquetaNumeroPlaza.setFont(new Font("Arial", Font.BOLD, 16)); // Ajusta el tamaño de fuente para mejor visibilidad
                    etiquetaNumeroPlaza.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Añadir margen
                    panelPlaza.add(etiquetaNumeroPlaza, BorderLayout.NORTH);

                    // Agregar un JLabel con la matrícula del vehículo en la parte inferior si la plaza no está disponible
                    if (!plaza.isDisponible()) {
                        Vehiculo vehiculo = parking.getVehiculoByMatricula(plaza.getMatriculaVehiculo()); // Suponiendo que hay un método para obtener el vehículo en una plaza
                        JLabel etiquetaMatricula = new JLabel(vehiculo.getMATRICULA(), SwingConstants.CENTER);
                        etiquetaMatricula.setForeground(Color.DARK_GRAY);
                        etiquetaMatricula.setFont(new Font("Arial", Font.BOLD, 14)); // Ajusta el tamaño de fuente para mejor visibilidad
                        etiquetaMatricula.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Añadir margen
                        panelPlaza.add(etiquetaMatricula, BorderLayout.SOUTH);
                    }

                    panelFila.add(panelPlaza);
                }
            }

            // Agregar el panel de la fila al panel principal
            gbc.gridx = 0;
            gbc.gridy = row * 2; // Multiplica por 2 para dejar espacio para el carril
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panelPrincipal.add(panelFila, gbc);

            // Agregar un espacio (carril) después de cada dos filas
            if (row % 2 == 1) {
                JPanel panelCarril = new JPanel();
                panelCarril.setPreferredSize(new Dimension(pantallaSize.width, 20)); // Altura del carril (espacio)
                gbc.gridx = 0;
                gbc.gridy = row * 2 + 1; // Multiplica por 2 y añade 1 para la fila del carril
                panelPrincipal.add(panelCarril, gbc);
            }
        }

        // Colocar el panelPrincipal en un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setPreferredSize(new Dimension(pantallaSize.width, pantallaSize.height)); // Ajusta el tamaño del JScrollPane según el tamaño de la pantalla

        // Configurar el JScrollPane para mejor experiencia de desplazamiento
        scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Incremento de desplazamiento en píxeles
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20); // Incremento de desplazamiento en píxeles

        // Agregar el JScrollPane al JFrame
        ventanaPlazas.getContentPane().add(scrollPane);

        ventanaPlazas.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza el JFrame para que ocupe toda la pantalla
        ventanaPlazas.setVisible(true);
    }

    private void createUIComponents() {
        // Código de creación de componentes personalizados (si es necesario)
    }

    // Getters y Setters si es necesario
}
