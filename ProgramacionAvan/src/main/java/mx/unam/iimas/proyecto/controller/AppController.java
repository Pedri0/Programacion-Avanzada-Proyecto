package mx.unam.iimas.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import mx.unam.iimas.proyecto.entity.Habitante;
import mx.unam.iimas.proyecto.service.HabitanteService;

@Controller
public class AppController {
	
	private HabitanteService habitanteService;
	
	public AppController () {
		
	}
	
	@Autowired
	public AppController(HabitanteService habitanteService) {
		this.habitanteService = habitanteService;
	}
	

	@GetMapping({"/","/login"}) //solicita acceso o mapea al login
	public String index() {
		return "index";
	}
	
	@GetMapping("/menu") //solicita acceso o mapea al menu
	public String menu() {
		return "menu";
	}
	
	@GetMapping("/user") //solicita acceso o mapea a la pagina user
	public String user() {
		return "user";
	}
	
	@GetMapping("/admin") //solicita acceso o mapea a la pagina admin
	public String admin() {
		return "admin";
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/listahabitantes") //funcion para mapear a la pagina lista habitantes y obtenga de la db todos los habitantes disponibles en ella misma
	public ModelAndView displayAllHabitante() {
		System.out.println("Pagina de Habitante solicitada : Todos los Habitantes"); //print en consola no en pagina
		ModelAndView mv = new ModelAndView();
		List habitanteList = habitanteService.getAllHabitantes();
		mv.addObject("habitanteList", habitanteList);
		mv.setViewName("listahabitantes");
		return mv;
	}
	
	@GetMapping("/addHabitante") //funcion para mapear a la pagina agregar habitante, para ello debe instanciarse un nuevo habitante
	public ModelAndView displayNewHabitanteForm() {
		ModelAndView mv = new ModelAndView("addHabitante");
		mv.addObject("headerMessage", "Add Habitante Details");
		mv.addObject("habitante", new Habitante());
		return mv;	
	}
	
	@PostMapping(value = "/addHabitante") //funcion para verificar si se guarda correctamente en la db el habitante si es correcto redirecciona al menu
	public ModelAndView saveNewHabitante(@ModelAttribute Habitante habitante, BindingResult result) {
		ModelAndView mv = new ModelAndView("redirect:/menu");
		
		if (result.hasErrors()) {
			return new ModelAndView("redirect:/error");
		}
		boolean isAdded = habitanteService.guardarHabitante(habitante);
		if (isAdded) {
			mv.addObject("message", "Se ha agregado un nuevo Habitante");
		} else {
			return new ModelAndView("redirect:/error");
		}
		
		return mv;
	}
	
	@GetMapping("/editHabitante/{idhabitante}") //funcion que mapea a la pagina para editar un habitante con un idHabitante especifico
	public ModelAndView displayEditHabitanteFrom(@PathVariable Long idhabitante) {
		ModelAndView mv = new ModelAndView("/editHabitante");
		Habitante habitante = habitanteService.getHabitantePorId(idhabitante);
		mv.addObject("header message", "Edit Habitante Details");
		mv.addObject("habitante", habitante);
		return mv;
	}
	
	@PostMapping(value = "/editHabitante/{idhabitante}") //funcion que verifica si los nuevos datos son correctos y fue posible editar la info del habitante, si es exitosa regresa al menu
	public ModelAndView saveEditedHabitante(@ModelAttribute Habitante habitante, BindingResult result) {
		ModelAndView mv = new ModelAndView("redirect:/menu");
		
		if (result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("redirect:/error");
		}
		boolean isSaved = habitanteService.guardarHabitante(habitante);
		if (!isSaved) {
			return new ModelAndView("redirect:/error");
		}
		
		return mv;
	}
	
	@GetMapping("/deleteHabitante/{idhabitante}") //funcion para eliminar un habitante de la db redirecciona al menu independientemente si fue exitoso o no
	public ModelAndView deleteHabitanteById(@PathVariable Long idhabitante) {
		boolean isDeleted = habitanteService.eliminarHabitanteporId(idhabitante);
		System.out.println("Respuesta de eliminacion de Habitante" + isDeleted); //print en consola
		ModelAndView mv = new ModelAndView("redirect:/menu");
		return mv;
	}
	//Todas las funciones requieren de funciones definidas en la interfaz habitante service
}