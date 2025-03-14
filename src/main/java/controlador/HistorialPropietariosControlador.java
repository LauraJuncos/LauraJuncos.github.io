/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.HistorialPropietariosDAO;
import DTO.HistorialPropietariosDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */
public class HistorialPropietariosControlador {

    private HistorialPropietariosDAO historialDAO;
    private int paginaActual = 1; // Página inicial
    private int elementosPorPagina = 10;
    private final Connection conexion;

    // Constructor
    public HistorialPropietariosControlador(Connection conexion) {
        this.conexion = conexion;
        this.historialDAO = new HistorialPropietariosDAO(conexion);
    }

    // Método para aplicar los filtros y obtener los datos
    public List<HistorialPropietariosDTO> aplicarFiltros(String nombre, String marca, String sexo, 
                                                          String modelo, String anio, String numVehiculos) throws SQLException {
        int offset = calcularOffset();
        return historialDAO.obtenerHistorialPaginadoConFiltros(elementosPorPagina, offset, nombre, marca, sexo, modelo, anio, numVehiculos);
    }

    // Método para obtener la siguiente página
    public List<HistorialPropietariosDTO> siguientePagina(String nombre, String marca, String sexo, 
                                                          String modelo, String anio, String numVehiculos) throws SQLException {
        paginaActual++;
        int offset = calcularOffset();
        return historialDAO.obtenerHistorialPaginadoConFiltros(elementosPorPagina, offset, nombre, marca, sexo, modelo, anio, numVehiculos);
    }

    // Método para obtener la página anterior
    public List<HistorialPropietariosDTO> anteriorPagina(String nombre, String marca, String sexo, 
                                                         String modelo, String anio, String numVehiculos) throws SQLException {
        if (paginaActual > 1) {
            paginaActual--;
        }
        int offset = calcularOffset();
        return historialDAO.obtenerHistorialPaginadoConFiltros(elementosPorPagina, offset, nombre, marca, sexo, modelo, anio, numVehiculos);
    }

    // Método para contar el total de registros
    public int contarTotalHistorial() throws SQLException {
        return historialDAO.contarTotalHistorial();
    }

    // Método para resetear la página a la primera
    public void resetearPaginacion() {
        paginaActual = 1;
    }

    // Método para obtener el número total de páginas
    public int getTotalPaginas() throws SQLException {
        int totalRegistros = contarTotalHistorial();
        return (int) Math.ceil((double) totalRegistros / elementosPorPagina);
    }

    // Método para obtener la cantidad de elementos por página
    public int getElementosPorPagina() {
        return elementosPorPagina;
    }

    // Método para establecer la cantidad de elementos por página
    public void setElementosPorPagina(int elementosPorPagina) {
        this.elementosPorPagina = elementosPorPagina;
    }

    // Método privado para calcular el offset de la paginación
    private int calcularOffset() {
        return (paginaActual - 1) * elementosPorPagina;
    }
}
