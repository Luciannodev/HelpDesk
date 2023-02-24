package br.com.zezinho.helpdesk.config;

import br.com.zezinho.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Profile("test")
public class TestConfig {
    @Autowired
    private DBService dbService;
    @Bean
    public void instanciaDB(){
        this.dbService.instanciaDB();
    }


}
