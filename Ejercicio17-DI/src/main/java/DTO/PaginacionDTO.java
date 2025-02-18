/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author Laura
 */
public class PaginacionDTO<T> {
    
    private List<T> datos;
    private int paginaActual;
    private int totalElementos;
    private int totalPaginas;
    private int elementosPorPagina;

    // Getters y Setters
    public List<T> getDatos() {
        return datos;
    }

    public void setDatos(List<T> datos) {
        this.datos = datos;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(int totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getElementosPorPagina() {
        return elementosPorPagina;
    }

    public void setElementosPorPagina(int elementosPorPagina) {
        this.elementosPorPagina = elementosPorPagina;
    }
}
