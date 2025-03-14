/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.CochesDAO;
import DTO.CochesDTO;
import DTO.PaginacionDTO;
import DTO.PersonasDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laura
 */
public class CochesServicio {

    private CochesDAO cochesDAO;

    // Constructor
    public CochesServicio(CochesDAO cochesDAO) {
        this.cochesDAO = cochesDAO;
    }

    public List<CochesDTO> buscarCochesPorAtributos(String marca, String modelo, String anoMatriculacion) throws SQLException {
        return cochesDAO.buscarPorAtributos(marca, modelo, anoMatriculacion);
    }

    // Obtener coches paginados
    public PaginacionDTO<CochesDTO> obtenerCochesPaginados(int paginaActual, int elementosPorPagina) throws SQLException {
        int offset = (paginaActual - 1) * elementosPorPagina;
        // Llamada al método del DAO para obtener los coches paginados
        List<CochesDTO> coches = cochesDAO.obtenerCochesPaginados(elementosPorPagina, offset);
        // Obtener el total de elementos para la paginación
        int totalElementos = cochesDAO.contarTotalCoches();
        // Calcular el total de páginas
        int totalPaginas = (int) Math.ceil((double) totalElementos / elementosPorPagina);

        // Crear el objeto PaginacionDTO
        PaginacionDTO<CochesDTO> paginacion = new PaginacionDTO<>();
        paginacion.setDatos(coches);
        paginacion.setPaginaActual(paginaActual);
        paginacion.setTotalElementos(totalElementos);
        paginacion.setTotalPaginas(totalPaginas);
        paginacion.setElementosPorPagina(elementosPorPagina);

        return paginacion;
    }

    public List<CochesDTO> buscarCochesAvanzados(CochesDTO cocheFiltro, PersonasDTO personaFiltro) throws SQLException {
        // Construir la consulta dinámica
        StringBuilder query = new StringBuilder("SELECT * FROM coches ");
        List<String> condiciones = new ArrayList<>();

        if (cocheFiltro.getMarca() != null) {
            condiciones.add("marca LIKE ?");
        }
        if (cocheFiltro.getModelo() != null) {
            condiciones.add("modelo LIKE ?");
        }
        // Aquí verificamos si el año de matriculación es diferente de -1 (valor por defecto)
        if (cocheFiltro.getAnoMatriculacion() != -1) {
            condiciones.add("anoMatriculacion = ?");
        }

        // Si también quieres filtrar por persona (por ejemplo, por DNI o nombre)
        if (personaFiltro.getNombre() != null) {
            condiciones.add("persona_nombre LIKE ?");
        }
        if (personaFiltro.getDni() != null) {
            condiciones.add("persona_dni LIKE ?");
        }

        // Si existen condiciones, agregar al query
        if (!condiciones.isEmpty()) {
            query.append("WHERE ");
            query.append(String.join(" AND ", condiciones));
        }

        // Ejecutar la consulta con los parámetros que no sean null
        List<CochesDTO> coches = new ArrayList<>();
        try (PreparedStatement stmt = cochesDAO.getConexion().prepareStatement(query.toString())) {
            int index = 1;
            if (cocheFiltro.getMarca() != null) {
                stmt.setString(index++, "%" + cocheFiltro.getMarca() + "%");
            }
            if (cocheFiltro.getModelo() != null) {
                stmt.setString(index++, "%" + cocheFiltro.getModelo() + "%");
            }
            if (cocheFiltro.getAnoMatriculacion() != -1) {
                stmt.setInt(index++, cocheFiltro.getAnoMatriculacion());
            }
            if (personaFiltro.getNombre() != null) {
                stmt.setString(index++, "%" + personaFiltro.getNombre() + "%");
            }
            if (personaFiltro.getDni() != null) {
                stmt.setString(index++, "%" + personaFiltro.getDni() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CochesDTO coche = new CochesDTO();
                coche.setId(rs.getInt("id"));
                coche.setMarca(rs.getString("marca"));
                coche.setModelo(rs.getString("modelo"));
                coche.setAnoMatriculacion(rs.getInt("anoMatriculacion"));
                coches.add(coche);
            }
        }

        return coches;
    }

    // Método para obtener las marcas
    public List<String> obtenerMarcas() {
        try {
            return cochesDAO.obtenerMarcas(); // Llamar al DAO para obtener las marcas
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retornar lista vacía en caso de error
        }
    }

    // Método para obtener los modelos por marca
    public List<String> obtenerModelosPorMarca(String marca) {
        try {
            return cochesDAO.obtenerModelosPorMarca(marca); // Llamar al DAO para obtener los modelos
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retornar lista vacía en caso de error
        }
    }

    public void agregarCoche(CochesDTO coche) throws SQLException {
        cochesDAO.insertarCoche(coche);
    }

    public List<CochesDTO> obtenerTodosLosCoches() throws SQLException {
        return cochesDAO.obtenerTodosLosCoches();
    }
}
