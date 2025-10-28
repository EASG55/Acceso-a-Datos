package AccesoFicherosIO;

//Importamos las librerias de FileWriter y su manejo de excepciones

import java.io.FileWriter;
import java.io.IOException;

public class EjemploFileWriter {
    static void main(String[] args) {
        //variable para almacenar el caracter leido
        String contenido = "Primera linea\nSegunda linea\nTercera linea";

        //Try-catch cierra automaticamente el FileWriter
        //Por defecto sobreescribe el archivo si existe
        try(FileWriter fw = new FileWriter("src/AccesoFicherosIO/salida.txt")){
            //Escribimos la cadena completa
            fw.write(contenido);
            //Flush() se llama automaticamente al final

        } catch (IOException e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        //Resultado esperado: crea salida.txt con tres lineas de texto
    }
}
