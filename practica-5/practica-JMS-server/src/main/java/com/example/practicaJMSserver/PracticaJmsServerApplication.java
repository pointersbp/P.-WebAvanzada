package com.example.practicaJMSserver;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import java.io.IOException;

@SpringBootApplication
public class PracticaJmsServerApplication {

	public static void main(String[] args) throws IOException, JMSException {
		SpringApplication.run(PracticaJmsServerApplication.class, args);
		System.out.println("Inicializando Servidor JMS");
		try {
			BrokerService broker = new BrokerService();
			broker.addConnector("tcp://0.0.0.0:61616");
			broker.start();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
