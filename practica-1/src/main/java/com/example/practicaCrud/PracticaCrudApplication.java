package com.example.practicaCrud;

import com.example.practicaCrud.servicios.SeguridadServicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PracticaCrudApplication {

	public static void main(String[] args) {
		//SpringApplication.run(PracticaCrudApplication.class, args);
		ApplicationContext applicationContext = SpringApplication.run(PracticaCrudApplication.class, args);

		SeguridadServicio seguridadServicio = (SeguridadServicio) applicationContext.getBean("seguridadServicio");
		seguridadServicio.crearUsuarios();
	}

}
