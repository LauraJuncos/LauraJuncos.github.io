/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.HistorialPropietariosDAO;
import DTO.HistorialPropietariosDTO;
import DTO.PaginacionDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */
public class HistorialPropietariosServicio {

    private HistorialPropietariosDAO historialPropietariosDAO;

    // Constructor donde se inicializa el DAO
    public HistorialPropietariosServicio(HistorialPropietariosDAO historialPropietariosDAO) {
        this.historialPropietariosDAO = historialPropietariosDAO;
    }

    // Método para obtener la paginación de historial de propietarios con filtros
    public PaginacionDTO<HistorialPropietariosDTO> obtenerHistorialPaginadoConFiltros(
            int paginaActual, int elementosPorPagina, String filtroColumna, String filtroValor) throws SQLException {

        // Calcular el offset para la paginación
        int offset = (paginaActual - 1) * elementosPorPagina;

        // Obtener los elementos paginados con el filtro
        List<HistorialPropietariosDTO> historial = historialPropietariosDAO.obtenerHistorialPaginadoConFiltros(
                elementosPorPagina, offset, filtroColumna, filtroValor);

        // Obtener el total de elementos con filtro
        int totalElementos = historialPropietariosDAO.contarTotalHistorialConFiltro(filtroColumna, filtroValor);
        int totalPaginas = (int) Math.ceil((double) totalElementos / elementosPorPagina);

        // Crear y devolver el objeto PaginacionDTO
        PaginacionDTO<HistorialPropietariosDTO> paginacion = new PaginacionDTO<>();
        paginacion.setDatos(historial);
        paginacion.setPaginaActual(paginaActual);
        paginacion.setTotalElementos(totalElementos);
        paginacion.setTotalPaginas(totalPaginas);
        paginacion.setElementosPorPagina(elementosPorPagina);

        return paginacion;
    }

    // Método para obtener todos los registros de historial de propietarios
    public List<HistorialPropietariosDTO> obtenerTodos() throws SQLException {
        return historialPropietariosDAO.findAll();
    }

    // Método para obtener un registro por ID
    public HistorialPropietariosDTO obtenerPorId(int id) throws SQLException {
        return historialPropietariosDAO.findById(id);  // Si no existe, devuelve null
    }

    // Método para insertar un historial de propietario
    public void insertarHistorial(HistorialPropietariosDTO historialPropietariosDTO) throws SQLException {
        historialPropietariosDAO.create(historialPropietariosDTO);
    }

    // Método para actualizar un historial de propietario
    public boolean actualizarHistorial(int id, HistorialPropietariosDTO historialPropietariosDTO) throws SQLException {
        return historialPropietariosDAO.update(id, historialPropietariosDTO);
    }

    // Método para eliminar un historial de propietario
    public boolean eliminarHistorial(int id) throws SQLException {
        return historialPropietariosDAO.delete(id);
    }

    // Método para buscar por atributo específico
    public List<HistorialPropietariosDTO> buscarPorAtributo(String columna, String valor) throws SQLException {
        return historialPropietariosDAO.buscarPorAtributo(columna, valor);
    }

    // Método para obtener el historial de propietario por idCoche
    public HistorialPropietariosDTO obtenerPorIdCoche(int idCoche) throws SQLException {
        return historialPropietariosDAO.findByIdCoche(idCoche); // Este método debe ser implementado en el DAO
    }

    public HistorialPropietariosDTO buscarPorIdCoche(int idCoche) throws SQLException {
        return historialPropietariosDAO.buscarPorIdCoche(idCoche);
    }

    public List<HistorialPropietariosDTO> obtenerHistorialCompleto() throws SQLException {
        return historialPropietariosDAO.obtenerHistorialCompleto();
    }

    public int obtenerNumeroDeVehiculosPorNombrePersona(String nombrePersona) throws SQLException {
        // Implementación que consulte la base de datos usando el nombre de la persona
        return historialPropietariosDAO.contarVehiculosPorNombrePersona(nombrePersona);
    }
// HistorialPropietariosServicio.java

    public int obtenerNumeroDeVehiculosPorPersona(int personaId) throws SQLException {
        return historialPropietariosDAO.contarVehiculosPorPersona(personaId);
    }

    public void asociarVehiculoAPersona(int idPersona, int idCoche) throws SQLException {
        historialPropietariosDAO.asociarVehiculoAPersona(idPersona, idCoche);
    }

}
