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
import java.sql.SQLException;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class Conectar {

    private static final String URL = "jdbc:mysql://localhost:3306/proyectomvc";
    private static final String USUARIO = "laura";
    private static final String CONTRASENA = "1234";
    private static final int TAMANO_INICIAL_POOL = 5;

    private static PoolDataSource poolDataSource;

    static {
        try {
            poolDataSource = PoolDataSourceFactory.getPoolDataSource();
            poolDataSource.setConnectionFactoryClassName("com.mysql.cj.jdbc.MysqlDataSource");
            poolDataSource.setURL(URL);
            poolDataSource.setUser(USUARIO);
            poolDataSource.setPassword(CONTRASENA);
            poolDataSource.setInitialPoolSize(TAMANO_INICIAL_POOL);
        } catch (SQLException e) {
            throw new RuntimeException("Error al configurar el pool de conexiones: " + e.getMessage(), e);
        }
    }

    public Connection abrirConexion() {
        try {
            return poolDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexión desde el pool: " + e.getMessage(), e);
        }
    }

    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }
}