import java.io.File;
import java.io.IOException;

public class Fichero5 {
    public static void main(String[] args) throws IOException {
        //Rutas de archivo y directorio como cadena de texto
        String directorio = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas Java\\Padre";
        String archivo = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas Java\\hijo.txt";

        //Crear instancias File usando su constructor
        File directorioPadre = new File(directorio);
        File archivoHijo = new File (archivo);

        //verificar si el archivo y directorio existen
        boolean fin = false;

        do{
            if(!directorioPadre.exists()){
                System.out.println("Directorio no existe");
                directorioPadre.mkdir();
                System.out.println("Directorio creado correctamente");
            }else if(!archivoHijo.exists()){
                System.out.println("Archivo no existe");
                archivoHijo.createNewFile();
                System.out.println("Archivo creado correctamente");
            }else{
                System.out.println("Directorio y archivo ya existen");
                fin = true;
            }
        }while(!fin);

    }
}
