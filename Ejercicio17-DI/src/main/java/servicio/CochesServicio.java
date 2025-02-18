/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import DAO.CochesDAO;
import DTO.CochesDTO;
import DTO.PaginacionDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

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

    // Método ajustado para buscar coches por atributos con validación de filtros
    public List<CochesDTO> buscarCochesPorAtributos(String marca, String modelo, String anoMatriculacion) {
        try {
            validarFiltros(marca, modelo, anoMatriculacion);
            return cochesDAO.buscarPorAtributos(marca, modelo, anoMatriculacion);
        } catch (IllegalArgumentException e) {
            mostrarMensajeError("El filtro contiene caracteres especiales no permitidos.");
        } catch (Exception e) {
            mostrarMensajeError("Ocurrió un error inesperado.");
        }
        return new ArrayList<>();
    }
    
    // Método para validar los filtros y evitar SQL injection
    private void validarFiltros(String... filtros) {
        for (String filtro : filtros) {
            if (filtro == null || filtro.isEmpty()) {
                continue; // No validar valores nulos o vacíos
            }

            if (!filtro.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\p{L}\\p{N}\\s.'-]+$") && !filtro.matches("^[0-9]{4}$")) {
                throw new IllegalArgumentException("El filtro contiene caracteres especiales no permitidos.");
            }
        }
    }

    // Método para mostrar un mensaje de error al usuario
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Obtener coches paginados con validación
    public PaginacionDTO<CochesDTO> obtenerCochesPaginados(int paginaActual, int elementosPorPagina) throws SQLException {
        validarPaginacion(paginaActual, elementosPorPagina);
        int offset = (paginaActual - 1) * elementosPorPagina;
        List<CochesDTO> coches = cochesDAO.obtenerCochesPaginados(elementosPorPagina, offset);
        int totalElementos = cochesDAO.contarTotalCoches();
        int totalPaginas = (int) Math.ceil((double) totalElementos / elementosPorPagina);

        PaginacionDTO<CochesDTO> paginacion = new PaginacionDTO<>();
        paginacion.setDatos(coches);
        paginacion.setPaginaActual(paginaActual);
        paginacion.setTotalElementos(totalElementos);
        paginacion.setTotalPaginas(totalPaginas);
        paginacion.setElementosPorPagina(elementosPorPagina);

        return paginacion;
    }

    // Método para obtener las marcas
    public List<String> obtenerMarcas() {
        return cochesDAO.obtenerMarcas();
    }

    // Método para obtener los modelos por marca con validación de filtro
    public List<String> obtenerModelosPorMarca(String marca) {
        try {
            validarFiltroSimple(marca);
            return cochesDAO.obtenerModelosPorMarca(marca);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void agregarCoche(CochesDTO coche) throws SQLException {
        cochesDAO.insertarCoche(coche);
    }

    public List<CochesDTO> obtenerTodosLosCoches() throws SQLException {
        return cochesDAO.obtenerTodosLosCoches();
    }

    // Método para validar un filtro simple
    private void validarFiltroSimple(String filtro) throws SQLException {
        if (contieneCaracteresEspeciales(filtro)) {
            throw new SQLException("El filtro contiene caracteres especiales no permitidos.");
        }
    }

    // Método para validar la paginación
    private void validarPaginacion(int paginaActual, int elementosPorPagina) throws SQLException {
        if (paginaActual < 1 || elementosPorPagina < 1) {
            throw new SQLException("Los valores de paginación no son válidos.");
        }
    }

     // Método para verificar si un filtro contiene caracteres especiales
    private boolean contieneCaracteresEspeciales(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false; // No hay caracteres especiales en un valor nulo o vacío
        }
        // Permitir letras, dígitos, espacios y algunos caracteres especiales básicos
        return !valor.matches("^[\\p{L}\\p{N}\\s.'-]+$");
    }
    
    public int contarTotalRegistros() {
    return cochesDAO.contarTotalRegistros();
}
// Método para eliminar un coche por ID
    public void eliminarCoche(int idCoche) {
        cochesDAO.eliminarCoche(idCoche);
        System.out.println("Coche eliminado exitosamente.");
    }

}
