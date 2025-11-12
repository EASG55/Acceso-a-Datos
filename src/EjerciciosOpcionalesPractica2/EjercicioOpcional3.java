package EjerciciosOpcionalesPractica2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

public class EjercicioOpcional3 {

    /**
     * Copia un archivo de origen a destino.
     *
     * @param origen archivo fuente
     * @param destino archivo destino
     * @throws IOException si hay error en la copia
     */
    private static void copiarArchivo(File origen, File destino) throws IOException {
        // Usar NIO.2 (Files.copy) para una copia robusta
        Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Lee la fecha del último backup desde el archivo de control.
     *
     * @param archivoControl ruta del archivo de control
     * @return timestamp del último backup, o 0 si no existe
     * @throws IOException si hay error de lectura
     */
    private static long leerUltimoBackup(String archivoControl) throws IOException {
        Path rutaControl = Paths.get(archivoControl);
        
        if (!Files.exists(rutaControl)) {
            return 0; // 0 significa que nunca se ha hecho backup
        }
        
        try {
            // Leer el timestamp (almacenado como String en la primera línea)
            List<String> lineas = Files.readAllLines(rutaControl, StandardCharsets.UTF_8);
            if (lineas.isEmpty()) {
                return 0;
            }
            return Long.parseLong(lineas.get(0));
        } catch (NumberFormatException e) {
            System.err.println("Advertencia: archivo de control corrupto. Iniciando backup completo.");
            return 0;
        }
    }

    /**
     * Escribe el timestamp actual en el archivo de control.
     * @param archivoControl ruta del archivo de control
     * @param timestamp timestamp actual
     * @throws IOException si hay error de escritura
     */
    private static void escribirTimestampBackup(String archivoControl, long timestamp) throws IOException {
        Path rutaControl = Paths.get(archivoControl);
        // Asegurarse de que la carpeta (.backup) exista
        Files.createDirectories(rutaControl.getParent());
        // Escribir el timestamp como texto
        Files.write(rutaControl, String.valueOf(timestamp).getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Realiza backup incremental de una carpeta.
     *
     * @param carpetaOrigen ruta de la carpeta a respaldar
     * @param carpetaDestino ruta donde guardar el backup
     * @param archivoControl archivo que registra el último backup
     * @return número de archivos copiados
     * @throws IOException si hay error en el proceso
     */
    public static int backupIncremental(String carpetaOrigen, String carpetaDestino, String archivoControl) throws IOException {
        System.out.println("Iniciando backup incremental...");
        
        // 1. Leer el último timestamp
        long ultimoBackup = leerUltimoBackup(archivoControl);
        System.out.println("Último backup: " + (ultimoBackup == 0 ? "nunca" : new Date(ultimoBackup)));

        int archivosCopiados = 0;
        File dirOrigen = new File(carpetaOrigen);
        File dirDestino = new File(carpetaDestino);

        // 2. Asegurar que la carpeta de destino exista
        if (!dirDestino.exists()) {
            dirDestino.mkdirs();
        }

        File[] archivosEnOrigen = dirOrigen.listFiles();
        if (archivosEnOrigen == null) {
            System.err.println("Error: No se puede leer la carpeta de origen: " + carpetaOrigen);
            return 0;
        }

        // 3. Iterar archivos y copiar si son nuevos o modificados
        for (File archivoFuente : archivosEnOrigen) {
            if (archivoFuente.isFile()) {
                // Comprobar si el archivo fue modificado DESPUÉS del último backup
                if (archivoFuente.lastModified() > ultimoBackup) {
                    File archivoDestino = new File(dirDestino, archivoFuente.getName());
                    
                    copiarArchivo(archivoFuente, archivoDestino);
                    
                    System.out.println("Copiando: " + archivoFuente.getName() + 
                        (ultimoBackup == 0 ? "" : " (modificado)"));
                    archivosCopiados++;
                }
            }
            // Nota: Este ejercicio no pide recursividad, solo archivos en el primer nivel.
        }

        // 4. Registrar el nuevo timestamp
        long nuevoTimestamp = System.currentTimeMillis();
        escribirTimestampBackup(archivoControl, nuevoTimestamp);
        
        System.out.println("Backup completado: " + archivosCopiados + " archivos");
        System.out.println("Registro actualizado: " + new Date(nuevoTimestamp));
        
        return archivosCopiados;
    }


    public static void main(String[] args) {
        String origen = "./documentos";
        String destino = "./backup";
        String control = "./backup/.lastbackup";

        try {
            // 0. Preparar entorno de prueba
            new File(origen).mkdirs();
            Files.writeString(Paths.get(origen, "doc1.txt"), "Contenido 1", StandardCharsets.UTF_8);
            Files.writeString(Paths.get(origen, "doc2.txt"), "Contenido 2", StandardCharsets.UTF_8);
            Files.writeString(Paths.get(origen, "imagen.png"), "Contenido 3", StandardCharsets.UTF_8);

            // 1. Primera ejecución (debería copiar todo)
            System.out.println("--- PRIMERA EJECUCIÓN ---");
            backupIncremental(origen, destino, control);
            
            // Pausa para simular paso del tiempo
            Thread.sleep(2000); 
            
            // 2. Modificar un archivo y añadir uno nuevo
            System.out.println("\n(Modificando doc2.txt y añadiendo doc3.txt...)");
            Files.writeString(Paths.get(origen, "doc2.txt"), "Contenido 2 modificado", StandardCharsets.UTF_8);
            Files.writeString(Paths.get(origen, "doc3.txt"), "Contenido 4 nuevo", StandardCharsets.UTF_8);

            // 3. Segunda ejecución (debería copiar solo 2 archivos)
            System.out.println("\n--- SEGUNDA EJECUCIÓN ---");
            backupIncremental(origen, destino, control);

            // 4. Tercera ejecución (no debería copiar nada)
            System.out.println("\n--- TERCERA EJECUCIÓN ---");
            backupIncremental(origen, destino, control);

        } catch (IOException e) {
            System.err.println("Error de E/S en el backup: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}