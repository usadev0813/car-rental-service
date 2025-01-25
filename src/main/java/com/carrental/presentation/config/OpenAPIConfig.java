package com.carrental.presentation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private final String url;

    public OpenAPIConfig(
            @Value("${carrental.openapi.url}") final String url
    ) {
        this.url = url;
    }

    @Bean
    public OpenAPI openAPI() {
        final Server devServer = new Server();
        devServer.setUrl(url);
        devServer.setDescription("개발 환경 서버 URL");

        final Info info = new Info()
                .title("Car Rental Service API")
                .version("v1.0.0")
                .description("자동차 렌탈 서비스 API");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
