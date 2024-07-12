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

    private static final String NUMEROS_VALIDOS = "123456789";
    private static final String LETRAS_VALIDAS = "ABCDEFGHJKLMNPQRSTUVWXYZ";

    private static final Random RANDOM = new Random();
    private static final int LONGITUD_NUMEROS = NUMEROS_VALIDOS.length();
    private static final int LONGITUD_LETRAS = LETRAS_VALIDAS.length();

    /**
     * Genera una cadena de texto con números aleatorios.
     *
     * @param cantidad Cantidad de números aleatorios a generar.
     * @return Una cadena de texto que contiene los números aleatorios generados.
     */
    public static String numero(int cantidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            int index = RANDOM.nextInt(LONGITUD_NUMEROS);
            sb.append(NUMEROS_VALIDOS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Genera una cadena de texto con letras aleatorias.
     *
     * @param cantidad Cantidad de letras aleatorias a generar.
     * @return Una cadena de texto que contiene las letras aleatorias generadas.
     */
    public static String letra(int cantidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            int index = RANDOM.nextInt(LONGITUD_LETRAS);
            sb.append(LETRAS_VALIDAS.charAt(index));
        }
        return sb.toString();
    }

}
