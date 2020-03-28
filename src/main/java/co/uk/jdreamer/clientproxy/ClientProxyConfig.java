package co.uk.jdreamer.clientproxy;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;

@Configuration
public class ClientProxyConfig {

    @Value("${users.api.v1.url}")
    private String usersEndpointUrl;

    @Bean
    public UserControllerRestEasyProxy geUserControllerRestEasyProxy() {
        // Client
        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
        // WebTarget
        ResteasyWebTarget target = client.target(usersEndpointUrl);
        // Return Proxy
        UserControllerRestEasyProxy proxy = target.proxy(UserControllerRestEasyProxy.class);
        return proxy;
    }

}
