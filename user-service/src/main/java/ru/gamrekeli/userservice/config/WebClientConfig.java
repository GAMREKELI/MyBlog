package ru.gamrekeli.userservice.config;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.gamrekeli.userservice.client.BlogClient;
import ru.gamrekeli.userservice.client.CommentClient;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.SecurityComponent;

@Configuration
public class WebClientConfig {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    // Клиент сервиса Blog
    @Bean
    public WebClient blogWebClient() {
        return WebClient.builder()
                .baseUrl("http://blog-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public BlogClient blogClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(blogWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(BlogClient.class);
    }

    // Клиент сервиса Comment
    @Bean
    public WebClient commentWebClint() {
        return WebClient.builder()
                .baseUrl("http://comment-service")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public CommentClient commentClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(commentWebClint()))
                .build();
        return httpServiceProxyFactory.createClient(CommentClient.class);
    }
}
