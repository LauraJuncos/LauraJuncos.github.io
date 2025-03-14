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
    private Date fechaAsociacion;

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
        return fechaAsociacion;
    }

    public void setFechaAsociacion(Date fechaAsociacion) {
        if (fechaAsociacion == null) {
            throw new IllegalArgumentException("La fecha de asociación no puede ser nula.");
        }
        this.fechaAsociacion = fechaAsociacion;
    }
}