package AccesoFicherosIO;

//Importamos las librerias de RAF y su manejo de excepciones

import java.io.RandomAccessFile;
import java.io.IOException;

public class EjemploRandomAccessFile {
    static void main(String[] args) {
        try(RandomAccessFile raf = new RandomAccessFile("src/AccesoFicherosIO/datos.bin","rw")){
            // Escribimos en diferentes posiciones
            raf.writeBytes("INICIO");

            //nos movemos a la posicion 20
            raf.seek(20);
            raf.writeBytes("MEDIO");

            //Nos movemos a la posicion 40
            raf.seek(40);
            raf.writeBytes("FINAL");

            //Volver al inicio para leer
            raf.seek(0);
            System.out.println("Posicion 0: " + raf.readLine());


            //mostramos la longitud total del archivo
            System.out.println("Tamaño del archivo: " + raf.length() + " bytes");
        } catch (IOException e){
            System.err.println("Error al acceder al archivo: " + e.getMessage());
        }

        //Resultado esperado: Escritura y lectura en posiciones especificas del archivo
    }
}
