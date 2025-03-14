/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.HistorialPropietariosDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laura
 */
public class HistorialPropietariosDAO {

    private Connection conexion;

    // Constructor
    public HistorialPropietariosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener los registros de historial de propietarios con filtros y paginación
    public List<HistorialPropietariosDTO> obtenerHistorialPaginadoConFiltros(int elementosPorPagina, int offset, String filtroColumna, String filtroValor) throws SQLException {
        List<HistorialPropietariosDTO> historialList = new ArrayList<>();
        String sql = "SELECT * FROM historialdepropietarios WHERE " + filtroColumna + " LIKE ? LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + filtroValor + "%");  // Filtro con LIKE
            stmt.setInt(2, elementosPorPagina);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setId(rs.getInt("id"));
                historial.setIdPersonas(rs.getInt("persona_id"));
                historial.setIdCoches(rs.getInt("vehiculo_id"));
                historial.setFechaTransaccion(rs.getDate("fecha"));
                historialList.add(historial);
            }
        }

        return historialList;
    }

    // Método para contar el total de registros con un filtro
    public int contarTotalHistorialConFiltro(String filtroColumna, String filtroValor) throws SQLException {
        String sql = "SELECT COUNT(*) FROM historialdepropietarios WHERE " + filtroColumna + " LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + filtroValor + "%");  // Filtro con LIKE
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Método para obtener todos los registros de historial de propietarios
    public List<HistorialPropietariosDTO> findAll() throws SQLException {
        List<HistorialPropietariosDTO> historialList = new ArrayList<>();
        String sql = "SELECT * FROM historialdepropietarios";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setId(rs.getInt("id"));
                historial.setIdPersonas(rs.getInt("persona_id"));
                historial.setIdCoches(rs.getInt("vehiculo_id"));
                historial.setFechaTransaccion(rs.getDate("fecha"));
                historialList.add(historial);
            }
        }

        return historialList;
    }

    // Método para contar el total de registros sin filtros
    public int contarTotalHistorial() throws SQLException {
        String sql = "SELECT COUNT(*) FROM historialdepropietarios";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Método para obtener un historial de propietario por ID
    public HistorialPropietariosDTO findById(int id) throws SQLException {
        String sql = "SELECT * FROM historialdepropietarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setId(rs.getInt("id"));
                historial.setIdPersonas(rs.getInt("persona_id"));
                historial.setIdCoches(rs.getInt("vehiculo_id"));
                historial.setFechaTransaccion(rs.getDate("fecha"));
                return historial;
            }
        }
        return null;
    }

    // Método para insertar un historial de propietario
    public void create(HistorialPropietariosDTO historialPropietariosDTO) throws SQLException {
        String sql = "INSERT INTO historialdepropietarios (persona_id, vehiculo_id, fecha) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, historialPropietariosDTO.getIdPersonas());
            stmt.setInt(2, historialPropietariosDTO.getIdCoches());
            stmt.setDate(3, new Date(historialPropietariosDTO.getFechaTransaccion().getTime()));
            stmt.executeUpdate();
        }
    }

    // Método para actualizar un historial de propietario
    public boolean update(int id, HistorialPropietariosDTO historialPropietariosDTO) throws SQLException {
        String sql = "UPDATE historialdepropietarios SET persona_id = ?, vehiculo_id = ?, fecha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, historialPropietariosDTO.getIdPersonas());
            stmt.setInt(2, historialPropietariosDTO.getIdCoches());
            stmt.setDate(3, new Date(historialPropietariosDTO.getFechaTransaccion().getTime()));
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para eliminar un historial de propietario
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM historialdepropietarios WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método para obtener los registros de historial de propietarios con filtros y paginación (con múltiples filtros)
    public List<HistorialPropietariosDTO> obtenerHistorialPaginadoConFiltros(int elementosPorPagina, int offset, String nombre, String marca, String sexo, String modelo, String anio, String numVehiculos) throws SQLException {
        List<HistorialPropietariosDTO> historialList = new ArrayList<>();

        // Comenzamos la consulta básica
        StringBuilder sql = new StringBuilder("SELECT * FROM historialdepropietarios hp ");
        sql.append("JOIN personas p ON hp.persona_id = p.id ");
        sql.append("JOIN coches c ON hp.vehiculo_id = c.id ");
        sql.append("WHERE 1=1 ");  // Condición para facilitar la concatenación de filtros

        // Añadir filtros si no son nulos o vacíos
        if (nombre != null && !nombre.isEmpty()) {
            sql.append("AND p.nombre LIKE ? ");
        }
        if (marca != null && !marca.isEmpty()) {
            sql.append("AND c.marca LIKE ? ");
        }
        if (sexo != null && !sexo.isEmpty()) {
            sql.append("AND p.sexo LIKE ? ");
        }
        if (modelo != null && !modelo.isEmpty()) {
            sql.append("AND c.modelo LIKE ? ");
        }
        if (anio != null && !anio.isEmpty()) {
            sql.append("AND c.anoMatriculacion LIKE ? ");
        }
        if (numVehiculos != null && !numVehiculos.isEmpty()) {
            sql.append("AND (SELECT COUNT(*) FROM historialdepropietarios WHERE vehiculo_id = c.id) = ? ");
        }

        // Añadir la paginación (LIMIT y OFFSET)
        sql.append("LIMIT ? OFFSET ?");

        // Preparar la consulta
        try (PreparedStatement stmt = conexion.prepareStatement(sql.toString())) {
            int index = 1;

            // Establecer los parámetros para los filtros
            if (nombre != null && !nombre.isEmpty()) {
                stmt.setString(index++, "%" + nombre + "%");
            }
            if (marca != null && !marca.isEmpty()) {
                stmt.setString(index++, "%" + marca + "%");
            }
            if (sexo != null && !sexo.isEmpty()) {
                stmt.setString(index++, "%" + sexo + "%");
            }
            if (modelo != null && !modelo.isEmpty()) {
                stmt.setString(index++, "%" + modelo + "%");
            }
            if (anio != null && !anio.isEmpty()) {
                stmt.setString(index++, "%" + anio + "%");
            }
            if (numVehiculos != null && !numVehiculos.isEmpty()) {
                stmt.setString(index++, numVehiculos);
            }

            // Establecer los parámetros de paginación
            stmt.setInt(index++, elementosPorPagina);
            stmt.setInt(index++, offset);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setId(rs.getInt("hp.id"));
                historial.setIdPersonas(rs.getInt("hp.persona_id"));
                historial.setIdCoches(rs.getInt("hp.vehiculo_id"));
                historial.setFechaTransaccion(rs.getDate("hp.fecha"));
                historialList.add(historial);
            }
        }

        return historialList;
    }

    // Método para buscar historial de propietarios por un atributo específico
    public List<HistorialPropietariosDTO> buscarPorAtributo(String columna, String valor) throws SQLException {
        List<HistorialPropietariosDTO> historialList = new ArrayList<>();

        // Validar que la columna es válida
        if (columna == null || columna.isEmpty()) {
            throw new IllegalArgumentException("La columna no puede ser nula o vacía");
        }

        // Asegúrate de que el valor de la columna sea algo razonable para evitar inyecciones SQL
        String sql = "SELECT * FROM historialdepropietarios WHERE " + columna + " LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + valor + "%");  // Filtro con LIKE
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setId(rs.getInt("id"));
                historial.setIdPersonas(rs.getInt("persona_id"));
                historial.setIdCoches(rs.getInt("vehiculo_id"));
                historial.setFechaTransaccion(rs.getDate("fecha"));
                historialList.add(historial);
            }
        }

        return historialList;
    }

    public HistorialPropietariosDTO findByIdCoche(int idCoche) throws SQLException {
        String sql = "SELECT * FROM historial_propietarios WHERE idCoche = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCoche);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setIdCoches(rs.getInt("idCoche"));
                historial.setIdPersonas(rs.getInt("idPersona"));
                // Agregar más campos si es necesario
                return historial;
            }
        }
        return null;  // Si no se encuentra el historial, devolver null
    }

    // Método para buscar el historial de propietario por idCoche
    public HistorialPropietariosDTO buscarPorIdCoche(int idCoche) throws SQLException {
        String query = "SELECT * FROM historialpropietarios WHERE id_coche = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idCoche);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mapear el resultado a un DTO
                HistorialPropietariosDTO historial = new HistorialPropietariosDTO();
                historial.setIdCoches(rs.getInt("id_coche"));
                historial.setIdPersonas(rs.getInt("id_persona"));
                return historial;
            } else {
                return null;  // No se encontró historial para el coche
            }
        }
    }

    public List<HistorialPropietariosDTO> obtenerHistorialCompleto() throws SQLException {
        List<HistorialPropietariosDTO> historial = new ArrayList<>();
        String query = "SELECT hp.*, p.nombre AS nombrePersona FROM historialpropietarios hp "
                + "JOIN personas p ON hp.idPersona = p.id"; // Asegúrate de usar 'idPersona' en lugar de 'id_personas'

        try (PreparedStatement stmt = conexion.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HistorialPropietariosDTO registro = new HistorialPropietariosDTO();
                registro.setId(rs.getInt("id"));
                registro.setIdCoches(rs.getInt("idCoche"));
                registro.setIdPersonas(rs.getInt("idPersona"));
                registro.setFechaTransaccion(rs.getDate("fechaCambio"));
                registro.setNombrePersona(rs.getString("nombrePersona")); // Carga el nombre de la persona
                historial.add(registro);
            }
        }
        return historial;
    }

    public int obtenerNumeroDeVehiculosPorPersona(String nombre) throws SQLException {
        String query = "SELECT COUNT(DISTINCT idCoches) AS numVehiculos FROM historialpropietarios WHERE idPersonas = ?";
        int numVehiculos = 0;

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    numVehiculos = rs.getInt("numVehiculos");
                }
            }
        }
        return numVehiculos;
    }

    public int contarVehiculosPorNombrePersona(String nombrePersona) throws SQLException {
        String sql = "SELECT COUNT(*) FROM historialpropietarios hp "
                + "JOIN personas p ON hp.id_personas = p.id "
                + "WHERE p.nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombrePersona);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public int contarVehiculosPorPersona(int personaId) throws SQLException {
        String query = "SELECT COUNT(*) FROM historialpropietarios WHERE idPersona = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, personaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void asociarVehiculoAPersona(int idPersona, int idCoche) throws SQLException {
        String query = "INSERT INTO historialpropietarios (idPersona, idCoche, fechaCambio) VALUES (?, ?, CURRENT_DATE())";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idPersona);
            stmt.setInt(2, idCoche);
            stmt.executeUpdate();
        }
    }
}
