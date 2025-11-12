package AcessoDatosBinarios;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        //inicializacion de variables
        String url = "jdbc:mysql://localhost:3306/mi_base_datos";
        String user = "root";
        String password = "mysql";

        Connection conn = null; //objeto de conexion a la bbdd para mysql
        Statement stmt = null; // objeto que captura sentencias de sql
        ResultSet rs = null;  // objeto que captura el resultado de dicha sentencia


        try{
            //establecer conexion
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida a MySQL!");

            //crear statement y ejecutar consulta
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id, nombre, email, edad FROM usuarios");

            //procesar resultados
            while (rs.next()) {
                //inicializacion y conversion de variables de mysql a java
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int edad = rs.getInt("edad");

                //mostrar en consola el resultado fila a fila
                System.out.println(id + " - " + nombre + " - " + email + " (" + edad + " años)");
            }


        }catch(SQLException e){
            System.err.println("Error al conectar: " + e.getMessage());
        }finally {
            try{
                if(rs != null){
                    rs.close();
                }
            }catch(SQLException e){
                System.err.println("Error al conectar: " + e.getMessage());
            }
            try{
                if(stmt != null){
                    rs.close();
                }
            }catch (SQLException e){
                System.err.println("Error al conectar: " + e.getMessage());
            }
            try{
                if(conn != null){
                    rs.close();
                }
            }catch (SQLException e){
                System.err.println("Error al conectar: " + e.getMessage());
            }
        }
    }
}
