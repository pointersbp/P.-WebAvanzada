package com.example.practicaJMSclient;

import com.example.practicaJMSclient.Entidades.Client;
import com.example.practicaJMSclient.Entidades.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class PracticaJmsClientApplication {

	private static List<Client> misClientes;
	private static long counter = 0;

	public static void main(String[] args)  throws JMSException, IOException {
		misClientes = new ArrayList<>();
		SpringApplication.run(PracticaJmsClientApplication.class, args);
		Runnable sendData = new Runnable() {
			public void run() {
				sendMessage();
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(sendData, 0, 3, TimeUnit.SECONDS);
	}
	private static void sendMessage() {
		int temperatura, humedad;
		int totalClientes = misClientes.size();
		if (misClientes.size()==0) {
			System.out.println("NO HAY CLIENTES REGISTRADOS");
		} else {
			System.out.println("ENVIADOS DATOS: ");
		}
		for (Client cliente:
				misClientes) {
			temperatura = ThreadLocalRandom.current().nextInt(0, 50);  // de 0 a 50 celsius
			humedad = ThreadLocalRandom.current().nextInt(0, 101); // de 0 a 100 %
			System.out.println("Cliente " + cliente.getIdCliente() + ": temperatura="
					+ temperatura + "; humedad=" + humedad + ".");
			Info informacion = new Info((int)cliente.getIdCliente(), temperatura, humedad, totalClientes);
			try {
				cliente.enviarObjeto(informacion);
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Obtener lista de clientes.
	public static List<Client> getMisClientes() {
		return misClientes;
	}
	public static long getCounter() {
		return counter;
	}
	public static void setCounter(long counter) {
		PracticaJmsClientApplication.counter = counter;
	}

}
