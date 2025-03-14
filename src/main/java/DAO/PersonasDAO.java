/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.PersonasDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Personas;

public class PersonasDAO extends GenericoDAO<Personas> {

    private Connection conexion;

    public PersonasDAO(Connection conexion) {
        super(conexion);
        this.conexion = conexion;
    }

    @Override
    protected String getTableName() {
        return "personas";
    }

    @Override
    protected String getInsertQuery(Personas entity) {
        return "INSERT INTO personas (nombre, dni, sexo) VALUES (?, ?, ?)";
    }

    @Override
    protected void setInsertValues(PreparedStatement stmt, Personas entity) throws SQLException {
        stmt.setString(1, entity.getNombre());
        stmt.setString(2, entity.getDni());
        stmt.setInt(3, entity.getSexo().getValor());  // Se usa getValor() para obtener el valor numérico 1 o 2
    }

    @Override
    protected String getUpdateQuery(Personas entity) {
        return "UPDATE personas SET nombre = ?, dni = ?, sexo = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateValues(PreparedStatement stmt, Personas entity) throws SQLException {
        stmt.setString(1, entity.getNombre());
        stmt.setString(2, entity.getDni());
        stmt.setInt(3, entity.getSexo().getValor());  // Se usa getValor() para obtener el valor numérico 1 o 2
    }

    @Override
    protected int getUpdateParameterCount() {
        return 3;  // Porque hay 3 parámetros a actualizar
    }

    @Override
    protected Personas mapResultSetToEntity(ResultSet rs) throws SQLException {
        Personas persona = new Personas();
        persona.setId(rs.getInt("id"));
        persona.setNombre(rs.getString("nombre"));
        persona.setDni(rs.getString("dni"));
        persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));  // Se usa fromInt() para convertir el valor numérico a un enum Sexo
        return persona;
    }

    // Método para buscar personas por un atributo
    public List<PersonasDTO> buscarPorAtributoPersonas(String columna, String valor) throws SQLException {
        List<PersonasDTO> listaPersonas = new ArrayList<>();

        // Verifica que la columna sea una de las permitidas para evitar inyección SQL
        List<String> columnasValidas = List.of("nombre", "dni", "sexo");  // Lista de columnas válidas
        if (!columnasValidas.contains(columna)) {
            throw new SQLException("Columna inválida.");
        }

        String sql = "SELECT * FROM personas WHERE " + columna + " LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + valor + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Cambié de Personas a PersonasDTO
                    PersonasDTO persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setDni(rs.getString("dni"));
                    persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));  // Usamos fromInt para convertir a Sexo
                    listaPersonas.add(persona);
                }
            }
        }

        return listaPersonas;
    }

    // Método para buscar una persona por su ID
    public PersonasDTO buscarPorId(int id) throws SQLException {
        PersonasDTO persona = null;
        String query = "SELECT * FROM personas WHERE id = ?";  // La consulta SQL para buscar por ID
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);  // Asignamos el ID al parámetro de la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Si se encuentra la persona, creamos el objeto DTO
                    persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));  // Usamos fromInt para convertir el valor numérico a Sexo
                    // Asignar otros campos según sea necesario
                }
            }
        }
        return persona;  // Devuelve la persona encontrada (o null si no se encuentra)
    }

    public List<PersonasDTO> buscarPorFiltros(String nombre, String anoMatriculacion, String numVehiculos,
            String sexo, String marca, String modelo) throws SQLException {
        List<PersonasDTO> listaPersonas = new ArrayList<>();

        // Consulta base
        StringBuilder sql = new StringBuilder("SELECT * FROM personas p ");
        sql.append("JOIN coches c ON p.id = c.id_propietario WHERE 1=1 ");

        // Agregar condiciones dependiendo de los filtros
        if (nombre != null && !nombre.isEmpty()) {
            sql.append("AND p.nombre LIKE ? ");
        }
        if (anoMatriculacion != null && !anoMatriculacion.isEmpty()) {
            sql.append("AND c.anoMatriculacion = ? ");
        }
        if (numVehiculos != null && !numVehiculos.isEmpty()) {
            sql.append("AND c.numVehiculos = ? ");
        }
        if (sexo != null && !sexo.isEmpty()) {
            sql.append("AND p.sexo = ? ");
        }
        if (marca != null && !marca.isEmpty()) {
            sql.append("AND c.marca = ? ");
        }
        if (modelo != null && !modelo.isEmpty()) {
            sql.append("AND c.modelo = ? ");
        }

        try (PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {
            int index = 1;

            // Establecer los parámetros de la consulta
            if (nombre != null && !nombre.isEmpty()) {
                stmt.setString(index++, "%" + nombre + "%");
            }
            if (anoMatriculacion != null && !anoMatriculacion.isEmpty()) {
                stmt.setString(index++, anoMatriculacion);
            }
            if (numVehiculos != null && !numVehiculos.isEmpty()) {
                stmt.setString(index++, numVehiculos);
            }
            if (sexo != null && !sexo.isEmpty()) {
                stmt.setString(index++, sexo);
            }
            if (marca != null && !marca.isEmpty()) {
                stmt.setString(index++, marca);
            }
            if (modelo != null && !modelo.isEmpty()) {
                stmt.setString(index++, modelo);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PersonasDTO persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setDni(rs.getString("dni"));
                    persona.setSexo(rs.getInt("sexo"));
                    listaPersonas.add(persona);
                }
            }
        }

        return listaPersonas;
    }
    
    // Método para obtener todas las personas de la base de datos
    public List<PersonasDTO> obtenerTodasLasPersonas() throws SQLException {
        List<PersonasDTO> listaPersonas = new ArrayList<>();
        String query = "SELECT id, nombre, dni FROM personas";

        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PersonasDTO persona = new PersonasDTO();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setDni(rs.getString("dni"));

                listaPersonas.add(persona);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todas las personas: " + e.getMessage(), e);
        }

        return listaPersonas;
    }
}
