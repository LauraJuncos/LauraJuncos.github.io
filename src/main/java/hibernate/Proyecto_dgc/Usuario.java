package hibernate.Proyecto_dgc;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {

    public enum TipoUsuario {
        GESTOR, USUARIO, ADMINISTRACIONPUBLICA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Usuario")
    private Integer idUsuario;

    @Column(name = "DNI", nullable = false, unique = true)
    private String dni;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Contrasena", nullable = false)
    private String contrasena;

    @Column(name = "Telefono", nullable = false)
    private String telefono;

    @Column(name = "Calle", nullable = false)
    private String calle;

    @Column(name = "Ciudad", nullable = false)
    private String ciudad;

    @Column(name = "Codigo_Postal", nullable = false)
    private String codigoPostal;

    @Column(name = "Provincia", nullable = false)
    private String provincia;

    @Column(name = "Pais", nullable = false)
    private String pais;

    @Column(name = "Fecha_Alta", nullable = false)
    private Date fechaAlta;

    @Column(name = "Fecha_Ultimo_Acceso")
    private Timestamp fechaUltimoAcceso;

    @Enumerated(EnumType.STRING) // Mapeo correcto del enum
    @Column(name = "Tipo_Usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @Column(name = "Validado", nullable = false)
    private boolean validado;

    // Getters y setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Timestamp getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(Timestamp fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
}
