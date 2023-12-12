package ru.nsu.fit.battle_fw;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Config implements WebMvcConfigurer  {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Разрешенные источники
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Разрешенные HTTP-методы
                .allowCredentials(false) // Разрешение передачи учетных данных (например, куки)
                .maxAge(3600); // Время в секундах, в течение которого клиент может использовать кэшированные результаты предыдущего запроса
    }
}
