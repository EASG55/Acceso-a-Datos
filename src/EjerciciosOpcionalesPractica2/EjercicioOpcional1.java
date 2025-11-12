package EjerciciosOpcionalesPractica2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class EjercicioOpcional1 {

    /**
     * Lee un archivo JSON y extrae pares clave-valor simples.
     *
     * @param archivoJson ruta del archivo JSON
     * @return Map con las claves y valores parseados
     * @throws IOException sí hay error de lectura
     */
    public static Map<String, String> leerJsonSimple(String archivoJson) throws IOException {
        Map<String, String> datos = new HashMap<>();

        // Usar BufferedReader con try-with-resources
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(archivoJson), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();

                // Buscar líneas que contengan "clave": "valor"
                if (linea.contains(":") && linea.contains("\"")) {
                    try {
                        // Extraer clave (entre las primeras 2 comillas)
                        int keyStart = linea.indexOf("\"") + 1;
                        int keyEnd = linea.indexOf("\"", keyStart);
                        String clave = linea.substring(keyStart, keyEnd);

                        // Extraer valor (entre las siguientes 2 comillas)
                        int valueStart = linea.indexOf("\"", keyEnd + 1) + 1;
                        int valueEnd = linea.indexOf("\"", valueStart);
                        String valor = linea.substring(valueStart, valueEnd);

                        datos.put(clave, valor);
                    } catch (Exception e) {
                        System.err.println("Advertencia: No se pudo parsear la línea: " + linea);
                    }
                }
            }
        }

        System.out.println("JSON leído: " + datos.size() + " propiedades");
        return datos;
    }

    /**
     * Escribe un Map como archivo JSON formateado.
     *
     * @param datos Map con los datos a escribir
     * @param archivoJson ruta del archivo de salida
     * @throws IOException sí hay error de escritura
     */
    public static void escribirJsonSimple(Map<String, String> datos, String archivoJson) throws IOException {
        // Usar BufferedWriter con try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(archivoJson), StandardCharsets.UTF_8)) {
            writer.write("{");
            writer.newLine();

            // Usar un iterador para manejar la coma final
            Iterator<Map.Entry<String, String>> iterator = datos.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();

                // Formato: "clave": "valor"
                String linea = String.format("  \"%s\": \"%s\"", entry.getKey(), entry.getValue());

                // Añadir coma si no es el último elemento
                if (iterator.hasNext()) {
                    linea += ",";
                }

                writer.write(linea);
                writer.newLine();
            }

            writer.write("}");
            writer.newLine();
        }

        System.out.println("JSON escrito: " + datos.size() + " propiedades en " + archivoJson);
    }

    public static void main(String[] args) {
        String archivoEntrada = "config.json";
        String archivoSalida = "config_nuevo.json";

        // Crear archivo de entrada de ejemplo
        String contenidoJson = "{\n" +
                "  \"host\": \"localhost\",\n" +
                "  \"puerto\": \"8080\",\n" +
                "  \"debug\": \"true\"\n" +
                "}";

        try {
            Files.writeString(Paths.get(archivoEntrada), contenidoJson, StandardCharsets.UTF_8);

            // 1. Leer JSON
            Map<String, String> config = leerJsonSimple(archivoEntrada);
            System.out.println("Host: " + config.get("host"));

            // 2. Modificar y escribir JSON
            config.put("version", "1.0");
            escribirJsonSimple(config, archivoSalida);

            // Verificar el nuevo archivo
            System.out.println("\n--- Contenido de " + archivoSalida + " ---");
            Files.readAllLines(Paths.get(archivoSalida)).forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}