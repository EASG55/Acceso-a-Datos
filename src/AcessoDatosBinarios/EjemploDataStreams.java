package AcessoDatosBinarios;

import java.io.*;


public class EjemploDataStreams {
    public static void main(String[] args) throws IOException {
        //escritura

        //Try catch en el que inicializamos DataOutputStream y se cierra automaticamente
        try(DataOutputStream dos = new DataOutputStream(new  FileOutputStream("primitivos.dat"))) {
            //Inicializacion y asignacion de valores
            dos.writeInt(12345);
            dos.writeDouble(99.99);
            dos.writeUTF("Producto de Ejemplo");
            dos.writeBoolean(true);
        }catch(IOException e){
            System.err.println("Error al escribir: " + e.getMessage());
        }

        //lectura
        //Try catch en el que inicializamos DataInputStream y se cierra automaticamente
        try(DataInputStream dis = new DataInputStream(new FileInputStream("primitivos.dat"))) {
            //Inicializacion y asignacion de valores
            int numero = dis.readInt();
            double precio = dis.readDouble();
            String nombre = dis.readUTF();
            boolean activo = dis.readBoolean();

            //imprimimos los valores
            System.out.println("numero: " + numero);
            System.out.println("precio: " + precio);
            System.out.println("Nombre: " + nombre);
            System.out.println("estado: " + activo);
        }catch(IOException e){
            System.err.println("Error al escribir: " + e.getMessage());
        }
    }
}
