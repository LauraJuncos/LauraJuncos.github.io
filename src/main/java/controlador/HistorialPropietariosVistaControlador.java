/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DTO.HistorialPropietariosDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Laura
 */
public class HistorialPropietariosVistaControlador {

    private HistorialPropietariosControlador controlador;
    private List<HistorialPropietariosDTO> datos;
    private String nombreFiltro;
    private String marcaFiltro;
    private String sexoFiltro;
    private String modeloFiltro;
    private String anioFiltro;
    private String numVehiculosFiltro;

    // Constructor
    public HistorialPropietariosVistaControlador(HistorialPropietariosControlador controlador) {
        this.controlador = controlador;
    }

    // Método que se llama cuando se presiona el botón siguiente
    public void siguientePagina() {
        try {
            // Llamamos a siguientePagina pasándole los filtros actuales
            datos = controlador.siguientePagina(nombreFiltro, marcaFiltro, sexoFiltro, modeloFiltro, anioFiltro, numVehiculosFiltro);
            actualizarTabla(datos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método que se llama cuando se presiona el botón anterior
    public void anteriorPagina() {
        try {
            // Llamamos a anteriorPagina pasándole los filtros actuales
            datos = controlador.anteriorPagina(nombreFiltro, marcaFiltro, sexoFiltro, modeloFiltro, anioFiltro, numVehiculosFiltro);
            actualizarTabla(datos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la tabla con los datos paginados
    public void actualizarTabla(List<HistorialPropietariosDTO> datos) {
        // Lógica para actualizar la tabla con los nuevos datos
    }

    // Métodos para establecer los filtros
    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public void setMarcaFiltro(String marcaFiltro) {
        this.marcaFiltro = marcaFiltro;
    }

    public void setSexoFiltro(String sexoFiltro) {
        this.sexoFiltro = sexoFiltro;
    }

    public void setModeloFiltro(String modeloFiltro) {
        this.modeloFiltro = modeloFiltro;
    }

    public void setAnioFiltro(String anioFiltro) {
        this.anioFiltro = anioFiltro;
    }

    public void setNumVehiculosFiltro(String numVehiculosFiltro) {
        this.numVehiculosFiltro = numVehiculosFiltro;
    }
}