package mx.unam.iimas.proyecto.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mx.unam.iimas.proyecto.entity.Rol;
//repositorio necesario para la interfaz service
public interface RolRepository extends CrudRepository<Rol,Long>{

	List<Rol> findByRol(String rol);
}
