package clonemocky.practicaclonemocky;

import clonemocky.practicaclonemocky.servicios.SecurityService;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

@SpringBootApplication
@EnableHazelcastHttpSession
public class PracticaCloneMockyApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(PracticaCloneMockyApplication.class, args);

		SecurityService seguridadServices = (SecurityService) applicationContext.getBean("securityService");
		seguridadServices.crearUsuarioAdmin();
	}

	@Bean
	public HazelcastInstance hazelcastInstance() {//Configuración basica.
		return Hazelcast.newHazelcastInstance();
	}

}
