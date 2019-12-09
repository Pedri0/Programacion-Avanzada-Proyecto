package mx.unam.iimas.proyecto.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import mx.unam.iimas.proyecto.entity.Rol;

//definicion de valores en la tabla habitante
@Entity
public class Habitante  {

	@Id
	@Column(name = "idhabitante")
	private Long idhabitante;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) //aqui se hace la relacion many to many con la tabla  rol
	@JoinTable(name="rolhabitante",
	joinColumns=@JoinColumn(name="habitante_idhabitante"),
	inverseJoinColumns=@JoinColumn(name="rol_idrol"))
	private Set<Rol> rol = new HashSet<Rol>(0);
	
	
	public Habitante() {
		
	}
	
	public Habitante(Long idhabitante, String nombre, String password, String username, boolean enabled, Set<Rol> rol) {
		super();
		this.idhabitante = idhabitante;
		this.nombre = nombre;
		this.password = password;
		this.username = username;
		this.enabled = enabled;
		this.rol = rol;
	}
//getters y setters
	public Long getIdhabitante() {
		return idhabitante;
	}

	public void setIdhabitante(Long idhabitante) {
		this.idhabitante = idhabitante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Rol> getRol() {
		return this.rol;
	}

	public void setRol(Set<Rol> rol) {
		this.rol = rol;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idhabitante == null) ? 0 : idhabitante.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) { //verifica que dos habitantes no tengan el mismo id
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitante other = (Habitante) obj;
		if (idhabitante == null) {
			if (other.idhabitante != null)
				return false;
		} else if (!idhabitante.equals(other.idhabitante))
			return false;
		return true;
	}

	@Override
	public String toString() { //String to string para mostrar en paginas web datos requeridos por el usuario y/o funcionalidades
		return "Habitante [idhabitante=" + idhabitante + ", nombre=" + nombre + ", password=" + password + ", username="
				+ username + ", enabled=" + enabled + ", rol=" + rol + "]";
	}

}
