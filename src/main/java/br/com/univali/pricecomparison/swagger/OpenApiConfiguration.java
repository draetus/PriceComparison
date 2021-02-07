package br.com.univali.pricecomparison.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
 
    @Bean
    public GroupedOpenApi customOpenAPI() {
    	 return GroupedOpenApi.builder()
                 .setGroup("pricecomparison")
                 .pathsToMatch("/**")
                 .build();
    }
}