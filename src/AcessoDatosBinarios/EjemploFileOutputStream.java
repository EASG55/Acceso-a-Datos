package AcessoDatosBinarios;

import java.io.FileOutputStream;
import java.io.IOException;

public class EjemploFileOutputStream {
    public static void main(String[] args) throws IOException {
        //inicializacion de un array de numeros ASCII para escribir "hola Mundo"
        byte[] datos = {72, 111, 108, 97, 32, 77, 117, 110, 100, 111};

        //Try catch en el que inicializamos FileOutputStream y se cierra automaticamente
        try(FileOutputStream fos = new FileOutputStream("salida.bin")){
            //Escritura de caracteres ASCII en el fichero especificado
            fos.write(datos);
        }catch(IOException e){
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
