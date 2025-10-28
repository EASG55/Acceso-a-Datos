import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Ejercicio9Asistente {

    // Escáner global para la lectura de entradas por consola.
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        System.out.println("ASISTENTE DE ARCHIVOS");

        // Bucle principal del programa. Muestra el menú repetidamente hasta que el usuario elija salir.
        while (!salir) {
            try {
                mostrarMenu();
                String opcion = solicitarEntrada("Elige una opción: ");

                // Control de flujo mediante switch para ejecutar las distintas funcionalidades.
                switch (opcion) {
                    case "1" -> verificarArchivo();      // Verifica existencia o crea un archivo
                    case "2" -> explorarCarpeta();       // Muestra el contenido de un directorio
                    case "3" -> crearCarpeta();          // Crea una nueva carpeta
                    case "4" -> crearArchivo();          // Crea un nuevo archivo
                    case "5" -> trabajarConURIs();       // Permite manipular rutas y URIs
                    case "6" -> {                        // Finaliza la ejecución del programa
                        System.out.println("\nSaliendo del asistente...");
                        salir = true;
                    }
                    default -> {                          // Controla entradas inválidas
                        System.out.println("Opción no válida. Intente de nuevo.");
                        pausar();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                pausar();
            }
        }

        // Cierre del recurso Scanner al finalizar la ejecución.
        sc.close();
    }

    // Muestra el menú principal con las opciones disponibles para el usuario.
    public static void mostrarMenu() {
        System.out.println("\n---- MENÚ PRINCIPAL ----");
        System.out.println("1. Verificar si un archivo existe");
        System.out.println("2. Explorar una carpeta");
        System.out.println("3. Crear una nueva carpeta");
        System.out.println("4. Crear un nuevo archivo");
        System.out.println("5. Trabajar con URIs");
        System.out.println("6. Salir");
        System.out.println();
    }

    // Verifica si un archivo existe, mostrando información o creando el archivo si el usuario lo desea.
    public static void verificarArchivo() {
        System.out.println("\nVERIFICAR ARCHIVO");
        String ruta = solicitarEntrada("Introduce la ruta completa del archivo: ");

        if (ruta.isBlank()) {
            System.out.println("Error: la ruta no puede estar vacía.");
            pausar();
            return;
        }

        File archivo = new File(ruta);

        try {
            // Verificación de existencia del archivo
            if (archivo.exists() && archivo.isFile()) {
                System.out.println("El archivo existe: " + archivo.getAbsolutePath());
                System.out.println("Tamaño: " + archivo.length() + " bytes");
            } else {
                // En caso de no existir, se ofrece la posibilidad de crearlo
                System.out.println("El archivo no existe: " + archivo.getAbsolutePath());
                String resp = solicitarEntrada("¿Deseas crearlo? (s/n): ");
                if (resp.equalsIgnoreCase("s")) {
                    // Crea los directorios padres si no existen
                    if (archivo.getParentFile() != null && !archivo.getParentFile().exists()) {
                        archivo.getParentFile().mkdirs();
                    }
                    // Intenta crear el archivo
                    if (archivo.createNewFile()) {
                        System.out.println("Archivo creado correctamente en: " + archivo.getAbsolutePath());
                    } else {
                        System.out.println("No se pudo crear el archivo. Verifique permisos o ruta.");
                    }
                } else {
                    System.out.println("Operación cancelada por el usuario.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al crear el archivo: " + e.getMessage());
        }

        pausar();
    }

    // Explora el contenido de una carpeta mostrando archivos y subcarpetas.
    public static void explorarCarpeta() {
        System.out.println("\nEXPLORAR DIRECTORIO");
        String ruta = solicitarEntrada("Introduce la ruta del directorio: ");

        if (ruta.isBlank()) {
            System.out.println("Error: la ruta no puede estar vacía.");
            pausar();
            return;
        }

        File dir = new File(ruta);

        // Si ocurre, será capturada por el main.
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("La ruta no existe o no es un directorio válido.");
            pausar();
            return;
        }

        // Obtiene el listado de elementos contenidos en el directorio
        File[] elementos = dir.listFiles();
        if (elementos == null || elementos.length == 0) {
            System.out.println("El directorio está vacío.");
        } else {
            System.out.println("\nContenido del directorio:");
            int contador = 1;
            for (File f : elementos) {
                String tipo = f.isDirectory() ? "[Carpeta]" : "[Archivo]";
                System.out.printf("%d. %s %s%n", contador++, f.getName(), tipo);
            }
            System.out.println("\nTotal: " + elementos.length + " elementos");
        }

        pausar();
    }

    // Crea una nueva carpeta en la ruta indicada por el usuario.
    public static void crearCarpeta() {
        System.out.println("\nCREAR CARPETA");
        String ruta = solicitarEntrada("Introduce la ruta completa de la nueva carpeta: ");

        if (ruta.isBlank()) {
            System.out.println("Error: la ruta no puede estar vacía.");
            pausar();
            return;
        }

        File carpeta = new File(ruta);

        if (carpeta.exists()) {
            System.out.println("La carpeta ya existe: " + carpeta.getAbsolutePath());
        } else if (carpeta.mkdirs()) {
            System.out.println("Carpeta creada correctamente en: " + carpeta.getAbsolutePath());
        } else {
            System.out.println("No se pudo crear la carpeta. Verifique permisos.");
        }

        pausar();
    }

    // Crea un nuevo archivo y genera los directorios necesarios si no existen.
    public static void crearArchivo() {
        System.out.println("\nCREAR ARCHIVO");
        String ruta = solicitarEntrada("Introduce la ruta completa del nuevo archivo: ");

        if (ruta.isBlank()) {
            System.out.println("Error: la ruta no puede estar vacía.");
            pausar();
            return;
        }

        File archivo = new File(ruta);

        try {
            // Crea directorios padres si es necesario
            if (archivo.getParentFile() != null && !archivo.getParentFile().exists()) {
                archivo.getParentFile().mkdirs();
            }

            // Verifica existencia o crea el nuevo archivo
            if (archivo.exists()) {
                System.out.println("El archivo ya existe: " + archivo.getAbsolutePath());
            } else if (archivo.createNewFile()) {
                System.out.println("Archivo creado exitosamente en: " + archivo.getAbsolutePath());
            } else {
                System.out.println("No se pudo crear el archivo. Verifique permisos.");
            }
        } catch (IOException e) {
            System.out.println("Error de E/S al crear el archivo: " + e.getMessage());
        }

        pausar();
    }

    // Permite al usuario elegir entre verificar una URI o convertir una ruta a formato URI.
    public static void trabajarConURIs() {
        System.out.println("\nTRABAJAR CON URIs");
        System.out.println("1. Verificar una URI existente");
        System.out.println("2. Convertir ruta a URI");
        String opcion = solicitarEntrada("Tu elección: ");

        switch (opcion) {
            case "1" -> verificarURI();
            case "2" -> convertirRutaAURI();
            default -> System.out.println("Opción no válida.");
        }

        pausar();
    }

    // Verifica si una URI es válida y si el recurso que señala existe.
    public static void verificarURI() {
        String uriStr = solicitarEntrada("Introduce una URI: ");
        try {
            URI uri = new URI(uriStr);
            File f = new File(uri);

            if (f.exists()) {
                System.out.println("La URI apunta a un recurso existente: " + f.getAbsolutePath());
            } else {
                System.out.println("La URI es válida, pero el recurso no existe.");
            }
        } catch (URISyntaxException e) {
            System.out.println("Error: la URI introducida no tiene una sintaxis válida.");
        }
    }

    // Convierte una ruta de archivo local a su representación URI.
    public static void convertirRutaAURI() {
        String ruta = solicitarEntrada("Introduce la ruta a convertir: ");
        if (ruta.isBlank()) {
            System.out.println("Error: la ruta no puede estar vacía.");
            return;
        }

        File f = new File(ruta);

        System.out.println("Ruta original: " + f.getAbsolutePath());
        System.out.println("URI generada: " + f.toURI());
        if (f.exists()) {
            System.out.println("La ruta existe y la URI es válida.");
        } else {
            System.out.println("La ruta no existe, pero la URI se generó correctamente.");
        }
    }

    // Pausa la ejecución del programa hasta que el usuario presione Enter.
    public static void pausar() {
        System.out.print("\nPresiona Enter para continuar...");
        try {
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("\nError al pausar la ejecución.");
        }
    }

    // Solicita entrada del usuario de forma controlada y devuelve la cadena introducida.
    private static String solicitarEntrada(String mensaje) {
        System.out.print(mensaje);
        try {
            return sc.nextLine().trim();
        } catch (Exception e){
            System.out.println("Error de entrada. Intente nuevamente.");
            return "";
        }
    }
}