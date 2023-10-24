package com.example.practicaJMSjms.Entidades;

import com.example.practicaJMSjms.Controladores.MainController;
import com.example.practicaJMSjms.DataBase.SensorService;
import com.example.practicaJMSjms.DataBase.SensorServiceImpl;
import com.example.practicaJMSjms.PracticaJmsJmsApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Server {


    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Queue queue;
    Topic topic;
    MessageConsumer consumer;
    String cola;
    TailType tipoCola;

    SensorService sensorService;

    public Server(String cola, TailType tipoCola, SensorService sensorService) {
        this.cola = cola;
        this.tipoCola = tipoCola;
        this.sensorService = sensorService;
    }
    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {
        System.out.println("Tipo de Cola: " + tipoCola.toString());

        String brokerUrl=System.getenv("BROKER_CONNECTION");
        if(brokerUrl == null)
            brokerUrl="failover:tcp://localhost:61616";
        else
            brokerUrl="failover:tcp://"+brokerUrl;
        System.out.printf("Broker URL: %s\n",brokerUrl);
        //connection factory indicando
        factory = new ActiveMQConnectionFactory("admin", "admin", brokerUrl);

        //Crea un nuevo hilo cuando hacemos a conexión
        connection = factory.createConnection();

        //http://127.0.0.1:8161/admin/connections.jsp
        connection.start();

        // Creando una sesión no transaccional y automatica.
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Creamos o nos connectamos a la una cola, por defecto ActiveMQ permite
        // la creación si no existe. Si la cola es del tipo Queue es acumula los mensajes, si es
        // del tipo topic es en el momento.

        if(tipoCola == TailType.QUEUE) {
            queue = session.createQueue(cola);
            consumer = session.createConsumer(queue);
        }else {
            topic = session.createTopic(cola);
            consumer = session.createConsumer(topic);
        }

        consumer.setMessageListener(message -> {
            try {
                TextMessage messageTexto = (TextMessage) message;
                String jsonString = messageTexto.getText();
                // Volver a convertir en Info.
                ObjectMapper mapper = new ObjectMapper();
                Info informacion = mapper.readValue(jsonString, Info.class);
                // Guardar sensor en la base de datos
                sensorService.saveData(informacion);

                System.out.println("El mensaje de texto recibido: " + messageTexto.getText()+" - "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                // Insertar al inicio.
                PracticaJmsJmsApplication.getDatos().add(0, informacion);
                // Asegurarse de que no pase de 10 por cada dispositivo (cliente simulado)
                if (PracticaJmsJmsApplication.getDatos().size() > 10*informacion.getClientesActuales().intValue()) {
                    // Eliminar los datos luego del index 30. Mantener una lista con los 10 últimos elementos.
                    PracticaJmsJmsApplication.getDatos().subList(10*informacion.getClientesActuales().intValue() - 1,PracticaJmsJmsApplication.getDatos().size()).clear();
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }

    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }
}
