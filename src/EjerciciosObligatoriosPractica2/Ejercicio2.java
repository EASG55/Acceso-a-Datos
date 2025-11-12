package EjerciciosObligatoriosPractica2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ejercicio2 {

    /**
     * Verifica si una línea cumple el criterio de filtrado.
     * @param linea línea a evaluar
     * @param filtro criterio de búsqueda (null = siempre true)
     * @return true si la línea debe incluirse
     */
    private static boolean cumpleFiltro(String linea, String filtro) {
        if (filtro == null) {
            return true; // Si no hay filtro, todas las líneas pasan
        }
        return linea.contains(filtro);
    }

    /**
     * Combina múltiples archivos en uno solo, filtrando líneas.
     * @param archivosEntrada array con las rutas de los archivos a combinar
     * @param archivoSalida ruta del archivo resultado
     * @param filtro palabra que debe contener la línea para incluirse (null=todas)
     * @return número total de líneas escritas
     * @throws IOException si hay error de lectura/escritura
     */
    public static int combinarArchivos(String[] archivosEntrada, String archivoSalida, String filtro) throws IOException {
        int lineasTotalesEscritas = 0;

        // Usar BufferedWriter para el archivo de salida
        // con try-with-resources  y UTF-8
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoSalida), StandardCharsets.UTF_8)) {

            // Iterar sobre cada archivo de entrada
            for (String nombreArchivo : archivosEntrada) {
                Path rutaEntrada = Paths.get(nombreArchivo);

                // Validar que el archivo de entrada existe
                if (!Files.exists(rutaEntrada)) {
                    System.err.println("Advertencia: El archivo " + nombreArchivo + " no existe. Omitiendo.");
                    continue;
                }

                int lineasCoincidentes = 0;

                // Usar BufferedReader para cada archivo de entrada
                try (BufferedReader br = Files.newBufferedReader(rutaEntrada, StandardCharsets.UTF_8)) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        // Aplicar filtro
                        if (cumpleFiltro(linea, filtro)) {
                            bw.write(linea);
                            bw.newLine();
                            lineasCoincidentes++;
                            lineasTotalesEscritas++;
                        }
                    }
                }

                // Informar por consola
                System.out.println("Procesando " + nombreArchivo + ": " + lineasCoincidentes + " líneas coinciden");
            }
        }

        // Informar total
        System.out.println("Total: " + lineasTotalesEscritas + " líneas escritas en " + archivoSalida);
        return lineasTotalesEscritas;
    }

    public static void main(String[] args) {
        // Crear archivos de prueba
        String archivo1 = "src/EjerciciosObligatorios/archivo1.txt";
        String archivo2 = "src/EjerciciosObligatorios/archivo2.txt";
        String archivoSalida = "src/EjerciciosObligatorios/combinado.txt";
        String filtro = "Java ";

        try {
            Files.writeString(Paths.get(archivo1),
                    "Java es poderoso\nPython también\nJava es popular",
                    StandardCharsets.UTF_8);

            Files.writeString(Paths.get(archivo2),
                    "JavaScript en web\nJava en backend",
                    StandardCharsets.UTF_8);

            String[] entradas = {archivo1, archivo2, "archivo_inexistente.txt"};

            combinarArchivos(entradas, archivoSalida, filtro);

            // Verificar salida
            System.out.println("\n--- Contenido de " + archivoSalida + " ---");
            Files.readAllLines(Paths.get(archivoSalida)).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}