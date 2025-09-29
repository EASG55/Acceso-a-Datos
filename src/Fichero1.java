import java.io.File;
// import java.io*;


public class Fichero1 {
    public static void main(String[] args) {
        // Directorio padre que acabamos de crear en la ruta de Documentos
        File dirPadre = new File("C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas Java");

        //Nombre o ruta relativa al fichero que acabo de crear
        String nomHijo = "hijo.txt";

        // Creo una instancia File utilizando el constructor y la variable de arriba
        File archivo = new File(dirPadre, nomHijo);

        if(archivo.exists()){
            //Si el archivo existe se muestra un mensaje en pantalla de que existe
            // mostrando la ruta completa especificada
            System.out.println("El archivo existe en la ruta: " + archivo.getAbsolutePath());
        }else{
            //Si el archivo NO existe se muestra un mensaje en pantalla de que NO existe
            // mostrando la ruta completa especificada
            System.out.println("El archivo NO existe en la ruta: " + archivo.getAbsolutePath());

        }
    }
}
