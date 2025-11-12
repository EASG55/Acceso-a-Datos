package EjerciciosObligatoriosPractica2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase contenedora para las estadísticas de un archivo de texto.
 */
class EstadisticasTexto {
    private int numeroLineas;
    private int numeroPalabras;
    private int numeroCaracteres;
    private String palabraMasLarga;

    public EstadisticasTexto() {
        this.numeroLineas = 0;
        this.numeroPalabras = 0;
        this.numeroCaracteres = 0;
        this.palabraMasLarga = "";
    }

    // --- Getters y Setters ---
    public int getNumeroLineas() { return numeroLineas; }
    public void setNumeroLineas(int numeroLineas) { this.numeroLineas = numeroLineas; }
    public int getNumeroPalabras() { return numeroPalabras; }
    public void setNumeroPalabras(int numeroPalabras) { this.numeroPalabras = numeroPalabras; }
    public int getNumeroCaracteres() { return numeroCaracteres; }
    public void setNumeroCaracteres(int numeroCaracteres) { this.numeroCaracteres = numeroCaracteres; }
    public String getPalabraMasLarga() { return palabraMasLarga; }
    public void setPalabraMasLarga(String palabraMasLarga) { this.palabraMasLarga = palabraMasLarga; }

    // --- Métodos para incrementar ---
    public void incrementarLineas() { this.numeroLineas++; }
    public void anadirPalabras(int count) { this.numeroPalabras += count; }
    public void anadirCaracteres(int count) { this.numeroCaracteres += count; }

    /**
     * Comprueba si una palabra nueva es más larga que la actual.
     * @param palabra La palabra a comprobar.
     */
    public void comprobarPalabraLarga(String palabra) {
        if (palabra.length() > this.palabraMasLarga.length()) {
            this.palabraMasLarga = palabra;
        }
    }

    /**
     * Muestra las estadísticas por consola.
     */
    public void mostrarEnConsola() {
        System.out.println("=== Estadísticas del archivo ===");
        System.out.println("Líneas: " + this.numeroLineas);
        System.out.println("Palabras: " + this.numeroPalabras);
        System.out.println("Caracteres: " + this.numeroCaracteres);
        System.out.println("Palabra más larga: " + this.palabraMasLarga + " (" + this.palabraMasLarga.length() + " caracteres)");
    }
}

/**
 * Solución para el Ejercicio 1: Contador de Palabras y Estadísticas.
 */
public class Ejercicio1 {

    /**
     * Lee un archivo y cuenta palabras, líneas y caracteres.
     * @param nombreArchivo ruta del archivo a analizar
     * @return objeto EstadisticasTexto con los resultados
     * @throws IOException si hay error al leer el archivo
     */
    public static EstadisticasTexto analizarArchivo(String nombreArchivo) throws IOException {
        EstadisticasTexto stats = new EstadisticasTexto();

        // Usar Files.newBufferedReader para especificar Charset UTF-8 explícito
        // y try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(nombreArchivo), StandardCharsets.UTF_8)) {
            String linea;
            // readLine() es eficiente gracias al buffer
            while ((linea = reader.readLine()) != null) {
                stats.incrementarLineas();
                stats.anadirCaracteres(linea.length());

                // Procesar palabras
                String[] palabras = linea.trim().split("\\s+");
                if (palabras.length == 1 && palabras[0].isEmpty()) {
                    // Línea vacía no cuenta como palabra
                } else {
                    stats.anadirPalabras(palabras.length);
                    for (String palabra : palabras) {
                        stats.comprobarPalabraLarga(palabra);
                    }
                }
            }
        }
        // No es necesario validar si existe, Files.newBufferedReader lanza
        // NoSuchFileException (subclase de IOException) si no existe.
        return stats;
    }

    /**
     * Escribe las estadísticas en un archivo de salida.
     * @param estadisticas objeto con las estadísticas
     * @param archivoSalida ruta donde guardar el resultado
     * @throws IOException si hay error al escribir
     */
    public static void guardarEstadisticas(EstadisticasTexto estadisticas, String archivoSalida) throws IOException {
        // Usar BufferedWriter para escritura eficiente
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoSalida), StandardCharsets.UTF_8)) {
            bw.write("=== Estadísticas del archivo ===");
            bw.newLine();
            bw.write("Líneas: " + estadisticas.getNumeroLineas());
            bw.newLine();
            bw.write("Palabras: " + estadisticas.getNumeroPalabras());
            bw.newLine();
            bw.write("Caracteres: " + estadisticas.getNumeroCaracteres());
            bw.newLine();
            String palabraLarga = estadisticas.getPalabraMasLarga();
            bw.write("Palabra más larga: " + palabraLarga + " (" + palabraLarga.length() + " caracteres)");
            bw.newLine();
        }
    }

    /**
     * Método main para probar el Ejercicio 1.
     */
    public static void main(String[] args) {
        String archivoEntrada = "src/EjerciciosObligatorios/entrada_ej1.txt";
        String archivoSalida = "src/EjerciciosObligatorios/stats_ej1.txt";

        // 1. Crear el archivo de entrada de ejemplo
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(archivoEntrada), StandardCharsets.UTF_8)) {
            bw.write("Hola mundo");
            bw.newLine();
            bw.write("Java es genial");
            bw.newLine();
            bw.write("Programación");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de entrada: " + e.getMessage());
            return;
        }

        // 2. Analizar el archivo
        try {
            EstadisticasTexto resultados = analizarArchivo(archivoEntrada);

            // 3. Mostrar por consola
            resultados.mostrarEnConsola();

            // 4. Guardar en archivo de salida
            guardarEstadisticas(resultados, archivoSalida);
            System.out.println("\nEstadísticas guardadas en: " + archivoSalida);

        } catch (IOException e) {
            // Manejo adecuado de IOException
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }
}