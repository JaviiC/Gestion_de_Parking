package GESTION_DE_PARKING;

public class Main {

    public static void main(String[] args) {

        // Crear el WindowManager
        WindowManager manager = new WindowManager();
        manager.addWindow("accesoBBDD", new GUI_AccesoBBDD(manager));

    }

}
/*
  MEJORAS A IMPLEMENTAR

- Gestión de entrada al Parking de un Vehículo que está registrado en la Base de Datos
y quiere entrar nuevamente al parking.
- Corregir la creación de los Vehículos
 */