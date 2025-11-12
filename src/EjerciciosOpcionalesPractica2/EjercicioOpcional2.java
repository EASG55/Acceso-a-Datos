package EjerciciosOpcionalesPractica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EjercicioOpcional2 {

    // Mapa estático para almacenar las variables cargadas
    private static final Map<String, String> envVariables = new HashMap<>();

    /**
     * Lee un archivo.env y carga las variables.
     *
     * @param archivoEnv ruta del archivo.env
     * @return Map con las variables cargadas
     * @throws IOException sí hay error de lectura
     */
    public static Map<String, String> cargarEnv(String archivoEnv) throws IOException {
        // Limpiar variables anteriores si se recarga
        envVariables.clear();

        // Usar BufferedReader para leer el .env
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(archivoEnv), StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();

                // Ignorar comentarios y líneas vacías
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                // Buscar el primer '='
                int separatorIndex = linea.indexOf('=');

                // Asegurarse de que hay un '=' y no está al principio
                if (separatorIndex > 0) {
                    String clave = linea.substring(0, separatorIndex).trim();
                    String valor = linea.substring(separatorIndex + 1).trim();

                    // Quitar comillas si las hay (opcional pero común en .env)
                    if (valor.startsWith("\"") && valor.endsWith("\"")) {
                        valor = valor.substring(1, valor.length() - 1);
                    }

                    envVariables.put(clave, valor);
                }
            }
        }

        System.out.println("Cargadas " + envVariables.size() + " variables desde " + archivoEnv);
        // Retorna una copia para evitar modificación externa del mapa estático
        return new HashMap<>(envVariables);
    }

    /**
     * Obtiene el valor de una variable de entorno.
     *
     * @param clave nombre de la variable
     * @param valorPorDefecto valor si la variable no existe
     * @return valor de la variable o valorPorDefecto
     */
    public static String getEnv(String clave, String valorPorDefecto) {
        return envVariables.getOrDefault(clave, valorPorDefecto);
    }

    public static void main(String[] args) {
        String archivoEnv = ".env";

        // Crear archivo .env de ejemplo
        String contenidoEnv = "DB_HOST=localhost\n" +
                "DB_PORT=5432\n" +
                "DB_USER=admin\n" +
                "DB_PASSWORD=secret123\n" +
                "\n" +
                "# Comentario ignorado\n" +
                "DEBUG=true";

        try {
            Files.writeString(Paths.get(archivoEnv), contenidoEnv, StandardCharsets.UTF_8);

            // 1. Cargar el .env
            Map<String, String> env = cargarEnv(archivoEnv);

            // 2. Usar los valores del Map retornado
            System.out.println("Base de datos: " + env.get("DB_HOST") + ":" + env.get("DB_PORT"));

            // 3. Usar el método estático getEnv
            String debug = getEnv("DEBUG", "false");
            System.out.println("Debug mode: " + debug);

            // 4. Probar valor por defecto
            String api_key = getEnv("API_KEY", "key_default");
            System.out.println("API Key: " + api_key);

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}