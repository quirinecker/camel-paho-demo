import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CamelPahoCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) { }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println(topic + ": " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) { }
}
