package com.example.practicaJMSjms;

import com.example.practicaJMSjms.DataBase.SensorService;
import com.example.practicaJMSjms.Entidades.Info;
import com.example.practicaJMSjms.Entidades.Server;
import com.example.practicaJMSjms.Entidades.TailType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class PracticaJmsJmsApplication implements ApplicationContextAware {

	private static String nombrecola = "Principal";
	private static List<Info> datos;
	private static ApplicationContext ac;

	public static void main(String[] args) throws JMSException {
		datos = new ArrayList<>();
		SpringApplication.run(PracticaJmsJmsApplication.class, args);

		Server nuevo = new Server(nombrecola, TailType.TOPIC, (SensorService) ac.getBean("sensorServiceImpl"));
		nuevo.conectar();
	}

	@Override
	public void setApplicationContext(ApplicationContext ac) {
		PracticaJmsJmsApplication.ac = ac;
	}

	public static String getNombrecola() {
		return nombrecola;
	}

	public static void setNombrecola(String nombrecola) {
		PracticaJmsJmsApplication.nombrecola = nombrecola;
	}

	public static List<Info> getDatos() {
		return datos;
	}

	public static void setDatos(List<Info> datos) {
		PracticaJmsJmsApplication.datos = datos;
	}


}
