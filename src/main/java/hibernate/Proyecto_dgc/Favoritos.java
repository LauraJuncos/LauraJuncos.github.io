package hibernate.Proyecto_dgc;
// Generated 23 ene 2025, 10:53:15 by Hibernate Tools 6.3.2.Final

/**
 * Favoritos generated by hbm2java
 */
public class Favoritos implements java.io.Serializable {

	private Integer idFavoritos;
	private Usuario usuario;
	private Proyecto proyecto;

	public Favoritos() {
	}

	public Favoritos(Usuario usuario, Proyecto proyecto) {
		this.usuario = usuario;
		this.proyecto = proyecto;
	}

	public Integer getIdFavoritos() {
		return this.idFavoritos;
	}

	public void setIdFavoritos(Integer idFavoritos) {
		this.idFavoritos = idFavoritos;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

}
