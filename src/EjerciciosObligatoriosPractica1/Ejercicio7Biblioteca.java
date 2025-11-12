package EjerciciosObligatoriosPractica1;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio7Biblioteca {

    // Scanner global para la lectura de datos desde la consola
    private static final Scanner sc = new Scanner(System.in);

    // Carpeta base donde se almacenarán las categorías y los libros
    private static final String CARPETA = "C:\\biblioteca";

    public static void main(String[] args) {
        System.out.println("ORGANIZADOR DE BIBLIOTECA");

        try {
            // crear o verificar una categoría
            organizarBiblioteca();

            // verificar o crear un libro dentro de una categoría
            verificarLibro();

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
            sc.close();

    }


     //Este metodo permite crear una categoría dentro de la carpeta base "C:\\biblioteca".
     //También crea (si no existe) un archivo "catalogo.txt" dentro de dicha categoría.

    public static void organizarBiblioteca() {
        System.out.print("Introduce el nombre de la categoría: ");
        String categoria = sc.nextLine().trim();

        // Validación básica: no se permite un nombre vacío
        if (categoria.isEmpty()) {
            System.out.println("Error: el nombre de la categoría no puede estar vacío.");
            return;
        }

        // Se crea un objeto File que representa la ruta de la categoría
        File carpetaCategoria = new File(CARPETA, categoria);

        try {
            // Si la carpeta no existe, se crea (puede incluir subdirectorios)
            if (!carpetaCategoria.exists()) {
                carpetaCategoria.mkdirs();
                System.out.println("Categoría '" + categoria + "' creada exitosamente.");
            } else {
                System.out.println("La categoría '" + categoria + "' ya existe.");
            }

            // Se crea (si no existe) el archivo "catalogo.txt" dentro de la categoría
            File catalogo = new File(carpetaCategoria, "catalogo.txt");

            if (catalogo.createNewFile()) {
                System.out.println("Catálogo creado en: " + catalogo.getAbsolutePath());
            } else {
                System.out.println("El catálogo ya existe en: " + catalogo.getAbsolutePath());
            }

        } catch (IOException e) {
            System.out.println("Error de E/S al crear el catálogo: " + e.getMessage());
        }
    }


     //Metodo que comprueba si un libro existe dentro de una categoría específica.
     //Si la categoría no existe, la crea automáticamente.
     //Si el libro no existe, ofrece la opción de crearlo.

    public static void verificarLibro() {
        System.out.println();
        System.out.print("Introduce la categoría del libro: ");
        String categoriaLibro = sc.nextLine().trim();

        System.out.print("Introduce el nombre del libro: ");
        String nombreLibro = sc.nextLine().trim();

        // Validación básica: los campos no pueden estar vacíos
        if (categoriaLibro.isEmpty() || nombreLibro.isEmpty()) {
            System.out.println("Error: ni la categoría ni el nombre del libro pueden estar vacíos.");
            return;
        }

        // Carpeta donde debería encontrarse el libro
        File carpetaLibro = new File(CARPETA, categoriaLibro);

        try {
            // Si la categoría no existe, se crea automáticamente
            if (!carpetaLibro.exists()) {
                carpetaLibro.mkdirs();
                System.out.println("Categoría '" + categoriaLibro + "' creada automáticamente.");
            }

            // Archivo que representa al libro
            File libro = new File(carpetaLibro, nombreLibro);

            if (libro.exists()) {
                // Si el libro existe, se muestra su ruta y tamaño
                System.out.println("El libro existe en: " + libro.getAbsolutePath());
                System.out.println("Tamaño: " + libro.length() + " bytes");
            } else {
                // Si no existe, se pregunta al usuario si desea crearlo
                System.out.println("El libro no existe en: " + libro.getAbsolutePath());
                System.out.print("¿Quieres crear el libro? (s/n): ");
                String resp = sc.nextLine().trim();

                // Si el usuario responde "s", se crea el archivo vacío
                if (resp.equalsIgnoreCase("s")) {
                    if (libro.createNewFile()) {
                        System.out.println("Libro creado exitosamente en: " + libro.getAbsolutePath());
                    } else {
                        System.out.println("No se pudo crear el libro (ya existe o error desconocido).");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error de E/S al crear o acceder al libro: " + e.getMessage());
        }
    }
}
