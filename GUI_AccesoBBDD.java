package GESTION_DE_PARKING;

import javax.swing.*;

public class GUI_AccesoBBDD extends JFrame {

    private JPanel MainPanel;
    private JPasswordField PasswordAccess;
    private JTextField UserAccess;
    private JPanel UserPanel;
    private JPanel PasswordPanel;
    private JPanel SubMainPanel;
    private JButton AccessButton;
    private JProgressBar progressBar1;

    public GUI_AccesoBBDD(){
        // Crear la interfaz gráfica
        setTitle("Gestion de Parking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(MainPanel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
