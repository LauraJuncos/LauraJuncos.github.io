/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Laura
 */
public class PersonasVehiculos {
    
    private String nombre;
    private String sexo;  // Puede ser un enum Sexo si prefieres
    private String matricula;
    private int anoMatriculacion;
    private String marca;
    private String modelo;

    // Constructor vacío
    public PersonasVehiculos() {
    }

    // Constructor con todos los campos
    public PersonasVehiculos(String nombre, String sexo, String matricula, int anoMatriculacion, String marca, String modelo) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.matricula = matricula;
        this.anoMatriculacion = anoMatriculacion;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Constructor solo con algunos campos
    public PersonasVehiculos(String nombre, int anoMatriculacion, String marca, String modelo) {
        this.nombre = nombre;
        this.anoMatriculacion = anoMatriculacion;
        this.marca = marca;
        this.modelo = modelo;
    }

    // Getters y Setters
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
