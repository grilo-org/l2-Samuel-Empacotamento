package com.samuel.l2challenge.Empacotamento.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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

  @Value("${springdoc.swagger-ui.path}")
  private String swaggerUiPath;

  private String swaggerUrl;

  @PostConstruct
  public void init() {
    this.swaggerUrl = String.format("http://localhost:%d%s", port, ensureStartsWithSlash(swaggerUiPath));
    log.info("Swagger UI disponível em: {}", swaggerUrl);
  }

  /**
   * Customiza alguns dados do Swagger. Coloquei nome e descrição do projeto, meu nome e LinkedIn.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Empacotamento de produtos do Seu Manoel")
            .description("API para processamento de empacotamentos de produtos.")
            .contact(new Contact()
                .name("Samuel de Oliveira Silva")
                .url("https://www.linkedin.com/in/samuel-de-oliveira-silva-6508811a5/")))
        .servers(List.of(new Server().url(swaggerUrl).description("Local")));
  }

  private String ensureStartsWithSlash(String path) {
    return path.startsWith("/") ? path : "/" + path;
  }
}


