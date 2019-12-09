package mx.unam.iimas.proyecto.service;

import java.util.List;

import mx.unam.iimas.proyecto.entity.Habitante;
//habitante service, aqui definimos nuevas funcionalidades que sera definidas en impl
public interface HabitanteService {

	@SuppressWarnings("rawtypes")
	public List getAllHabitantes();
	public Habitante getHabitantePorId(Long idhabitante);
	public boolean guardarHabitante(Habitante habitante);
	public boolean eliminarHabitanteporId(Long idhabitante);
}
