package de.holarse.config;

import de.holarse.interceptor.PagePopulationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.js.ajax.AjaxUrlBasedViewResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.webflow.mvc.view.FlowAjaxTiles3View;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "de.holarse")
@PropertySources({
    @PropertySource("classpath:application.properties")
    , @PropertySource("classpath:git.properties")})
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    PagePopulationInterceptor pagePopulationInterceptor;

    @Bean
    public MultipartResolver filterMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Configure TilesConfigurer.
     *
     * @return
     */
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/views/tiles/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

//    /**
//     * Configure ViewResolvers to deliver preferred views.
//     * @param registry
//     */
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//
//        registry.viewResolver(viewResolver);
//    }
    @Bean
    public ViewResolver viewResolver() {
        AjaxUrlBasedViewResolver viewResolver = new AjaxUrlBasedViewResolver();
        viewResolver.setViewClass(FlowAjaxTiles3View.class);
        viewResolver.setOrder(-2);

        return viewResolver;
    }

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript
     * etc...
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pagePopulationInterceptor);
    }

}
