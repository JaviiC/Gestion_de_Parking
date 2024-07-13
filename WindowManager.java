package GESTION_DE_PARKING;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class WindowManager {
    private Map<String, JFrame> windows;

    public WindowManager() {
        windows = new HashMap<>();
    }

    public void addWindow(String name, JFrame window) {
        windows.put(name, window);
    }

    public void showWindow(String name) {
        JFrame window = windows.get(name);
        if (window != null) {
            window.setVisible(true);
        } else {
            System.out.println("No se encontró la ventana: " + name);
        }
    }

    public void hideWindow(String name) {
        JFrame window = windows.get(name);
        if (window != null) {
            window.setVisible(false);
        } else {
            System.out.println("No se encontró la ventana: " + name);
        }
    }

    public void switchWindow(String from, String to) {
        hideWindow(from);
        showWindow(to);
    }

}
