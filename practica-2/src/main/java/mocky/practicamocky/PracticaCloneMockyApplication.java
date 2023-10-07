package clonemocky.practicaclonemocky;

import clonemocky.practicaclonemocky.servicios.SecurityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PracticaCloneMockyApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(PracticaCloneMockyApplication.class, args);

		SecurityService seguridadServices = (SecurityService) applicationContext.getBean("securityService");
		seguridadServices.crearUsuarioAdmin();
	}
}
