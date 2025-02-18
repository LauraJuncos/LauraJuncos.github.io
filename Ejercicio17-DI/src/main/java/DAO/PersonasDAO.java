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
import javax.swing.JOptionPane;
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
    protected void setInsertValues(PreparedStatement stmt, Personas entity) {
        try {
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getDni());
            stmt.setInt(3, entity.getSexo().getValor());
        } catch (SQLException e) {
            System.err.println("Error al configurar los valores para insertar una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Se ha detectado un valor nulo al configurar los valores de inserción para una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error en los valores proporcionados para la inserción de una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al configurar los valores para insertar una persona: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    protected String getUpdateQuery(Personas entity) {
        return "UPDATE personas SET nombre = ?, dni = ?, sexo = ? WHERE id = ?";
    }

    @Override
    protected void setUpdateValues(PreparedStatement stmt, Personas entity) {
        try {
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getDni());
            stmt.setInt(3, entity.getSexo().getValor());
            stmt.setInt(4, entity.getId());
        } catch (SQLException e) {
            System.err.println("Error al configurar los valores para actualizar una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Se ha detectado un valor nulo al configurar los valores de actualización para una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error en los valores proporcionados para la actualización de una persona: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al configurar los valores para actualizar una persona: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected int getUpdateParameterCount() {
        return 4;
    }

    @Override
    protected Personas mapResultSetToEntity(ResultSet rs) {
        Personas persona = new Personas();
        try {
            persona.setId(rs.getInt("id"));
            persona.setNombre(rs.getString("nombre"));
            persona.setDni(rs.getString("dni"));
            persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));
        } catch (SQLException e) {
            System.err.println("Error al mapear los resultados del ResultSet a una entidad Persona: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al mapear los resultados del ResultSet a una entidad Persona: " + e.getMessage());
            e.printStackTrace();
        }
        return persona;
    }

    // Método para buscar personas por un atributo específico con validación de entradas
    public List<PersonasDTO> buscarPorAtributoPersonas(String columna, String valor) {
        List<PersonasDTO> listaPersonas = new ArrayList<>();

        // Verifica que la columna sea válida para evitar inyección SQL
        List<String> columnasValidas = List.of("nombre", "dni", "sexo");
        if (!columnasValidas.contains(columna)) {
            System.err.println("Columna inválida.");
            return listaPersonas;
        }

        String sql = "SELECT * FROM personas WHERE " + columna + " LIKE ? ORDER BY nombre";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + valor + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PersonasDTO persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setDni(rs.getString("dni"));
                    persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));
                    listaPersonas.add(persona);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar personas por atributo: " + e.getMessage());
            mostrarMensajeError("Error al buscar personas.");
        }

        return listaPersonas;
    }

    // Método para mostrar un mensaje de error al usuario
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Método para obtener todas las personas de la base de datos
    public List<PersonasDTO> obtenerTodasLasPersonas() {
        List<PersonasDTO> listaPersonas = new ArrayList<>();
        String query = "SELECT * FROM personas ORDER BY nombre";

        try (PreparedStatement stmt = conexion.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PersonasDTO persona = new PersonasDTO();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setDni(rs.getString("dni"));
                persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));
                listaPersonas.add(persona);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las personas: " + e.getMessage());
            e.printStackTrace();
        }

        return listaPersonas;
    }

    // Método para buscar una persona por ID
    public PersonasDTO buscarPorId(int id) {
        PersonasDTO persona = null;
        String query = "SELECT * FROM personas WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setDni(rs.getString("dni"));
                    persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la persona por ID: " + e.getMessage());
            e.printStackTrace();
        }

        return persona;
    }

    public List<PersonasDTO> buscarPorFiltros(String nombre, String anoMatriculacion, String numVehiculos,
            String sexo, String marca, String modelo) {
        List<PersonasDTO> listaPersonas = new ArrayList<>();

        // Construir la consulta base con filtros opcionales
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(h.idCoche) AS numVehiculos FROM personas p ");
        sql.append("LEFT JOIN historialPropietarios h ON p.id = h.idPersona ");
        sql.append("LEFT JOIN coches c ON h.idCoche = c.id WHERE 1=1 ");

        // Agregar condiciones dependiendo de los filtros
        if (nombre != null && !nombre.isEmpty()) {
            sql.append("AND p.nombre LIKE ? ");
        }
        if (anoMatriculacion != null && !anoMatriculacion.isEmpty()) {
            sql.append("AND c.anoMatriculacion = ? ");
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

        // Agrupar por la persona y filtrar por número de vehículos si es necesario
        sql.append("GROUP BY p.id, p.nombre, p.dni, p.sexo ");
        if (numVehiculos != null && !numVehiculos.isEmpty()) {
            sql.append("HAVING COUNT(h.idCoche) = ? ");
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
            if (sexo != null && !sexo.isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(sexo));
            }
            if (marca != null && !marca.isEmpty()) {
                stmt.setString(index++, marca);
            }
            if (modelo != null && !modelo.isEmpty()) {
                stmt.setString(index++, modelo);
            }
            if (numVehiculos != null && !numVehiculos.isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(numVehiculos));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PersonasDTO persona = new PersonasDTO();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setDni(rs.getString("dni"));
                    persona.setSexo(Personas.Sexo.fromInt(rs.getInt("sexo")));
                    listaPersonas.add(persona);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar personas por filtros: " + e.getMessage());
            e.printStackTrace();
        }

        return listaPersonas;
    }
    

    // Método para insertar una persona
    public void insertarPersona(PersonasDTO persona) {
    String query = "INSERT INTO personas (nombre, dni, sexo) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = conexion.prepareStatement(query)) {
        stmt.setString(1, persona.getNombre());
        stmt.setString(2, persona.getDni());
        stmt.setInt(3, persona.getSexo().getValor()); // Insertar el valor entero del sexo

        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error al insertar persona: " + e.getMessage());
        e.printStackTrace();
    }
}

    // Metodo para eliminar una persona por su ID
    public void eliminarPersona(int idPersona) {
        String query = "DELETE FROM personas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idPersona);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Persona eliminada exitosamente.");
            } else {
                System.err.println("No se encontro una persona con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar la persona: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
