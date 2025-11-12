package EjerciciosObligatoriosPractica2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Define los niveles de severidad del log.
 *
 */
enum NivelLog {
    INFO, WARNING, ERROR
}

/**
 * Implementa un sistema de logging con rotación de archivos.
 *
 */
class SistemaLog {
    private String archivoLog;
    private long tamanoMaximo;
    private int numeroRotacion;

    /**
     * Constructor del sistema de log.
     *
     * @param archivoLog Ruta base del archivo de log
     * @param tamanoMaximo Tamaño máximo en bytes antes de rotar
     */
    public SistemaLog(String archivoLog, long tamanoMaximo) {
        if (archivoLog == null || archivoLog.isEmpty() || tamanoMaximo <= 0) {
            throw new IllegalArgumentException("Parámetros inválidos");
        }
        this.archivoLog = archivoLog;
        this.tamanoMaximo = tamanoMaximo;
        this.numeroRotacion = 1;
    }

    /**
     * Escribe un mensaje en el log con timestamp.
     *
     * @param mensaje contenido a registrar
     * @param nivel nivel del log (INFO, WARNING, ERROR)
     * @throws IOException si hay error al escribir
     */
    public void escribirLog(String mensaje, NivelLog nivel) throws IOException {
        // 1. Verificar si hay que rotar ANTES de escribir
        rotarSiNecesario();

        // 2. Formatear mensaje
        // Usar formato de fecha ISO 8601 (simplificado)
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String lineaLog = String.format("[%s] [%s] %s", timestamp, nivel, mensaje);

        // 3. Escribir en el archivo
        // Usar BufferedWriter en modo APPEND y CREATE
        // y try-with-resources
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(this.archivoLog),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.write(lineaLog);
            writer.newLine();
            writer.flush(); // Garantizar escritura inmediata para logs
        }

        System.out.println("Log escrito: " + mensaje);
    }

    /**
     * Verifica si el archivo debe rotarse y ejecuta la rotación.
     *
     * @return true si se realizó la rotación
     * @throws IOException si hay error en la rotación
     */
    private boolean rotarSiNecesario() throws IOException {
        long tamanoActual = obtenerTamanoLog();

        if (tamanoActual >= this.tamanoMaximo) {
            Path origen = Paths.get(this.archivoLog);
            // Definir archivo de destino
            String archivoDestinoNombre = this.archivoLog + "." + this.numeroRotacion;
            Path destino = Paths.get(archivoDestinoNombre);

            // Renombrar (mover) el archivo
            Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("ROTACIÓN: " + this.archivoLog + " renombrado a " + archivoDestinoNombre);

            this.numeroRotacion++;
            return true;
        }
        return false;
    }

    /**
     * Obtiene el tamaño actual del archivo de log.
     *
     * @return tamaño en bytes
     */
    private long obtenerTamanoLog() {
        try {
            Path ruta = Paths.get(this.archivoLog);
            if (Files.exists(ruta)) {
                return Files.size(ruta);
            }
        } catch (IOException e) {
            System.err.println("No se pudo obtener el tamaño del log: " + e.getMessage());
        }
        return 0;
    }
}

public class Ejercicio3 {
    public static void main(String[] args) {
        // 1KB de tamaño máximo
        SistemaLog log = new SistemaLog("app.log", 1024);

        try {
            log.escribirLog("Aplicación iniciada", NivelLog.INFO);
            log.escribirLog("Usuario conectado", NivelLog.INFO);

            // Simular llenado del log
            for (int i = 0; i < 50; i++) {
                log.escribirLog("Operación de prueba número " + i, NivelLog.INFO);
            }

            log.escribirLog("Error de conexión", NivelLog.ERROR);
            log.escribirLog("Siguiente operación post-rotación", NivelLog.INFO);

        } catch (IOException e) {
            System.err.println("Error fatal de E/S escribiendo log: " + e.getMessage());
        }
    }
}