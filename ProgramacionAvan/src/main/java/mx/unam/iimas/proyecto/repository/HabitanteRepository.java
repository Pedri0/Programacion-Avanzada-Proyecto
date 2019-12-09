package mx.unam.iimas.proyecto.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mx.unam.iimas.proyecto.entity.Habitante;
//repositorio necesario para la interfaz service
@Repository
public interface HabitanteRepository extends CrudRepository<Habitante, Long> {
	public Optional<Habitante> findByUsername(String username);

}
