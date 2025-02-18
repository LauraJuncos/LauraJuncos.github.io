/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Date;

public class HistorialPropietarios {

    private int id;
    private int idCoche;
    private int idPersona;
    private Date fechaInicio;
    private Date fechaFin;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(int idCoche) {
        if (idCoche <= 0) {
            throw new IllegalArgumentException("El ID del coche no es válido.");
        }
        this.idCoche = idCoche;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        if (idPersona <= 0) {
            throw new IllegalArgumentException("El ID de la persona no es válido.");
        }
        this.idPersona = idPersona;
    }

    public Date getFechaAsociacion() {
        return fechaInicio;
    }

    public void setFechaAsociacion(Date fechaAsociacion) {
        if (fechaAsociacion == null) {
            throw new IllegalArgumentException("La fecha de asociación no puede ser nula.");
        }
        this.fechaInicio = fechaAsociacion;
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