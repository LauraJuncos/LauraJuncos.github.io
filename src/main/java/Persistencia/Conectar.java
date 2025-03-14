/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author Laura
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {

    private static final String URL = "jdbc:mysql://localhost:3306/proyectomvc";
    private static final String USUARIO = "laura";
    private static final String CONTRASENA = "1234";
    private Connection connection;

    public Conectar() {
        // Inicializar la conexión al crear una instancia de la clase
        try {
            connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener la conexión actual
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("Conexión restablecida.");
            } catch (SQLException e) {
                System.err.println("Error al restablecer la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay ninguna conexión abierta para cerrar.");
        }
    }
}
