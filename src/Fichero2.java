import java.io.File;
// import java.io*;


public class Fichero2 {
    public static void main(String[] args) {
        // Directorio padre que acabamos de crear en la ruta de Documentos
        File ruta = new File("C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas Java");

        if(ruta.exists()) {
            //Verificar si la ruta especificada es un directorio
            if(ruta.isDirectory()) {
                //Si en la ruta el ultimo elemento es un directorio se muestra un mensaje 
                // en pantalla de que la ruta especificada es un directorio
                System.out.println("La ruta presenta un directorio en: " + ruta.getAbsolutePath());
            }else if(ruta.isFile()) {
                //Si en la ruta el ultimo elemento es un archivo se muestra un mensaje 
                // en pantalla de que la ruta especificada es un archivo
                System.out.println("La ruta presenta un archivo en: " + ruta.getAbsolutePath());
            }

            //Verificar si la ruta especificada es un archivo

            //Fichero3 -> Hagais una ruta con URI, creais el objeto, verificais que existe (else if),
            // si es un directorio o archivo (else if) (try-catch)

            //Fichero4 -> copia exacta del Fichero1, pero que mostreis dentro del if el contenido de la carpeta,
            // sus nombres (List(), length)

            //Fichero5/6 -> verificar que dicho directorio(s) o archivo existe, en caso de que no exista crearlo
        }
    }
}
