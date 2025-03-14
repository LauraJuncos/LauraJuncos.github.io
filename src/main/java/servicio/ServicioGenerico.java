/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.GenericoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */
public abstract class ServicioGenerico<T> {

    protected GenericoDAO<T> dao;

    // Constructor
    public ServicioGenerico(GenericoDAO<T> dao) {
        this.dao = dao;
    }

    // Método para guardar una entidad
    public void save(T entity) throws SQLException {
        dao.create(entity);
    }

    // Método para obtener todos los registros
    public List<T> findAll() throws SQLException {
        return dao.findAll();
    }

    // Método para obtener una entidad por ID
    public T findById(int id) throws SQLException {
        return dao.findById(id);
    }

    // Método para actualizar una entidad
    public boolean update(int id, T entity) throws SQLException {
        return dao.update(id, entity);
    }

    // Método para eliminar una entidad
    public boolean delete(int id) throws SQLException {
        return dao.delete(id);
    }

    // Método para buscar por un atributo
    public List<T> buscarPorAtributo(String columna, String valor) throws SQLException {
        return dao.buscarPorAtributo(columna, valor);
    }
}