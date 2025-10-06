import java.io.File;
import java.net.URI;
import java.util.Scanner;

public class Ejercicio8Explorador {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce una ruta de carpeta o archivo: ");
        String ruta = sc.nextLine();

        // Explorar carpeta si es directorio
        explorarCarpeta(ruta);

        // Analizar elemento siempre (archivo o carpeta)
        analizarElemento(ruta);

        // Convertir a URI y mostrar
        convertirAURI(ruta);

        sc.close();
    }

    // Función para listar el contenido
    public static void explorarCarpeta(String ruta) {
        File dir = new File(ruta);

        //si ruta no existe
        if (!dir.exists()) {
            System.out.println("La ruta no existe: " + dir.getAbsolutePath());
            return;
        }

        //si la ruta no es un directorio
        if (!dir.isDirectory()) {
            System.out.println("La ruta no es un directorio: " + dir.getAbsolutePath());
            return;
        }

        //listar contenido
        String[] contenido = dir.list();
        if (contenido == null) {
            System.out.println("No se pudo listar el contenido (permiso o error de E/S).");
            return;
        }

        System.out.println("Contenido de: " + dir.getAbsolutePath());
        for (String nombre : contenido) {
            File elemento = new File(dir, nombre);
            if (elemento.isFile()) {
                System.out.println("- " + nombre + " [ARCHIVO]");
            } else if (elemento.isDirectory()) {
                System.out.println("- " + nombre + " [DIRECTORIO]");
            } else {
                System.out.println("- " + nombre + " [DESCONOCIDO]");
            }
        }
    }

    // Función para analizar elementos, ya sea archivo (tamaño) o carpeta (nº elementos)
    public static void analizarElemento(String ruta) {
        File f = new File(ruta);

        //si ruta no existe
        if (!f.exists()) {
            System.out.println("No se puede analizar: la ruta no existe.");
            return;
        }

        //si ruta es un archivo o directorio
        if (f.isFile()) {
            long tamanioBytes = f.length();
            System.out.println("Elemento: ARCHIVO");
            System.out.println("Nombre: " + f.getName());
            System.out.println("Ruta absoluta: " + f.getAbsolutePath());
            System.out.println("Tamaño: " + tamanioBytes + " bytes");

        } else if (f.isDirectory()) {
            String[] hijos = f.list();
            int numeroElementos = (hijos == null) ? 0 : hijos.length;
            System.out.println("Elemento: DIRECTORIO");
            System.out.println("Nombre: " + f.getName());
            System.out.println("Ruta absoluta: " + f.getAbsolutePath());
            System.out.println("Número de elementos: " + numeroElementos);
        } else {
            System.out.println("Elemento no reconocido (ni archivo ni directorio).");
        }
    }

    // Función para convertir ruta a URI y mostrar
    public static URI convertirAURI(String ruta) {
        File f = new File(ruta);
        URI uri = f.toURI();
        System.out.println("URI: " + uri.toString());
        return uri;
    }
}
