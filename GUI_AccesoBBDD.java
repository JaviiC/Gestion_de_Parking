package GESTION_DE_PARKING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GUI_AccesoBBDD extends JFrame {

    // Escribir la base de datos correspondiente
    private final String URL = "jdbc:mysql://localhost:3333/parking";
    // Cantidad de plazas para el parking. Una vez creado el objeto Parking, este número no afectará a dicho objeto:
    private final Integer PLAZAS_TOTALES = 500;

    private final WindowManager windowManager;

    private JPanel MainPanel;
    private JPasswordField PasswordAccess;
    private JTextField UserAccess;
    private JButton AccessButton;
    private JProgressBar progressBar;
    private Parking parking;

    public GUI_AccesoBBDD(WindowManager windowManager) {
        this.windowManager = windowManager;
        // Crear la interfaz gráfica
        setTitle("Acceso a la BBDD: GESTIÓN DE PARKING");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setContentPane(MainPanel);
        setVisible(true);

        // Configurar la barra de progreso
        progressBar.setIndeterminate(false); // Hacer que la barra sea determinista
        progressBar.setValue(0); // Inicializar el valor de la barra de progreso
        progressBar.setStringPainted(true); // Mostrar el texto de progreso
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setVisible(false); // Inicialmente oculta

        AccessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAccess();
            }
        });
    }

    public Parking getParking() {
        return parking;
    }

    private void handleAccess() {
        // Mostrar la barra de progreso
        progressBar.setVisible(true);
        progressBar.setValue(0); // Reiniciar el valor

        // Crear un SwingWorker para manejar la conexión y la creación del objeto Parking
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                String user = UserAccess.getText();
                String password = new String(PasswordAccess.getPassword());

                // Simular progreso de la carga
                int steps = 100; // Número total de pasos para el progreso
                for (int i = 0; i <= steps; i++) {
                    try {
                        // Simular tarea en proceso
                        Thread.sleep(30); // Tiempo para simular la tarea
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

        worker.execute(); // Ejecutar el SwingWorker
    }

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
