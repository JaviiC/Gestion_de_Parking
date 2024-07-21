package GESTION_DE_PARKING;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la visibilidad de múltiples ventanas JFrame.
 */
public class WindowManager {
    /**
     * Mapa que almacena las ventanas JFrame gestionadas por el WindowManager.
     * Las claves del mapa son los nombres de las ventanas, y los valores son las instancias de JFrame.
     */
    private Map<String, JFrame> windows;

    /**
     * Inicializa un nuevo WindowManager con un mapa vacío de ventanas.
     */
    public WindowManager() {
        windows = new HashMap<>();
    }

    /**
     * Agrega una nueva ventana al gestor.
     *
     * @param name   el nombre de la ventana para su identificación.
     * @param window la instancia de JFrame que se va a gestionar.
     */
    public void addWindow(String name, JFrame window) {
        windows.put(name, window);
    }

    /**
     * Muestra la ventana especificada.
     *
     * @param name el nombre de la ventana que se desea mostrar.
     * @throws IllegalArgumentException si no existe ninguna ventana con el nombre especificado.
     */
    public void showWindow(String name) {
        JFrame window = windows.get(name);
        if (window != null) {
            window.setVisible(true);
        } else {
            System.out.println("No se encontró la ventana: " + name);
        }
    }

    /**
     * Oculta la ventana especificada.
     *
     * @param name el nombre de la ventana que se desea ocultar.
     * @throws IllegalArgumentException si no existe ninguna ventana con el nombre especificado.
     */
    public void hideWindow(String name) {
        JFrame window = windows.get(name);
        if (window != null) {
            window.setVisible(false);
        } else {
            System.out.println("No se encontró la ventana: " + name);
        }
    }

    /**
     * Oculta una ventana y muestra otra.
     *
     * @param from el nombre de la ventana que se desea ocultar.
     * @param to   el nombre de la ventana que se desea mostrar.
     * @throws IllegalArgumentException si no existe ninguna ventana con los nombres especificados.
     */
    public void switchWindow(String from, String to) {
        hideWindow(from);
        showWindow(to);
    }
}
