/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.PersonasDAO;
import DTO.CochesDTO;
import DTO.PersonasDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Personas;

/**
 *
 * @author Laura
 */
public class PersonasServicio {

    private PersonasDAO personasDAO;

    public PersonasServicio(PersonasDAO personasDAO) {
        this.personasDAO = personasDAO;
    }

    // Buscar personas por un atributo general con validación de filtros
    public List<PersonasDTO> buscarPorAtributoPersonas(String columna, String valor) throws SQLException {
        validarFiltros(columna, valor);
        return personasDAO.buscarPorAtributoPersonas(columna, valor);
    }

     // Buscar personas por nombre con validación de filtro
    public List<PersonasDTO> buscarPorNombre(String nombre) {
        try {
            validarFiltroNombre(nombre);
            return personasDAO.buscarPorAtributoPersonas("nombre", nombre);
        } catch (Exception e) {
            mostrarMensajeError("El filtro contiene caracteres especiales no permitidos.");
            return new ArrayList<>();
        }
    }
    
    // Buscar personas por DNI con validación de filtro
    public List<PersonasDTO> buscarPorDni(String dni) {
        try {
            validarFiltros(dni);
            return personasDAO.buscarPorAtributoPersonas("dni", dni);
        } catch (IllegalArgumentException e) {
            mostrarMensajeError("El DNI contiene caracteres especiales no permitidos.");
        } catch (Exception e) {
            mostrarMensajeError("Ocurrió un error inesperado.");
        }
        return new ArrayList<>();
    }

    // Buscar personas por sexo
    public List<PersonasDTO> buscarPorSexo(Personas.Sexo sexo) throws SQLException {
        if (sexo == null) {
            throw new IllegalArgumentException("El valor del sexo no puede ser nulo.");
        }
        return personasDAO.buscarPorAtributoPersonas("sexo", String.valueOf(sexo.getValor()));
    }

    // Método para buscar una persona por ID
    public PersonasDTO buscarPorId(int id) throws SQLException {
        return personasDAO.buscarPorId(id);
    }

    // Obtener todas las personas de la base de datos
    public List<PersonasDTO> obtenerTodasLasPersonas() throws SQLException {
        return personasDAO.obtenerTodasLasPersonas();
    }

    // Buscar personas usando múltiples filtros (nombre, año, sexo, etc.) con validación de filtros
    public List<PersonasDTO> buscarPorFiltros(String nombre, String anoMatriculacion, String numVehiculos,
            Personas.Sexo sexo, String marca, String modelo) throws SQLException {
        String sexoStr = (sexo != null) ? String.valueOf(sexo.getValor()) : null;
        validarFiltros(nombre, anoMatriculacion, numVehiculos, sexoStr, marca, modelo);
        return personasDAO.buscarPorFiltros(nombre, anoMatriculacion, numVehiculos, sexoStr, marca, modelo);
    }

    // Método para validar los filtros y evitar SQL injection
    private void validarFiltros(String... filtros) throws SQLException {
        for (String filtro : filtros) {
            if (contieneCaracteresEspeciales(filtro)) {
                throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
            }
        }
    }

    // Método para validar un filtro simple de nombre
    private void validarFiltroNombre(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return; // Considerar válido si es nulo o vacío
        }
        if (!filtro.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\p{L}\\p{N}\\s.'-]+$")) {
            throw new IllegalArgumentException("El filtro contiene caracteres especiales no permitidos.");
        }
    }

    // Mostrar un mensaje de error al usuario
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método para verificar si un filtro contiene caracteres especiales
    private boolean contieneCaracteresEspeciales(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false; // No hay caracteres especiales en un valor nulo o vacío
        }
        // Permitir letras, dígitos, espacios y algunos caracteres especiales básicos
        return !valor.matches("^[\\p{L}\\p{N}\\s.'-]+$");
    }
    // Servicio para agregar una persona
    public void agregarPersonas(PersonasDTO persona) {
        personasDAO.insertarPersona(persona);
    }
    
    // Método para eliminar una persona por ID
    public void eliminarPersona(int idPersona) {
        personasDAO.eliminarPersona(idPersona);
        System.out.println("Persona eliminada exitosamente.");
    }
}
