package br.com.lucasmancan.pms.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig  {
    @Bean
    public Docket appDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.lucasmancan.pms.controllers"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Property Management System REST API (PMS API)")
                .description("Desafio de construção de um sistema para gerenciamento de patrimônio.")
                .version("1.0.0")
                .contact(new Contact("Lucas Frederico Mançan","https://www.linkedin.com/in/lucasmancan/", "lucasfmancan@gmail.com"))
                .build();
    }
}
