/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laura
 */

public abstract class GenericoDAO<T> {

    protected Connection conexion;

    public GenericoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para insertar una nueva entidad
    public void create(T entity) throws SQLException {
        String query = getInsertQuery(entity);  // Obtener la consulta de inserción
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            setInsertValues(stmt, entity);  // Establecer los valores para la inserción
            stmt.executeUpdate();  // Ejecutar la consulta
        }
    }

    // Método de paginación general
    public List<T> findPaginated(int offset, int pageSize) throws SQLException {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, pageSize);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs));  // Mapear los resultados a la entidad
                }
            }
        }
        return entities;
    }

    // Método de búsqueda por atributo con paginación
    public List<T> buscarPorAtributoPaginado(String columna, String valor, int offset, int pageSize) throws SQLException {
        List<T> resultados = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE " + columna + " LIKE ? LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, "%" + valor + "%");
            stmt.setInt(2, pageSize);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapResultSetToEntity(rs));  // Mapear los resultados a la entidad
                }
            }
        }
        return resultados;
    }

    // Contar todos los registros
    public int countAll() throws SQLException {
        String query = "SELECT COUNT(*) FROM " + getTableName();
        try (PreparedStatement stmt = conexion.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);  // Retornar el número de registros
            }
        }
        return 0;
    }

    // Obtener todos los registros
    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);  // Mapear el ResultSet a la entidad
                entities.add(entity);
            }
        }
        return entities;
    }

    // Obtener una entidad por su ID
    public T findById(int id) throws SQLException {
        T entity = null;
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entity = mapResultSetToEntity(rs);  // Mapear el ResultSet a la entidad
                }
            }
        }
        return entity;
    }

    // Actualizar una entidad
    public boolean update(int id, T entity) throws SQLException {
        String sql = getUpdateQuery(entity);  // Obtener la consulta de actualización
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            setUpdateValues(stmt, entity);  // Establecer los valores para la actualización
            stmt.setInt(getUpdateParameterCount() + 1, id);  // Establecer el ID para la condición WHERE
            int rowsAffected = stmt.executeUpdate();  // Ejecutar la actualización
            return rowsAffected > 0;  // Retornar si se afectaron filas
        }
    }

    // Eliminar una entidad por su ID
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);  // Establecer el ID para la condición WHERE
            int rowsAffected = stmt.executeUpdate();  // Ejecutar la eliminación
            return rowsAffected > 0;  // Retornar si se afectaron filas
        }
    }

    // Buscar entidades por un atributo
    public List<T> buscarPorAtributo(String columna, String valor) throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + columna + " = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, valor);  // Establecer el valor del atributo para la búsqueda
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    T entity = mapResultSetToEntity(rs);  // Mapear el ResultSet a la entidad
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    // Métodos abstractos que deben implementarse en las clases concretas
    protected abstract String getTableName();  // Obtener el nombre de la tabla
    protected abstract String getInsertQuery(T entity);  // Obtener la consulta de inserción
    protected abstract void setInsertValues(PreparedStatement stmt, T entity) throws SQLException;  // Establecer los valores de inserción
    protected abstract String getUpdateQuery(T entity);  // Obtener la consulta de actualización
    protected abstract void setUpdateValues(PreparedStatement stmt, T entity) throws SQLException;  // Establecer los valores de actualización
    protected abstract int getUpdateParameterCount();  // Contar los parámetros de actualización
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;  // Mapear el ResultSet a una entidad
}