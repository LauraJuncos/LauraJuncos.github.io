/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.CochesDTO;
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

    // Método ajustado para buscar coches por atributos
    public List<CochesDTO> buscarPorAtributos(String marca, String modelo, String anoMatriculacion) {
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
            } catch (SQLException e) {
                System.err.println("Error al buscar atributos coches por filtros: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar atributos coches por filtros: " + e.getMessage());
            e.printStackTrace();
        }
        return coches;
    }

    // Obtener coches paginados
    public List<CochesDTO> obtenerCochesPaginados(int elementosPorPagina, int offset) {
        List<CochesDTO> coches = new ArrayList<>();
        String query = "SELECT * FROM coches LIMIT ? OFFSET ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, elementosPorPagina);
            stmt.setInt(2, offset);
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
        } catch (SQLException e) {
            System.err.println("Error al obtener coches paginados: " + e.getMessage());
            e.printStackTrace();
        }
        return coches;
    }

    // Contar el total de coches
    public int contarTotalCoches() {
        String query = "SELECT COUNT(*) FROM coches";
        try (PreparedStatement stmt = conexion.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar total de coches: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Obtener marcas
    public List<String> obtenerMarcas() {
        List<String> marcas = new ArrayList<>();
        String sql = "SELECT DISTINCT marca FROM coches";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                marcas.add(rs.getString("marca"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener marcas: " + e.getMessage());
            e.printStackTrace();
        }
        return marcas;
    }

    // Obtener modelos por marca
    public List<String> obtenerModelosPorMarca(String marca) {
        List<String> modelos = new ArrayList<>();
        String sql = "SELECT DISTINCT modelo FROM coches WHERE marca = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, marca);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    modelos.add(rs.getString("modelo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener modelos: " + e.getMessage());
            e.printStackTrace();
        }
        return modelos;
    }

    // Método para insertar un coche
    public void insertarCoche(CochesDTO coche) {
        String query = "INSERT INTO coches (matricula, anoMatriculacion, marca, modelo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, coche.getMatricula());
            stmt.setInt(2, coche.getAnoMatriculacion());
            stmt.setString(3, coche.getMarca());
            stmt.setString(4, coche.getModelo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar coche: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Obtener todos los coches
    public List<CochesDTO> obtenerTodosLosCoches() {
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
            System.err.println("Error al obtener todos los coches: " + e.getMessage());
            e.printStackTrace();
        }

        return listaCoches;
    }
    
    public int contarTotalRegistros() {
    String query = "SELECT COUNT(*) FROM coches";
    try (PreparedStatement stmt = conexion.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;  // Si ocurre un error, devolver 0
}
    // Metodo para eliminar un coche por su ID
    public void eliminarCoche(int idCoche) {
        String query = "DELETE FROM coches WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idCoche);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Coche eliminado exitosamente.");
            } else {
                System.err.println("No se encontro un coche con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el coche: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
