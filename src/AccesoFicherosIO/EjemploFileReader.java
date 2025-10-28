package AccesoFicherosIO;

//Importamos las librerias de FileReader y su manejo de excepciones

import java.io.FileReader;
import java.io.IOException;

public class EjemploFileReader {
    static void main(String[] args) {
        //variable para almacenar el caracter leido
        int caracter;

        try(FileReader fr = new FileReader("src/AccesoFicherosIO/entrada.txt")){
            // read() retorna -1 cuando llega al final del archivo
            while((caracter=fr.read())!=-1){
                //Convertimos el int a char para mostrar el caracter
                System.out.print((char)caracter);
            }

        } catch (IOException e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        //Resultado esperado: imprime todo el contenido del archivo caracter a caracter.
    }

}
