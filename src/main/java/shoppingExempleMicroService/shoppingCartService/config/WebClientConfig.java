package shoppingExempleMicroService.shoppingCartService.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${microservice.url}")
    private String microserviceProductUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(microserviceProductUrl)
                .build();
    }


}
