package mx.unam.iimas.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Esta clase es necesaria para correr la app, la construye por defectto Springboot ya que toma las clases contenidas dentro de la carpeta
@SpringBootApplication
public class ProgramacionAvanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramacionAvanApplication.class, args);
	}

}
