/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Laura
 */
public class Coches {

    private int id;
    private String marca;
    private String modelo;
    private String matricula;
    private int anoMatriculacion;

    // Constructor vacío
    public Coches() {
    }

    // Constructor con matricula y año de matriculación
    public Coches(String matricula, int anoMatriculacion) {
        if (matricula == null || matricula.isEmpty()) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía.");
        }
        this.matricula = matricula;
        this.anoMatriculacion = anoMatriculacion;
    }

    // Constructor completo
    public Coches(int id, String marca, String modelo, String matricula, int anoMatriculacion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.anoMatriculacion = anoMatriculacion;
    }

    // Getters y Setters
    public int getAnoMatriculacion() {
        return anoMatriculacion;
    }

    public void setAnoMatriculacion(int anoMatriculacion) {
        this.anoMatriculacion = anoMatriculacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isEmpty()) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía.");
        }
        this.matricula = matricula;
    }
}
