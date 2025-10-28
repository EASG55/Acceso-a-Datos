package AccesoFicherosIO;

//Importamos las librerias de BufferedReader y su manejo de excepciones

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;


public class EjemploBufferedReader {
    static void main(String[] args) {
        //Variable para almacenar la linea leida
        String linea;
        //Contador de lineas
        int numLinea = 1;

        //BufferedReader envuelve al objeto FileReader para añadir Buffering, try catch cierra automaticamente
        try(BufferedReader br = new BufferedReader(new FileReader("src/AccesoFicherosIO/entrada.txt"))) {
            // readLine() retorna null cuando no hay más lineas
            while( (linea = br.readLine()) != null){
                System.out.println(numLinea + ": " + linea);
                numLinea++;
            }

        }catch (IOException e){
            System.err.println("Error al abrir el archivo: "+e.getMessage());
        }
    }
}
