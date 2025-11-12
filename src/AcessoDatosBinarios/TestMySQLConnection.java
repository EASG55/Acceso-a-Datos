package AcessoDatosBinarios;

//importacion de paquetes nativos para SQL y BBDD
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQLConnection {
    public static void main(String[] args) {

        //inicializacion de variables
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String user = "root";
        String password = "mysql";

        //try catch en el que intentamos hacer ping a la bbdd creada
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            System.out.println("Conexión exitosa al MySQL");
        }catch (SQLException e){
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
