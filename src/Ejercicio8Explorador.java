import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Ejercicio8Explorador {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Introduce una ruta de carpeta o archivo: ");
            String ruta = sc.nextLine().trim();

            // Validación básica de entrada vacía
            if (ruta.isEmpty()) {
                System.err.println("Error: No se ha introducido ninguna ruta.");
                return;
            }

            // Llamamos a los métodos.
            explorarCarpeta(ruta);
            analizarElemento(ruta);
            convertirAURI(ruta);

        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado en main: " + e.getMessage());
        }
    }


    /**
     * Metodo que lista el contenido de una carpeta especificada por el usuario.
     */
    public static void explorarCarpeta(String ruta) {
        try {
            File dir = new File(ruta);

            // Validar existencia de la ruta
            if (!dir.exists()) {
                System.err.println("Error [explorarCarpeta]: La ruta no existe -> " + dir.getAbsolutePath());
                return;
            }

            // Verificar si realmente es un directorio
            if (!dir.isDirectory()) {
                System.err.println("Aviso [explorarCarpeta]: La ruta no es un directorio -> " + dir.getAbsolutePath());
                return;
            }

            // Listar el contenido del directorio
            String[] contenido = dir.list();
            if (contenido == null) {
                System.err.println("Error [explorarCarpeta]: No se pudo listar el contenido (error de E/S o permisos).");
                return;
            }

            System.out.println("\nContenido de: " + dir.getAbsolutePath());
            for (String nombre : contenido) {
                File elemento = new File(dir, nombre);

                // Identificar tipo de elemento
                if (elemento.isFile()) {
                    System.out.println("- " + nombre + " [ARCHIVO]");
                } else if (elemento.isDirectory()) {
                    System.out.println("- " + nombre + " [DIRECTORIO]");
                } else {
                    System.out.println("- " + nombre + " [DESCONOCIDO]");
                }
            }
        } catch (Exception e) {
            System.err.println("Error inesperado [explorarCarpeta]: " + e.getMessage());
        }
    }

    /**
     * Metodo que analiza un elemento (archivo o carpeta) y muestra sus propiedades.
     */
    public static void analizarElemento(String ruta) {
        try {
            File f = new File(ruta);

            // Verificar existencia de la ruta
            if (!f.exists()) {
                System.err.println("Error [analizarElemento]: No se puede analizar, la ruta no existe.");
                return; 
            }

            // Mostrar detalles según el tipo de elemento
            if (f.isFile()) {
                long tamanioBytes = f.length();
                System.out.println("\nElemento: ARCHIVO");
                System.out.println("Nombre: " + f.getName());
                System.out.println("Ruta absoluta: " + f.getAbsolutePath());
                System.out.println("Tamaño: " + tamanioBytes + " bytes");

            } else if (f.isDirectory()) {
                String[] hijos = f.list();
                int numeroElementos = (hijos == null) ? 0 : hijos.length;
                System.out.println("\nElemento: DIRECTORIO");
                System.out.println("Nombre: " + f.getName());
                System.out.println("Ruta absoluta: " + f.getAbsolutePath());
                System.out.println("Número de elementos: " + numeroElementos);

            } else {
                System.err.println("Advertencia [analizarElemento]: El elemento no se reconoce (ni archivo ni directorio).");
            }
        }  catch (Exception e) {
            System.err.println("Error inesperado [analizarElemento]: " + e.getMessage());
        }
    }


    /**
     * Convierte una ruta local en un objeto URI.
     */
    public static URI convertirAURI(String ruta) {
        try {
            File f = new File(ruta);

            // Validar que la ruta exista antes de convertir
            if (!f.exists()) {
                System.err.println("Error [convertirAURI]: No se puede convertir una ruta inexistente a URI.");
                return null;
            }

            // Conversión directa de File a URI
            URI uri = f.toURI();
            System.out.println("\nURI: " + uri);
            return uri;
        }catch (Exception e) {
            System.err.println("Error inesperado [convertirAURI]: " + e.getMessage());
            return null;
        }
    }
}