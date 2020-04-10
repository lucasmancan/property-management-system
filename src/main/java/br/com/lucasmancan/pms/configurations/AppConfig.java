package br.com.lucasmancan.pms.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }
}
