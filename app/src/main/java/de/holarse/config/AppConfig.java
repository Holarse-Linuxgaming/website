package de.holarse.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class AppConfig implements WebMvcConfigurer {

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


    /**
     * Thymeleaf layout dialect
     * @return
    **/
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

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
         springTemplateEngine.addDialect(layoutDialect());
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
