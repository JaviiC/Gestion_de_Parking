package GESTION_DE_PARKING;

/**
 * Clase principal que arranca la aplicación de gestión de parking.
 * <p>
 * Esta clase contiene el método {@link #main(String[])} que inicia la aplicación creando una instancia de
 * {@link WindowManager} y mostrando la ventana de acceso a la base de datos {@link GUI_AccesoBBDD}.
 * </p>
 */
public class Main {

    /**
     * Método principal que arranca la aplicación.
     * <p>
     * Este método crea una instancia de {@link WindowManager} y añade una ventana de acceso a la base de datos
     * {@link GUI_AccesoBBDD} al {@link WindowManager}. La ventana de acceso es la primera vista que los usuarios
     * ven al iniciar la aplicación.
     * </p>
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta implementación).
     */
    public static void main(String[] args) {

        // Crear el WindowManager
        WindowManager manager = new WindowManager();

        // Crear y mostrar la ventana de acceso a la base de datos
        manager.addWindow("accesoBBDD", new GUI_AccesoBBDD(manager));
    }

}
/*
  MEJORAS A IMPLEMENTAR

  - Gestión de entrada al Parking de un Vehículo que está registrado en la Base de Datos y quiere entrar nuevamente al parking.
  - Corregir la creación de los Vehículos.
*/
