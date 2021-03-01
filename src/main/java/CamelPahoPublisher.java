import io.quarkus.runtime.StartupEvent;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.Delayed;
import java.util.logging.Handler;

@Path("publish")
public class CamelPahoPublisher {

    @Inject PahoConfiguration config;

    MqttClient client;

    void onStart(@Observes StartupEvent ev) throws MqttException {
        // new MqttClient(url out of the config file, client id [is random generated], new MemoryPersistence)
        client = new MqttClient(
                config.url,
                MqttClient.generateClientId(),
                new MemoryPersistence()
        );

        // Options for the connection to the mqtt broker
        MqttConnectOptions options = new MqttConnectOptions();
        options.setPassword(config.password.toCharArray());
        options.setUserName(config.username);
        options.setCleanSession(true);


        // connecting to the mqtt broker
        client.connect(options);
    }

    // will publish the passed through String to the configured topic-write
    public void publish(String message) throws MqttException {
        client.publish(config.topicWrite, new MqttMessage(message.getBytes()));
    }

    // (POST localhost:8080/publish) will publish the body content to the mqtt broker.
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.TEXT_PLAIN})
    public void post(String message) throws MqttException {
        this.publish(message);
    }
}
