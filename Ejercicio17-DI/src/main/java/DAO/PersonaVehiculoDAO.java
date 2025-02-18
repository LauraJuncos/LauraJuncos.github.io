/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PersonaVehiculoDTO;
import Persistencia.Conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.PersonasVehiculos;

/**
 *
 * @author Laura
 */
public class PersonaVehiculoDAO extends GenericoDAO<PersonasVehiculos> {

    public PersonaVehiculoDAO(Connection conexion) {
        super(conexion);
    }

    public List<PersonaVehiculoDTO> filtrarPersonasConVehiculos(String nombre, String marca, String modelo, String sexo, Integer anoMatriculacion) {
        List<PersonaVehiculoDTO> listaPersonas = new ArrayList<>();

        // Construcción de la consulta base
        StringBuilder query = new StringBuilder("SELECT p.nombre, p.sexo, c.matricula, c.anoMatriculacion, c.marca, c.modelo "
                + "FROM historico h "
                + "JOIN personas p ON h.id_persona = p.id "
                + "JOIN coches c ON h.id_coche = c.id "
                + "WHERE h.fecha_fin IS NULL");

        // Lista de parámetros para el PreparedStatement
        List<Object> parametros = new ArrayList<>();

        // Añadir condiciones de filtro dinámicamente
        if (nombre != null && !nombre.isEmpty()) {
            query.append(" AND p.nombre LIKE ?");
            parametros.add("%" + nombre.trim() + "%");
        }
        if (marca != null && !marca.equals("Todos")) {
            query.append(" AND c.marca = ?");
            parametros.add(marca.trim());
        }
        if (modelo != null && !modelo.equals("Todos")) {
            query.append(" AND c.modelo = ?");
            parametros.add(modelo.trim());
        }
        if (sexo != null && !sexo.isEmpty()) {
            query.append(" AND p.sexo = ?");
            parametros.add(sexo.trim());
        }
        if (anoMatriculacion != null && anoMatriculacion > 0) {
            query.append(" AND c.anoMatriculacion = ?");
            parametros.add(anoMatriculacion);
        }

        // Ejecutar la consulta con los parámetros
        try (Connection conexion = new Conectar().abrirConexion();
             PreparedStatement pstmt = conexion.prepareStatement(query.toString())) {

            // Asignar los valores de los parámetros en el PreparedStatement
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                // Procesar el ResultSet y mapearlo a la lista de PersonaVehiculoDTO
                while (rs.next()) {
                    String nombrePersona = rs.getString("nombre");
                    String sexoPersona = rs.getString("sexo");
                    String matricula = rs.getString("matricula");
                    int ano = rs.getInt("anoMatriculacion");
                    String marcaCoche = rs.getString("marca");
                    String modeloCoche = rs.getString("modelo");

                    // Crear el objeto PersonaVehiculoDTO y añadirlo a la lista
                    PersonaVehiculoDTO personaVehiculo = new PersonaVehiculoDTO(nombrePersona, sexoPersona, matricula, ano, marcaCoche, modeloCoche);
                    listaPersonas.add(personaVehiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones (puedes mejorarlo para mostrar mensajes al usuario o loguearlo)
        }

        return listaPersonas;
    }

    @Override
    protected String getTableName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getInsertQuery(PersonasVehiculos entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setInsertValues(PreparedStatement stmt, PersonasVehiculos entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getUpdateQuery(PersonasVehiculos entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateValues(PreparedStatement stmt, PersonasVehiculos entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected int getUpdateParameterCount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected PersonasVehiculos mapResultSetToEntity(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
