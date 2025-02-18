/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.PersonaVehiculoDAO;
import DTO.PersonaVehiculoDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */
public class PersonaVehiculoServicio {
    private PersonaVehiculoDAO personaVehiculoDAO;

    // Constructor
    public PersonaVehiculoServicio(PersonaVehiculoDAO personaVehiculoDAO) {
        this.personaVehiculoDAO = personaVehiculoDAO;
    }

    // Método para filtrar personas y vehículos con validación de filtros
    public List<PersonaVehiculoDTO> filtrarPersonasConVehiculos(String nombre, String marca, String modelo, String sexo, Integer anoMatriculacion) throws SQLException {
        validarFiltros(nombre, marca, modelo, sexo);
        return personaVehiculoDAO.filtrarPersonasConVehiculos(nombre, marca, modelo, sexo, anoMatriculacion);
    }

    // Método para validar los filtros y evitar SQL injection
    private void validarFiltros(String... filtros) throws SQLException {
        for (String filtro : filtros) {
            if (contieneCaracteresEspeciales(filtro)) {
                throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
            }
        }
    }

     // Método para verificar si un filtro contiene caracteres especiales
    private boolean contieneCaracteresEspeciales(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false; // No hay caracteres especiales en un valor nulo o vacío
        }
        // Permitir letras, dígitos, espacios y algunos caracteres especiales básicos
        return !valor.matches("^[\\p{L}\\p{N}\\s.'-]+$");
    }
}
