package de.holarse.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // TODO später dann vom Dateisystem über NGINX ausliefern
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }
    
}
