package mx.unam.iimas.proyecto.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Rol {

//definicion de valores en la tabla rol
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idrol")
	private Long idrol;
	
	@Column(name = "rol")
	private String rol;
	
	@ManyToMany(mappedBy = "rol") //definicion de la relacion maby to maby con la tabla habitantes
    private Set<Habitante> habitantes = new HashSet<>();
	
	public String getRol() {
		return rol;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "idrol=" + idrol + ", rol=" + rol;
	}

	public Long getIdrol() {
		return idrol;
	}

	public void setIdrol(Long idrol) {
		this.idrol = idrol;
	}
	
	
}
