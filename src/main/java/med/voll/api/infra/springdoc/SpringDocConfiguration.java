package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Voll.med API")
                        .description("API Rest para aplicación de clínica médica, contiene las funcionalidades CRUD de médicos y de pacientes, además de reserva y cancelamiento de consultas")
                        .contact(new Contact()
                                //.name("Equipo Backend") Ejemplo educativo
                                //.email("backend@voll.med")) Ejemplo educativo
                                .name("Brayan Díaz")
                                .email("brayandiaz258f@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0") //Ejemplo educativo
                                .url("http://voll.med/api/licencia"))); //Ejemplo educativo
    }



}
