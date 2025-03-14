/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.PersonasDAO;
import DTO.PersonasDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */

public class PersonasServicio {

    private PersonasDAO personasDAO;

    public PersonasServicio(PersonasDAO personasDAO) {
        this.personasDAO = personasDAO;
    }

    public List<PersonasDTO> buscarPorAtributoPersonas(String columna, String valor) throws SQLException {
        return personasDAO.buscarPorAtributoPersonas(columna, valor);
    }

    // Buscar personas por nombre
    public List<PersonasDTO> buscarPorNombre(String nombre) throws SQLException {
        return personasDAO.buscarPorAtributoPersonas("nombre", nombre);
    }

    // Buscar personas por DNI
    public List<PersonasDTO> buscarPorDni(String dni) throws SQLException {
        return personasDAO.buscarPorAtributoPersonas("dni", dni);
    }

    // Buscar personas por sexo
    public List<PersonasDTO> buscarPorSexo(String sexo) throws SQLException {
        return personasDAO.buscarPorAtributoPersonas("sexo", sexo);
    }
    // Método para buscar una persona por ID
    public PersonasDTO buscarPorId(int id) throws SQLException {
        // Llamamos al DAO para obtener la persona por su ID
        return personasDAO.buscarPorId(id);
    }
    public List<PersonasDTO> obtenerTodasLasPersonas() throws SQLException {
        return personasDAO.obtenerTodasLasPersonas();
    }
}
