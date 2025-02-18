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
        validarEntidad(entity);
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
        validarEntidad(entity);
        return dao.update(id, entity);
    }

    // Método para eliminar una entidad
    public boolean delete(int id) throws SQLException {
        return dao.delete(id);
    }

    // Método para buscar por un atributo con validación de filtros
    public List<T> buscarPorAtributo(String columna, String valor) throws SQLException {
        validarFiltros(columna, valor);
        return dao.buscarPorAtributo(columna, valor);
    }

    // Método para validar los filtros y evitar SQL injection
    private void validarFiltros(String... filtros) throws SQLException {
        for (String filtro : filtros) {
            if (contieneCaracteresEspeciales(filtro)) {
                throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
            }
        }
    }

    // Método para validar una entidad antes de guardarla o actualizarla
    protected abstract void validarEntidad(T entity) throws SQLException;

    // Método para verificar si un filtro contiene caracteres especiales
    private boolean contieneCaracteresEspeciales(String valor) {
        if (valor == null) {
            return false;
        }
        return !valor.matches("[a-zA-Z0-9\s]*");
    }
}
