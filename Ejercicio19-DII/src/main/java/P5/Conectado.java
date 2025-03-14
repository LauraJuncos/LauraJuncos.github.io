/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package P5;
/**
 *
 * @author Laura
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectado {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/fpdual?useSSL=false&serverTimezone=UTC";
        String user = "laura";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a MySQL!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC de MySQL no encontrado.");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
    }
}

