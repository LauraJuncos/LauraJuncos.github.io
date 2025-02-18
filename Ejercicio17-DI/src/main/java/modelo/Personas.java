/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Laura
 */
public class Personas {

    private int id;
    private String nombre;
    private String dni;
    private Sexo sexo;

    // Constructores
    public Personas() {
    }

    public Personas(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Personas(int id, String nombre, String dni, Sexo sexo) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

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
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede estar vacío.");
        }
        this.dni = dni;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    // Enum Sexo
    public enum Sexo {
        Hombre(1), Mujer(2);

        private final int valor;

        Sexo(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return valor;
        }

        // Método para convertir el valor int a un valor del enum
        public static Sexo fromInt(int i) {
            for (Sexo sexo : values()) {
                if (sexo.getValor() == i) {
                    return sexo;
                }
            }
            throw new IllegalArgumentException("Valor de sexo inválido: " + i);
        }
    }
}
