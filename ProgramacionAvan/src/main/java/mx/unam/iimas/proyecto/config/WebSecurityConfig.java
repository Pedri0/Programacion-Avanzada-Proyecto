package mx.unam.iimas.proyecto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mx.unam.iimas.proyecto.service.HabitanteDetailsServiceImpl;

//Clase para configurar el login con contraseña
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Necesario para evitar que la seguridad se aplique a los resources
	// Como los css, imagenes y javascripts
	String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(resources).permitAll().antMatchers("/", "/index").permitAll() //permite a cualquier persona acceder a los resources definido aqui arriba
			.antMatchers("/admin*").access("hasRole('ADMIN')") //permite unicamente al admin acceder a la pagina admin
			.antMatchers("/listahabitantes*").access("hasRole('ADMIN')") //permite unicamente al admin acceder a la pagina lista habitantes
			.antMatchers("/editHabitante*").access("hasRole('ADMIN')") //permite unicamente al admin acceder a la pagina edit habitante
			.antMatchers("/addHabitante*").access("hasRole('ADMIN')") //permite unicamente al admin acceder a la pagina addhabitante
			.antMatchers("/deleteHabitante*").access("hasRole('ADMIN')") //permite unicamente al admin eliminar un uisuario
			.antMatchers("/user*").access("hasRole('USER') or hasRole('ADMIN')") //permite admin y usuario acceder a la pagina usuario
			.anyRequest().authenticated().and().formLogin() //cualquier solicitud debe ser de un usuario autenticado
			.loginPage("/login").permitAll().defaultSuccessUrl("/menu").failureUrl("/login?error=true") //permite a cualquier persona ver el login, si el login fue exitoso redirige al menu por default
			.usernameParameter("username").passwordParameter("password").and().logout().permitAll() //define los parametros username y password para porder loguearse
			.logoutSuccessUrl("/login?logout"); //cierra sesion y muestra el cartel de has cerrado sesion
	}

	BCryptPasswordEncoder bCryptPasswordEncoder; //Para encriptar las contraseñas que se almacenan en la db y solicite a la db que las contraseñas tengan ese formato 

	// Crea el encriptador de contraseñas
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//El numero 4 representa que tan fuerte es la encriptacion.
//Se puede en un rango entre 4 y 31. 
//Si no se pone un numero el programa utilizara uno aleatoriamente cada vez
//que inicie la aplicacion, por lo cual las contrasenas encriptadas no funcionaran bien
		return bCryptPasswordEncoder;
	}

	@Autowired
	HabitanteDetailsServiceImpl habitanteDetailsService;

	// Registra el service para usuarios y el encriptador de contrasena
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(habitanteDetailsService).passwordEncoder(passwordEncoder());
	}
}
