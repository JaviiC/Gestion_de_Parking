package GESTION_DE_PARKING;

import javax.swing.*;

public class GUI_GestionParking extends JFrame{

    private JPanel MainPanel;

    public GUI_GestionParking() {
        // Crear la interfaz gráfica
        setTitle("Gestion de Parking");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(MainPanel);
    }

}
