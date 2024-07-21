package GESTION_DE_PARKING;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la generación y validación de matrículas según el país.
 * Esta clase proporciona métodos para crear matrículas aleatorias válidas para diferentes países
 * y para determinar el país al que pertenece una matrícula dada.
 *
 * @version 1.0
 * @see Vehiculo
 */
public class GestionMatriculas {

    /**
     * Mapa que almacena los patrones de expresiones regulares para validar las matrículas por país.
     * La clave es el país (de tipo {@link Paises}) y el valor es el patrón de expresión regular
     * que define el formato de la matrícula para ese país.
     */
    private static final Map<Paises, String> patronesPaisesMatricula = new HashMap<>();

    /**
     * Inicializa el mapa de patrones de matrículas para diferentes países.
     *
     * Este bloque estático se ejecuta una sola vez cuando la clase es cargada y se utiliza
     * para asociar cada país con su respectivo patrón de expresión regular. Los patrones
     * se utilizan para validar matrículas según el formato específico de cada país.
     */
    static {
        patronesPaisesMatricula.put(Paises.Alemania, "^[0-9]{2} [0-9]{2} [A-HJ-NP-Z]{3}$"); // KA PA 777
        patronesPaisesMatricula.put(Paises.Austria, "^[A-HJ-NP-Z]{1} [0-9]{3} [A-HJ-NP-Z]{2}$"); // K 510 BV
        patronesPaisesMatricula.put(Paises.Belgica, "^[0-9]{1}-[A-HJ-NP-Z]{3}-[0-9]{3}$"); // 1-ABC-003
        patronesPaisesMatricula.put(Paises.Bulgaria, "^[A-HJ-NP-Z]{2} [0-9]{4} [A-HJ-NP-Z]{2}$"); // CA 7845 XC
        patronesPaisesMatricula.put(Paises.Republica_Checa, "^[0-9]{1}[A-HJ-NP-Z]{1}[0-9]{1} [0-9]{4}$"); // 4A2 3000
        patronesPaisesMatricula.put(Paises.Eslovenia, "^[A-HJ-NP-Z]{2} [0-9]{2}-[0-9]{1}[A-HJ-NP-Z]{2}$"); // LJ 13-1JP
        patronesPaisesMatricula.put(Paises.Espana, "^[0-9]{4} [A-HJ-NP-Z]{3}$"); // 2008 HHR
        patronesPaisesMatricula.put(Paises.Estonia, "^[0-9]{3} [A-HJ-NP-Z]{3}$"); // 307 RTB
        patronesPaisesMatricula.put(Paises.Finlandia, "^[0-9]{4}-[A-HJ-NP-Z]{3}$"); // MMG-418
        patronesPaisesMatricula.put(Paises.Francia, "^[A-HJ-NP-Z]{2}-[0-9]{3}-[A-HJ-NP-Z]{2}$"); // AA-229-AA
        patronesPaisesMatricula.put(Paises.Italia, "^[A-HJ-NP-Z]{2} [0-9]{3}[A-HJ-NP-Z]{2}$"); // CM 844CA
        patronesPaisesMatricula.put(Paises.Luxemburgo, "^[A-HJ-NP-Z]{2} [0-9]{3}$"); // HV 105
        patronesPaisesMatricula.put(Paises.Malta, "^[A-HJ-NP-Z]{3} [0-9]{3}$"); // ACF 110
        patronesPaisesMatricula.put(Paises.Paises_Bajos, "^[A-HJ-NP-Z]{2}-[A-HJ-NP-Z]{2}-[0-9]{2}$"); // PP-XF-69
        patronesPaisesMatricula.put(Paises.Portugal, "^[0-9]{2}-[0-9]{2}-[A-HJ-NP-Z]{2}$"); // 45-72-XQ
        patronesPaisesMatricula.put(Paises.Rumania, "^[A-HJ-NP-Z]{2} [0-9]{2}[A-HJ-NP-Z]{3}$"); // AG 07PAS
    }

    /**
     * Genera una nueva matrícula aleatoria válida para el país especificado.
     *
     * @param pais El país para el cual se desea generar la matrícula. No debe ser {@code null}.
     * @return Una nueva matrícula válida para el país especificado.
     * @throws IllegalArgumentException Si el país proporcionado es {@code null}.
     */
    public static String nueva(Paises pais) {
        if (pais == null) {
            throw new IllegalArgumentException("El país no puede ser null.");
        }
        return switch (pais) {
            case Alemania -> Aleatorio.numero(2) + " " + Aleatorio.numero(2) + " " + Aleatorio.letra(3);
            case Austria -> Aleatorio.letra(1) + " " + Aleatorio.numero(3) + " " + Aleatorio.letra(2);
            case Belgica -> Aleatorio.numero(1) + "-" + Aleatorio.letra(3) + "-" + Aleatorio.numero(3);
            case Bulgaria -> Aleatorio.letra(2) + " " + Aleatorio.numero(4) + " " + Aleatorio.letra(2);
            case Republica_Checa -> Aleatorio.numero(1) + Aleatorio.letra(1) + Aleatorio.numero(1) + " " + Aleatorio.numero(4);
            case Eslovenia -> Aleatorio.letra(2) + " " + Aleatorio.numero(2) + "-" + Aleatorio.numero(1) + Aleatorio.letra(2);
            case Espana -> Aleatorio.numero(4) + " " + Aleatorio.letra(3);
            case Estonia -> Aleatorio.numero(3) + " " + Aleatorio.letra(3);
            case Finlandia -> Aleatorio.numero(4) + "-" + Aleatorio.letra(3);
            case Francia -> Aleatorio.letra(2) + "-" + Aleatorio.numero(3) + "-" + Aleatorio.letra(2);
            case Italia -> Aleatorio.letra(2) + " " + Aleatorio.numero(3) + Aleatorio.letra(2);
            case Luxemburgo -> Aleatorio.letra(2) + " " + Aleatorio.numero(3);
            case Malta -> Aleatorio.letra(3) + " " + Aleatorio.numero(3);
            case Paises_Bajos -> Aleatorio.letra(2) + "-" + Aleatorio.letra(2) + "-" + Aleatorio.numero(2);
            case Portugal -> Aleatorio.numero(2) + "-" + Aleatorio.numero(2) + "-" + Aleatorio.letra(2);
            case Rumania -> Aleatorio.letra(2) + " " + Aleatorio.numero(2) + Aleatorio.letra(3);
        };
    }

    /**
     * Obtiene el país al que pertenece la matrícula especificada.
     *
     * Este método recorre un conjunto de patrones de matrículas asociados a diferentes países
     * y devuelve el país correspondiente a la matrícula proporcionada.
     *
     * @param matricula La matrícula cuyo país se desea obtener. No debe ser {@code null}.
     * @return El país correspondiente a la matrícula.
     * @throws IllegalArgumentException Si la matrícula no corresponde a ningún país registrado.
     * @throws NullPointerException Si la matrícula es {@code null}.
     */
    public static Paises getPais(String matricula) {
        if (matricula == null) {
            throw new NullPointerException("La matrícula no puede ser null.");
        }
        for (Map.Entry<Paises, String> entry : patronesPaisesMatricula.entrySet()) {
            if (matricula.matches(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Matrícula " + matricula + " no válida para ningún país registrado.");
    }

}
