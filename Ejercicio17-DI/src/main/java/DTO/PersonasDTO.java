/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import modelo.Personas;

/**
 *
 * @author Laura
 */
public class PersonasDTO {
    private int id;
    private String nombre;
    private String dni;
    private Personas.Sexo sexo;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Personas.Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Personas.Sexo sexo) {
        this.sexo = sexo;
    }

    // Método adicional para aceptar int y convertir a Sexo
    public void setSexo(int sexo) {
        this.sexo = Personas.Sexo.fromInt(sexo);  // Conversión de int a Sexo
    }
}
