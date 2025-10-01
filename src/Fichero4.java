import java.io.File;

public class Fichero4 {
    public static void main(String[] args) {
        String dirPadre = "C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas Java";

        //creacion de la instancia utilizando el constructor File
        File directorio = new File(dirPadre);

        //verificar si el archivo existe y si es un directorio
        if(directorio.exists() && directorio.isDirectory()) {
            //creacion de un array del contenido de la carpeta
            String[] contenido = directorio.list();

            //bucle en el cual pasamos mostrando el contenido de la carpeta
            for(int i = 0; i < contenido.length; i++) {
                System.out.println(contenido[i]);
            }
        }else{
            System.out.println("La siguiente ruta no es un directorio");
        }
    }
}
