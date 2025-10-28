package AccesoFicherosIO;

//Importamos las librerias de BufferedWriter y su manejo de excepciones

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

public class EjemploBufferedWriter {
    static void main(String[] args) {
        //array con lineas a escribir
        String[] lineas = {
                "Encabezado del documento",
                "Esta es la primera linea del contenido",
                "Esta es la segunda linea del contenido",
                "Final del documento",
        };

        //BufferedWriter envuelve al objeto FileWriter para añadir Buffering, try catch cierra automaticamente
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/AccesoFicherosIO/salida_buffer.txt"))){
            //Bucle for-each que escribe linea a linea con salto incluido
            for(String linea : lineas){
                //escritura en documento
                bw.write(linea);
                // el salto de linea
                bw.newLine();
            }
            //Flush() Se llama automaticamente al final
        }catch (IOException e){
            System.err.println("Error al escribir en el archivo: " +e.getMessage());
        }

        //Resultado esperado: Archivo con 4 lineas, cada una en su propia linea
    }
}
