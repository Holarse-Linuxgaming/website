package de.holarse.config;

import de.holarse.web.converters.StringToFilepondConverter;
import de.holarse.web.interceptors.RequestLoggingInterceptor;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class AppConfig {

    //@Autowired
    //private ApplicationContext applicationContext;

    // @Bean
    // public HandlerInterceptor requestLoggingInterceptor() {
    //     return new RequestLoggingInterceptor();
    // }

    // @Bean
    // public MultipartResolver filterMultipartResolver() {
    //     return new StandardServletMultipartResolver();
    // }

//    @Override
//    public void configurePathMatch(final PathMatchConfigurer configurer) {
//        configurer.setUseTrailingSlashMatch(true);
//    }

     @Primary
     @Bean
     public ClassLoaderTemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("classpath:templates/");        
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
     }
    
     @Bean
     public ClassLoaderTemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("classpath:templates/");        
        resolver.setSuffix(".txt");
        resolver.setTemplateMode(TemplateMode.TEXT);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);        
        return resolver;
     }
    
     @Bean(value = "emailTemplateEngine")
     public TemplateEngine emailTemplateEngine() {
         final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
         // Resolver for TEXT emails
         templateEngine.addTemplateResolver(textTemplateResolver());
         return templateEngine;
     }

     @Primary
     @Bean
     public SpringTemplateEngine springTemplateEngine() {
         final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
         // Resolver for HTML pages
         springTemplateEngine.setTemplateResolver(htmlTemplateResolver());
         springTemplateEngine.addDialect(new SpringSecurityDialect());
         return springTemplateEngine;
     }

    // @Bean
    // public ViewResolver viewResolver() {
    //     ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    //     viewResolver.setTemplateEngine(springTemplateEngine());
    //     viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
    //     return viewResolver;
    // }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript
     * etc...
     *
     * @param registry
     */


    // @Bean
    // public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    //     return new PropertySourcesPlaceholderConfigurer();
    // }

    // @Bean
    // public LocalValidatorFactoryBean validator() {
    //     return new LocalValidatorFactoryBean();
    // }

    // @Override
    // public void addInterceptors(final InterceptorRegistry registry) {
    //     registry.addInterceptor(requestLoggingInterceptor());
    // }

    // @Override
    // public void addFormatters(FormatterRegistry registry) {
    //     registry.addConverter(new StringToFilepondConverter());
    // }

    // @Bean
    // PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
    //     return pageableResolver -> pageableResolver.setOneIndexedParameters(true);
    // }

}
