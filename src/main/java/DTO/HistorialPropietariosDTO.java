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
    private Date fechaTransaccion;
    private String nombrePersona;

    // Constructor vacío
    public HistorialPropietariosDTO() {
    }

    // Constructor con parámetros
    public HistorialPropietariosDTO(int id, int idPersonas, int idCoches, Date fechaTransaccion) {
        this.id = id;
        this.idPersonas = idPersonas;
        this.idCoches = idCoches;
        this.fechaTransaccion = fechaTransaccion;
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
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }
    
}