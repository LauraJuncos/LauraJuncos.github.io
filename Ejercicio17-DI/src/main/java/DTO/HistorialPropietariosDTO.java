/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author Laura
 */
public class HistorialPropietariosDTO {
    
    private int id;
    private int idPersonas;
    private int idCoches;
        private Date fechaInicio;
        private Date fechaFin;
    private String nombrePersona;

    // Constructor vacío
    public HistorialPropietariosDTO() {
    }

    // Constructor con parámetros
    public HistorialPropietariosDTO(int id, int idPersonas, int idCoches, Date fechaTransaccion) {
        this.id = id;
        this.idPersonas = idPersonas;
        this.idCoches = idCoches;
        this.fechaInicio = fechaTransaccion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPersonas() {
        return idPersonas;
    }

    public void setIdPersonas(int idPersonas) {
        this.idPersonas = idPersonas;
    }

    public int getIdCoches() {
        return idCoches;
    }

    public void setIdCoches(int idCoches) {
        this.idCoches = idCoches;
    }

    public Date getFechaTransaccion() {
        return fechaInicio;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaInicio = fechaTransaccion;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
}