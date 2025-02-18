/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.HistorialPropietariosDAO;
import DTO.HistorialPropietariosDTO;
import DTO.PaginacionDTO;
import java.sql.Date;
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

        // Validar filtros
        validarFiltros(filtroColumna, filtroValor);

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
        validarHistorial(historialPropietariosDTO);
        historialPropietariosDAO.create(historialPropietariosDTO);
    }

    // Método para actualizar un historial de propietario
    public boolean actualizarHistorial(int id, HistorialPropietariosDTO historialPropietariosDTO) throws SQLException {
        validarHistorial(historialPropietariosDTO);
        return historialPropietariosDAO.update(id, historialPropietariosDTO);
    }

    // Método para eliminar un historial de propietario
    public boolean eliminarHistorial(int id) throws SQLException {
        return historialPropietariosDAO.delete(id);
    }

    // Método para buscar por atributo específico con validación de filtro
    public List<HistorialPropietariosDTO> buscarPorAtributo(String columna, String valor) throws SQLException {
        validarFiltros(columna, valor);
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
        validarFiltroSimple(nombrePersona);
        return historialPropietariosDAO.contarVehiculosPorNombrePersona(nombrePersona);
    }

    public int obtenerNumeroDeVehiculosPorPersona(int personaId) throws SQLException {
        return historialPropietariosDAO.contarVehiculosPorPersona(personaId);
    }

    public void asociarVehiculoAPersona(int idPersona, int idCoche) throws SQLException {
    Date fechaInicio = new Date(System.currentTimeMillis()); // Fecha actual
    historialPropietariosDAO.registrarAsociacion(idCoche, idPersona, fechaInicio);
}


    // Método para validar los filtros y evitar SQL injection
    private void validarFiltros(String... filtros) throws SQLException {
        for (String filtro : filtros) {
            if (contieneCaracteresEspeciales(filtro)) {
                throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
            }
        }
    }

    // Método para validar un filtro simple
    private void validarFiltroSimple(String filtro) throws SQLException {
        if (contieneCaracteresEspeciales(filtro)) {
            throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
        }
    }

    // Método para verificar si un filtro contiene caracteres especiales
    private boolean contieneCaracteresEspeciales(String valor) {
        if (valor == null) {
            return false;
        }
        return !valor.matches("[a-zA-Z0-9\s]*");
    }

    // Método para validar los datos del historial antes de insertarlos o actualizarlos
    private void validarHistorial(HistorialPropietariosDTO historial) throws SQLException {
        if (historial == null || historial.getIdPersonas() <= 0 || historial.getIdCoches() <= 0 || historial.getFechaTransaccion() == null) {
            throw new SQLException("Los datos del historial no son válidos.");
        }
    }
    // Método para obtener el historial de un coche específico
    public HistorialPropietariosDTO obtenerHistorialPorCoche(int idCoche) {
        return historialPropietariosDAO.obtenerHistorialPorCoche(idCoche);
    }
}
