package de.holarse.config;

import de.holarse.web.interceptors.RequestLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "de.holarse" )
@PropertySources({
    @PropertySource("classpath:application.properties"), 
    @PropertySource("classpath:git.properties")})
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;     
    
    @Bean
    public HandlerInterceptor requestLoggingInterceptor() {
        return new RequestLoggingInterceptor();
    }
    
    @Bean
    public MultipartResolver filterMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Override
//    public void configurePathMatch(final PathMatchConfigurer configurer) {
//        configurer.setUseTrailingSlashMatch(true);
//    }

    @Bean
    public SpringResourceTemplateResolver springTemplateResolver(){
        SpringResourceTemplateResolver springTemplateResolver = new SpringResourceTemplateResolver();
        springTemplateResolver.setApplicationContext(this.applicationContext);
        springTemplateResolver.setPrefix("/WEB-INF/templates/");
        springTemplateResolver.setSuffix(".html");
        return springTemplateResolver;
    }
    
    @Bean
    public SpringTemplateEngine springTemplateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springTemplateResolver());
        springTemplateEngine.addDialect(new SpringSecurityDialect()); 
       return springTemplateEngine;
    }
    
    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(springTemplateEngine());
        return viewResolver;
    }      
        
    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript
     * etc...
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // TODO später dann vom Dateisystem über NGINX ausliefern
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/"); 
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }    

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(requestLoggingInterceptor());
    }
    
    

}
