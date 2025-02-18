/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Laura
 */
public class PersonaVehiculoDTO {
    private String nombre;
    private String sexo;
    private String matricula;
    private int anoMatriculacion;
    private String marca;
    private String modelo;

    public PersonaVehiculoDTO() {
    }

    
    // Constructor
    public PersonaVehiculoDTO(String nombre, String sexo, String matricula, int anoMatriculacion, String marca, String modelo) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.matricula = matricula;
        this.anoMatriculacion = anoMatriculacion;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnoMatriculacion() {
        return anoMatriculacion;
    }

    public void setAnoMatriculacion(int anoMatriculacion) {
        this.anoMatriculacion = anoMatriculacion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    
}
