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
        MqttClient mqttClient = new MqttClient(
                config.url,
                MqttClient.generateClientId(),
                new MemoryPersistence()
        );

        MqttConnectOptions options = new MqttConnectOptions();
        options.setPassword(config.password.toCharArray());
        options.setUserName(config.username);
        options.setCleanSession(true);

        mqttClient.connect(options);
        mqttClient.setCallback(callback);
        mqttClient.subscribe(config.topicRead, config.qos);
    }
}
