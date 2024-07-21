package GESTION_DE_PARKING;

import java.util.Random;

/**
 * Esta clase proporciona métodos estáticos para generar cadenas aleatorias de números y letras.
 * Los números generados están en el rango del 1 al 9, ambos inclusive.
 * Las letras generadas son del conjunto: A-H, J-N, P-Z.
 *
 * @version 1.0
 */
public class Aleatorio {

    /**
     * Cadena que contiene los números válidos para la generación aleatoria.
     * Incluye los dígitos del 1 al 9.
     */
    private static final String NUMEROS_VALIDOS = "123456789";

    /**
     * Cadena que contiene las letras válidas para la generación aleatoria.
     * Incluye las letras A-H, J-N, y P-Z.
     */
    private static final String LETRAS_VALIDAS = "ABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * Instancia de la clase {@link Random} utilizada para generar números aleatorios.
     */
    private static final Random RANDOM = new Random();

    /**
     * Longitud de la cadena de números válidos.
     */
    private static final int LONGITUD_NUMEROS = NUMEROS_VALIDOS.length();

    /**
     * Longitud de la cadena de letras válidas.
     */
    private static final int LONGITUD_LETRAS = LETRAS_VALIDAS.length();

    /**
     * Genera una cadena de texto que contiene números aleatorios.
     *
     * <p>El rango de los números generados es del 1 al 9 (ambos inclusive).</p>
     *
     * @param cantidad La cantidad de números aleatorios a generar.
     * @return Una cadena de texto que contiene los números aleatorios generados.
     * @throws IllegalArgumentException Si la cantidad es menor o igual a cero.
     */
    public static String numero(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            int index = RANDOM.nextInt(LONGITUD_NUMEROS);
            sb.append(NUMEROS_VALIDOS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Genera una cadena de texto que contiene letras aleatorias.
     *
     * <p>Las letras generadas pertenecen al conjunto: A-H, J-N, P-Z.</p>
     *
     * @param cantidad La cantidad de letras aleatorias a generar.
     * @return Una cadena de texto que contiene las letras aleatorias generadas.
     * @throws IllegalArgumentException Si la cantidad es menor o igual a cero.
     */
    public static String letra(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            int index = RANDOM.nextInt(LONGITUD_LETRAS);
            sb.append(LETRAS_VALIDAS.charAt(index));
        }
        return sb.toString();
    }
}
