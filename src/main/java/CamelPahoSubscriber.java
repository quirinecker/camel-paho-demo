import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class CamelPahoSubscriber {

    @Inject
    PahoConfiguration config;

    @Inject
    CamelPahoCallback callback;

    void onStart(@Observes StartupEvent ev) throws MqttException {
        // new MqttClient(url out of the config file, client id [is random generated], new MemoryPersistence)
        MqttClient mqttClient = new MqttClient(
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
        mqttClient.connect(options);
        // set the Callback Object
        mqttClient.setCallback(callback);
        // subscribe to the read topic configured in the application.properties
        // qos = Quality Of Service, Default = 1
        mqttClient.subscribe(config.topicRead, config.qos);
    }
}
