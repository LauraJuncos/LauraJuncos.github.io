package hibernate.Proyecto_dgc;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "informe")
public class Informe implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_informe")
    private Integer idInforme;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    @Column(name = "autor", nullable = false, length = 100)
    private String autor;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "contenido", columnDefinition = "TEXT", nullable = false)
    private String contenido;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "tipo_informe", nullable = false, length = 50)
    private String tipoInforme;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Getters y Setters
    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoInforme() {
        return tipoInforme;
    }

    public void setTipoInforme(String tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
