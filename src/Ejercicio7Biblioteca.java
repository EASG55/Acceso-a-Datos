import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio7Biblioteca {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("ORGANIZADOR DE BIBLIOTECA");

        //Organizar biblioteca
        organizarBiblioteca();

        //Verificar libro
        verificarLibro();

        sc.close();
    }

    // Función que organiza la biblioteca
    public static void organizarBiblioteca() {
        System.out.print("Introduce el nombre de la categoría: ");
        String categoria = sc.nextLine();

        File carpetaCategoria = new File("C:\\biblioteca", categoria);

        //si categoria no existe, crearla
        if (!carpetaCategoria.exists()) {
            carpetaCategoria.mkdirs();
            System.out.println("Categoría '" + categoria + "' creada exitosamente");
        } else {
            System.out.println("La categoría '" + categoria + "' ya existe");
        }

        //archivo catalogo.txt
        File catalogo = new File(carpetaCategoria, "catalogo.txt");
        try {
            if (catalogo.createNewFile()) {
                System.out.println("Catálogo creado en: " + catalogo.getAbsolutePath());
            } else {
                System.out.println("El catálogo ya existe en: " + catalogo.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error al crear catalogo.txt");
        }
    }

    // Función que verifica si el libro existe o se crea
    public static void verificarLibro() {
        System.out.println();
        System.out.print("Introduce la categoría del libro: ");
        String categoriaLibro = sc.nextLine();
        System.out.print("Introduce el nombre del libro: ");
        String nombreLibro = sc.nextLine();

        //si carpeta no exisste, crearla
        File carpetaLibro = new File("C:\\biblioteca", categoriaLibro);
        if (!carpetaLibro.exists()) {
            carpetaLibro.mkdirs();
        }

        File libro = new File(carpetaLibro, nombreLibro);

        //si libro existe, dar el tamaño. Sino, preguntar si se crea o no
        if (libro.exists()) {
            System.out.println("El libro existe en: " + libro.getAbsolutePath());
            System.out.println("Tamaño: " + libro.length() + " bytes");
        } else {
            System.out.println("El libro no existe en: " + libro.getAbsolutePath());
            System.out.print("¿Quieres crear el libro? (s/n): ");
            String resp = sc.nextLine();
            if (resp.equalsIgnoreCase("s")) {
                try {
                    if (libro.createNewFile()) {
                        System.out.println("Libro creado exitosamente en: " + libro.getAbsolutePath());
                    } else {
                        System.out.println("No se pudo crear el libro.");
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear el libro.");
                }
            }
        }
    }
}
