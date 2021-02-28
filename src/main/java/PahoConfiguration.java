import io.quarkus.arc.config.ConfigProperties;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;

@ConfigProperties
public class PahoConfiguration {
    public String url = "localhost";
    public String topicRead = "/";
    public String topicWrite = "/";
    public String username = "";
    public String password = "";
    public int qos = 1;
}
