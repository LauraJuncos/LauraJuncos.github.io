package hibernate.Proyecto_dgc;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "proyecto")
public class Proyecto implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Proyecto")
    private Integer idProyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Gestor", referencedColumnName = "Id_Usuario", nullable = false, insertable = false, updatable = false)
    private Usuario usuarioByIdGestor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Usuario", referencedColumnName = "Id_Usuario", nullable = true)
    private Usuario usuarioByIdUsuario;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "Descripcion", columnDefinition = "TEXT", nullable = true)
    private String descripcion;

    @Column(name = "Estado", length = 10, nullable = false)
    private String estado;

    @Column(name = "Fase", length = 12, nullable = true)
    private String fase;

    @Column(name = "Presupuesto", precision = 15, scale = 2, nullable = true)
    private BigDecimal presupuesto;

    @Column(name = "Fecha_Inicio", nullable = true)
    private Date fechaInicio;

    @Column(name = "Fecha_Fin", nullable = true)
    private Date fechaFin;

    @Column(name = "Ubicacion", columnDefinition = "TEXT", nullable = true)
    private String ubicacion;

    @Column(name = "Responsable", length = 100, nullable = true)
    private String responsable;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Alegacion> alegacions;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Favoritos> favoritoses;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Informe> informes;

    public Proyecto() {
    }

    // Getters y Setters

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Usuario getUsuarioByIdGestor() {
        return usuarioByIdGestor;
    }

    public void setUsuarioByIdGestor(Usuario usuarioByIdGestor) {
        this.usuarioByIdGestor = usuarioByIdGestor;
    }

    public Usuario getUsuarioByIdUsuario() {
        return usuarioByIdUsuario;
    }

    public void setUsuarioByIdUsuario(Usuario usuarioByIdUsuario) {
        this.usuarioByIdUsuario = usuarioByIdUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Set<Alegacion> getAlegacions() {
        return alegacions;
    }

    public void setAlegacions(Set<Alegacion> alegacions) {
        this.alegacions = alegacions;
    }

    public Set<Favoritos> getFavoritoses() {
        return favoritoses;
    }

    public void setFavoritoses(Set<Favoritos> favoritoses) {
        this.favoritoses = favoritoses;
    }

    public Set<Informe> getInformes() {
        return informes;
    }

    public void setInformes(Set<Informe> informes) {
        this.informes = informes;
    }
}
