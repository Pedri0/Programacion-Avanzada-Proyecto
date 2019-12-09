package mx.unam.iimas.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.unam.iimas.proyecto.entity.Habitante;
import mx.unam.iimas.proyecto.entity.Rol;
import mx.unam.iimas.proyecto.repository.HabitanteRepository;

@Service
public class HabitanteDetailsServiceImpl implements UserDetailsService , HabitanteService {
	
	@Autowired
	HabitanteRepository habitanteRepository;
	
	private HabitanteRepository repository;
	
	public HabitanteDetailsServiceImpl () {
		
	}
	
	@Autowired
	public HabitanteDetailsServiceImpl(HabitanteRepository repository) {
		super();
		this.repository = repository;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		//Buscar el usuario con el repositorio y si no existe lanzar una exepcion
		mx.unam.iimas.proyecto.entity.Habitante appHabitante =
				habitanteRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe Habitante"));
		//Mapear nuestra lista de Rango con la de spring security
		List grantList = new ArrayList();
		for (Rol rol: appHabitante.getRol()) {
			// ROLE_USER, ROLE_ADMIN,..
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(rol.getRol());
			grantList.add(grantedAuthority);
		}
		
		//Crear El objeto UserDetails que va a ir en sesion y retornarlo.
				UserDetails habitante = (UserDetails) new User(appHabitante.getUsername(), appHabitante.getPassword(), grantList);
					return habitante;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getAllHabitantes() { //funcion que retorna una lista con todos los habitantes
		List list = new ArrayList();
		repository.findAll().forEach(e -> list.add(e));
		return list;
	}
	
	@Override
	public Habitante getHabitantePorId(Long idhabitante) { //funcion que retorna un habitante de acuerdo al id que se le pasa a la funcion
		Habitante habitante = repository.findById(idhabitante).get();
		return habitante;
	}
	
	@Override
	public boolean guardarHabitante(Habitante habitante) { //funcion que guarda un habitante de acuerdo al id que se le pasa a la funcion
		try {
			repository.save(habitante);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	@Override
	public boolean eliminarHabitanteporId(Long idhabitante) { ////funcion que elimina un habitante de acuerdo al id que se le pasa a la funcion
		try {
			repository.deleteById(idhabitante);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}

}
