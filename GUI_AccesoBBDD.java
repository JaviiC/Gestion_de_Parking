package GESTION_DE_PARKING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Ventana de acceso para conectar con la base de datos y gestionar el parking.
 * <p>
 * Esta clase representa una interfaz gráfica de usuario (GUI) que permite a los usuarios introducir credenciales
 * para conectarse a la base de datos. Una vez autenticados, se crea un objeto {@link Parking} y se abre la ventana de gestión del parking.
 * </p>
 */
public class GUI_AccesoBBDD extends JFrame {

    /**
     * URL de la base de datos a la que se conecta.
     */
    private final String URL = "jdbc:mysql://localhost:3333/parking";

    /**
     * Número total de plazas en el parking.
     */
    private final Integer PLAZAS_TOTALES = 500;

    /**
     * Administrador de las ventanas de la aplicación.
     */
    private final WindowManager windowManager;

    /**
     * Panel principal de la interfaz gráfica.
     */
    private JPanel MainPanel;

    /**
     * Campo para introducir la contraseña de acceso.
     */
    private JPasswordField PasswordAccess;

    /**
     * Campo para introducir el nombre de usuario.
     */
    private JTextField UserAccess;

    /**
     * Botón para iniciar el proceso de acceso.
     */
    private JButton AccessButton;

    /**
     * Barra de progreso para mostrar el estado de la conexión.
     */
    private JProgressBar progressBar;

    /**
     * Objeto {@link Parking} que se crea al conectar con la base de datos.
     */
    private Parking parking;

    /**
     * Constructor que inicializa la interfaz gráfica y configura los componentes.
     *
     * @param windowManager El {@link WindowManager} para gestionar la navegación entre diferentes vistas.
     */
    public GUI_AccesoBBDD(WindowManager windowManager) {
        this.windowManager = windowManager;
        // Configuración inicial de la ventana
        setTitle("Acceso a la BBDD: GESTIÓN DE PARKING");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setContentPane(MainPanel);
        setVisible(true);

        // Configurar la barra de progreso
        progressBar.setIndeterminate(false);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setVisible(false);

        // Configurar el botón de acceso
        AccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAccess();
            }
        });
    }

    /**
     * Obtiene el objeto {@link Parking} creado después de una conexión exitosa.
     *
     * @return El objeto {@link Parking}.
     */
    public Parking getParking() {
        return parking;
    }

    /**
     * Maneja el proceso de autenticación del usuario y la conexión a la base de datos.
     * <p>
     * Muestra una barra de progreso mientras se intenta conectar a la base de datos. Si la conexión es exitosa,
     * se crea un objeto {@link Parking} y se abre la ventana de gestión del parking. En caso de error, muestra un mensaje de error.
     * </p>
     */
    private void handleAccess() {
        // Mostrar la barra de progreso
        progressBar.setVisible(true);
        progressBar.setValue(0);

        // Crear un {@link SwingWorker} para manejar la conexión y la creación del objeto Parking
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                String user = UserAccess.getText();
                String password = new String(PasswordAccess.getPassword());

                // Simular progreso de la carga
                int steps = 100;
                for (int i = 0; i <= steps; i++) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Publicar el progreso en el hilo de eventos de Swing
                    publish(i);

                    // Intentar conectar a la base de datos solo cuando el progreso está completo
                    if (i == steps) {
                        if (authenticateUser(user, password)) {
                            try {
                                System.out.println("Intentando conectar a la base de datos...");
                                Connection conexion = DriverManager.getConnection(URL, user, password);
                                System.out.println("Conexión exitosa. Creando objeto Parking...");
                                parking = new Parking(conexion, PLAZAS_TOTALES);
                                System.out.println("Objeto Parking creado exitosamente.");

                                // Abre la ventana de gestión del parking
                                SwingUtilities.invokeLater(() -> {
                                    GUI_GestionParking gestionParking = new GUI_GestionParking(windowManager, parking);
                                    windowManager.addWindow("gestionParking", gestionParking);
                                    windowManager.switchWindow("accesoBBDD", "gestionParking");
                                });
                            } catch (SQLException ex) {
                                System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                            }
                        } else {
                            // Mostrar el mensaje de error en el hilo de eventos de Swing
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(GUI_AccesoBBDD.this, "ERROR: Credenciales incorrectas o no se pudo acceder a la base de datos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                // Actualizar el valor de la barra de progreso
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
            }

            @Override
            protected void done() {
                // Ocultar la barra de progreso si la tarea se completó
                progressBar.setVisible(false);
            }
        };

        worker.execute(); // Ejecutar el {@link SwingWorker}
    }

    /**
     * Intenta autenticar al usuario con las credenciales proporcionadas.
     *
     * @param user El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return {@code true} si la autenticación es exitosa, {@code false} en caso contrario.
     */
    private boolean authenticateUser(String user, String password) {
        try (Connection connection = DriverManager.getConnection(URL, user, password)) {
            // Si la conexión es exitosa, devolver true
            return true;
        } catch (SQLException e) {
            // Manejar error de conexión
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para crear componentes personalizados.
     * <p>
     * Este método se utiliza para inicializar componentes gráficos personalizados.
     * Se puede dejar vacío si no se necesitan componentes personalizados.
     * </p>
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
