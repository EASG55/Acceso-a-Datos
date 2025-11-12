package AcessoDatosBinarios;

import java.io.FileInputStream;
import java.io.IOException;

public class EjemploFileInputStream {
    public static void main(String[] args) {
        //inicializacion de variable para recorrer fichero .bin
        int b;

        //try catch en el que inicializamos FileInputStream y se cierra automaticamente
        try(FileInputStream fis = new FileInputStream("datos.bin")){
            //bucle en el que leemos caracter a caracter
            while ((b = fis.read()) != -1){
                //impresion del fichero con su conversion a ASCII
                System.out.println(b + "");
            }
        }catch(IOException e){
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
