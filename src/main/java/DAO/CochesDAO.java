/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.CochesDTO;
import DTO.PersonasDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CochesDAO {

    private Connection conexion;

    // Constructor
    public CochesDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public List<CochesDTO> buscarPorAtributos(String marca, String modelo, String anoMatriculacion) throws SQLException {
        List<CochesDTO> coches = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM coches WHERE 1=1");

        if (marca != null && !marca.isEmpty()) {
            query.append(" AND marca LIKE ?");
        }
        if (modelo != null && !modelo.isEmpty()) {
            query.append(" AND modelo LIKE ?");
        }
        if (anoMatriculacion != null && !anoMatriculacion.isEmpty()) {
            query.append(" AND anoMatriculacion = ?");
        }

        try (PreparedStatement stmt = conexion.prepareStatement(query.toString())) {
            int index = 1;

            if (marca != null && !marca.isEmpty()) {
                stmt.setString(index++, "%" + marca + "%");
            }
            if (modelo != null && !modelo.isEmpty()) {
                stmt.setString(index++, "%" + modelo + "%");
            }
            if (anoMatriculacion != null && !anoMatriculacion.isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(anoMatriculacion));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CochesDTO coche = new CochesDTO();
                    coche.setId(rs.getInt("id"));
                    coche.setMarca(rs.getString("marca"));
                    coche.setModelo(rs.getString("modelo"));
                    coche.setMatricula(rs.getString("matricula"));
                    coche.setAnoMatriculacion(rs.getInt("anoMatriculacion"));
                    coches.add(coche);
                }
            }
        }
        return coches;
    }

    // Obtener coches paginados
    public List<CochesDTO> obtenerCochesPaginados(int elementosPorPagina, int offset) throws SQLException {
        List<CochesDTO> coches = new ArrayList<>();
        String query = "SELECT * FROM coches LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, elementosPorPagina);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CochesDTO coche = new CochesDTO();
                coche.setId(rs.getInt("id"));
                coche.setMarca(rs.getString("marca"));
                coche.setModelo(rs.getString("modelo"));
                coche.setMatricula(rs.getString("matricula"));
                coche.setAnoMatriculacion(rs.getInt("anoMatriculacion"));
                coches.add(coche);
            }
        }
        return coches;
    }

    // Contar el total de coches
    public int contarTotalCoches() throws SQLException {
        String query = "SELECT COUNT(*) FROM coches";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<String> obtenerMarcas() throws SQLException {
        List<String> marcas = new ArrayList<>();

        // Consulta SQL para obtener las marcas distintas
        String sql = "SELECT DISTINCT marca FROM coches";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                marcas.add(rs.getString("marca")); // Añadir cada marca a la lista
            }
        }

        return marcas;
    }

    public List<String> obtenerModelosPorMarca(String marca) throws SQLException {
        List<String> modelos = new ArrayList<>();

        // Consulta SQL para obtener los modelos de la marca seleccionada
        String sql = "SELECT DISTINCT modelo FROM coches WHERE marca = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, marca); // Establecer la marca como parámetro
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    modelos.add(rs.getString("modelo")); // Añadir cada modelo a la lista
                }
            }
        }

        return modelos;
    }

    public List<CochesDTO> buscarCochesAvanzados(CochesDTO cocheFiltro, PersonasDTO personaFiltro) throws SQLException {
        List<CochesDTO> coches = new ArrayList<>();
        // Crear la consulta SQL con los filtros
        String query = "SELECT c.*, p.nombre FROM coches c "
                + "INNER JOIN personas p ON c.id_persona = p.id WHERE 1=1";

        if (cocheFiltro.getMarca() != null && !cocheFiltro.getMarca().isEmpty()) {
            query += " AND c.marca = ?";
        }
        if (cocheFiltro.getModelo() != null && !cocheFiltro.getModelo().isEmpty()) {
            query += " AND c.modelo = ?";
        }
        if (cocheFiltro.getAnoMatriculacion() != 0) {  // Cambiado a verificar si es 0, ya que es un int
            query += " AND c.anoMatriculacion = ?";
        }

        // Preparar la conexión y el statement
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            int index = 1;

            if (cocheFiltro.getMarca() != null && !cocheFiltro.getMarca().isEmpty()) {
                stmt.setString(index++, cocheFiltro.getMarca());
            }
            if (cocheFiltro.getModelo() != null && !cocheFiltro.getModelo().isEmpty()) {
                stmt.setString(index++, cocheFiltro.getModelo());
            }
            if (cocheFiltro.getAnoMatriculacion() != 0) {  // Usamos el valor int para el año
                stmt.setInt(index++, cocheFiltro.getAnoMatriculacion());
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Crear los objetos DTO y agregarlos a la lista
                CochesDTO coche = new CochesDTO();
                coche.setMarca(rs.getString("marca"));
                coche.setModelo(rs.getString("modelo"));
                coche.setAnoMatriculacion(rs.getInt("anoMatriculacion"));

                // Obtener el nombre de la persona asociada
                PersonasDTO persona = new PersonasDTO();
                persona.setNombre(rs.getString("nombre"));

                // Agregar el coche a la lista
                coches.add(coche);
            }
        }
        return coches;
    }

    public void insertarCoche(CochesDTO coche) throws SQLException {
        String query = "INSERT INTO coches (matricula, anoMatriculacion, marca, modelo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, coche.getMatricula());
            stmt.setInt(2, coche.getAnoMatriculacion());
            stmt.setString(3, coche.getMarca());
            stmt.setString(4, coche.getModelo());

            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los coches de la base de datos
    public List<CochesDTO> obtenerTodosLosCoches() throws SQLException {
        List<CochesDTO> listaCoches = new ArrayList<>();
        String query = "SELECT id, matricula, anoMatriculacion, marca, modelo FROM coches";

        try (PreparedStatement stmt = conexion.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CochesDTO coche = new CochesDTO();
                coche.setId(rs.getInt("id"));
                coche.setMatricula(rs.getString("matricula"));
                coche.setAnoMatriculacion(rs.getInt("anoMatriculacion"));
                coche.setMarca(rs.getString("marca"));
                coche.setModelo(rs.getString("modelo"));

                listaCoches.add(coche);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al obtener todos los coches: " + e.getMessage(), e);
        }

        return listaCoches;
    }
}
