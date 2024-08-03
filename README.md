# Sistema de Gestión de Parking

Este proyecto implementa un sistema de gestión de parking, proporcionando funcionalidades completas para manejar la entrada, salida, aparcamiento y desaparcamiento de vehículos, así como la gestión de plazas y el seguimiento de tickets. La aplicación está diseñada para optimizar las operaciones de un parking, mejorando la eficiencia y la organización.

## Tecnologías Utilizadas

- **Java**
- **JDBC**: Para la conexión a la base de datos
- **MySQL**: Como sistema de gestión de bases de datos

## Funcionalidades Principales

### Gestión de Vehículos

- **Registro de Vehículos**: Permite la entrada y el registro de vehículos en el sistema.
- **Eliminación de Vehículos**: Gestiona la salida y eliminación de vehículos del sistema.
- **Aparcamiento de Vehículos**: Asigna vehículos a plazas disponibles y genera un ticket correspondiente.
- **Desaparcamiento de Vehículos**: Libera plazas ocupadas y actualiza el ticket con la hora de salida.

### Gestión de Plazas

- **Registro y Actualización de Plazas**: Crea y mantiene el estado de las plazas de aparcamiento.
- **Consulta de Plazas Disponibles**: Proporciona información en tiempo real sobre las plazas disponibles.

### Gestión de Tickets

- **Generación de Tickets**: Crea registros de entrada y salida de vehículos.
- **Historial de Tickets**: Mantiene un registro histórico de todos los tickets generados.

## Instalación y Ejecución

### Prerrequisitos

- **Java JDK**: Asegúrate de tener instalado el JDK de Java.
- **MySQL**: Necesitarás una base de datos MySQL configurada.

### Configuración de la Base de Datos

1. Crea una base de datos en MySQL:
   ```sql
   CREATE DATABASE parking CHARACTER SET utf8mb4 COLLATE utf8mb4_es_0900_as_cs;
2. Desde la base de datos crea las tablas de Vehiculo, Plaza y Ticket:
   ```sql
   CREATE TABLE vehiculo (
    matricula VARCHAR(15) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    pais VARCHAR(20) NOT NULL,
    precioEstacionamiento DECIMAL(5,2) NOT NULL,
    activo BOOLEAN NOT NULL,
    PRIMARY KEY (matricula)
   );

   CREATE TABLE plaza (
       numero SMALLINT UNSIGNED NOT NULL,
       disponible BOOLEAN NOT NULL,
       matriculaVehiculo VARCHAR(15),
       PRIMARY KEY (numero),
       FOREIGN KEY (matriculaVehiculo) REFERENCES vehiculo(matricula) ON UPDATE CASCADE
   );

   CREATE TABLE ticket (
       id INT AUTO_INCREMENT PRIMARY KEY,
       matricula VARCHAR(15) NOT NULL,
       numeroPlaza SMALLINT UNSIGNED NOT NULL,
       fechaEntrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       fechaSalida TIMESTAMP,
       precioTotal DECIMAL(4,2),
       FOREIGN KEY (matricula) REFERENCES vehiculo(matricula) ON DELETE CASCADE,
       FOREIGN KEY (numeroPlaza) REFERENCES plaza(numero) ON DELETE CASCADE
   );

## Ejecución del Proyecto
1. Clona el repositorio:
```bash
git clone https://github.com/tu_usuario/gestion_parking.git
````
2. Navega al directorio del proyecto:
```bash
cd gestion_parking
````
3. Compila y ejecuta la aplicación:
```bash
javac -d bin src/GESTION_DE_PARKING/*.java
java -cp bin GESTION_DE_PARKING.Parking
````
# Uso del Programa
El sistema de gestión de parking ofrece una serie de métodos para interactuar con el sistema y gestionar las operaciones del parking.

## Ejemplo de Uso

```java
package APP_GESTION_DE_PARKING;

import GESTION_DE_PARKING.GUI_AccesoBBDD;
import GESTION_DE_PARKING.WindowManager;

public class Main {

    public static void main(String[] args) {

        // Crear el WindowManager
        WindowManager manager = new WindowManager();
        manager.addWindow("accesoBBDD", new GUI_AccesoBBDD(manager));

    }
}
``````


# Contribuciones
¡Las contribuciones son bienvenidas! Si tienes alguna mejora o corrección, por favor abre un "pull request".

# Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más información.


Espero que este programa te sea de gran utilidad y facilite la gestión de tu parking. ¡Gracias por utilizar mi software! 
J.C
