import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Fichero3 {
    public static void main(String[] args) {
        try {
            // Crear un objeto File a partir de un URI
            String uriString = "file:///C:/Users/AlumnoAfternoon/Documents/Pruebas%20Java";
            URI uri = new URI(uriString);
            File ruta = new File(uri);
            //verificar si el archivo o directorio existe
            if(ruta.exists()) {
                //Verificar si la ruta especificada es un directorio
                if(ruta.isDirectory()) {
                    //Si en la ruta el ultimo elemento es un directorio se muestra un mensaje
                    // en pantalla de que la ruta especificada es un directorio
                    System.out.println("La ruta presenta un directorio en: " + uri.toString());
                }else if(ruta.isFile()) {
                    //Si en la ruta el ultimo elemento es un archivo se muestra un mensaje
                    // en pantalla de que la ruta especificada es un archivo
                    System.out.println("La ruta presenta un archivo en: " + uri.toString());
                }
            }else{
                System.out.println("La URI no existe: " + uri.toString());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }
}
