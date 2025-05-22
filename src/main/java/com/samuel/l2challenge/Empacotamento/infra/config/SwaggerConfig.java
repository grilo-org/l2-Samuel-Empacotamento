package com.samuel.l2challenge.Empacotamento.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class SwaggerConfig {

  @Value("${server.port}")
  private int port;

  @Value("${springdoc.swagger-ui.path:/swagger-ui.html}")
  private String swaggerUiPath;

  private String apiBaseUrl;

  @PostConstruct
  public void init() {
    this.apiBaseUrl = String.format("http://localhost:%d", port);
    String swaggerUiFullUrl = apiBaseUrl + ensureStartsWithSlash(swaggerUiPath);
    log.info("Swagger UI disponível em: {}", swaggerUiFullUrl);
  }

  /**
   * Customiza alguns dados do Swagger. Coloquei nome e descrição do projeto, meu nome e LinkedIn.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
        .info(new Info()
            .title("Empacotamento de produtos do Seu Manoel")
            .description("API para processamento de empacotamentos de produtos.")
            .contact(new Contact()
                .name("Samuel de Oliveira Silva")
                .url("https://www.linkedin.com/in/samuel-de-oliveira-silva-6508811a5/")))
        .servers(List.of(new Server().url(apiBaseUrl).description("Local")));
  }

  private String ensureStartsWithSlash(String path) {
    return path.startsWith("/") ? path : "/" + path;
  }
}



