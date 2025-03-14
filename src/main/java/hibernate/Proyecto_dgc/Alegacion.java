package hibernate.Proyecto_dgc;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "alegacion")
public class Alegacion implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Alegacion")
    private Integer idAlegacion;

    @ManyToOne
    @JoinColumn(name = "Id_Usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Id_Proyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "Fecha_Presentacion", nullable = false)
    private Date fechaPresentacion;

    @Column(name = "Contenido", columnDefinition = "TEXT", nullable = false)
    private String contenido;

    @Column(name = "Resolucion", length = 255)
    private String resolucion;

    @Column(name = "Codigo_Validacion", length = 255, nullable = false)
    private String codigoValidacion;

    @Column(name = "Estado", length = 50, nullable = false)
    private String estado;

    // Constructor vacío
    public Alegacion() {
    }

    // Constructor con argumentos requeridos
    public Alegacion(Usuario usuario, Proyecto proyecto, Date fechaPresentacion, String contenido,
                     String codigoValidacion, String estado) {
        this.usuario = usuario;
        this.proyecto = proyecto;
        this.fechaPresentacion = fechaPresentacion;
        this.contenido = contenido;
        this.codigoValidacion = codigoValidacion;
        this.estado = estado;
    }

    // Constructor completo
    public Alegacion(Usuario usuario, Proyecto proyecto, Date fechaPresentacion, String contenido, String resolucion,
                     String codigoValidacion, String estado) {
        this.usuario = usuario;
        this.proyecto = proyecto;
        this.fechaPresentacion = fechaPresentacion;
        this.contenido = contenido;
        this.resolucion = resolucion;
        this.codigoValidacion = codigoValidacion;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdAlegacion() {
        return idAlegacion;
    }

    public void setIdAlegacion(Integer idAlegacion) {
        this.idAlegacion = idAlegacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getCodigoValidacion() {
        return codigoValidacion;
    }

    public void setCodigoValidacion(String codigoValidacion) {
        this.codigoValidacion = codigoValidacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
