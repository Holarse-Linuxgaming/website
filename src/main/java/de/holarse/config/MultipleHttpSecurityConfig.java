package de.holarse.config;

import de.holarse.drupal.Drupal6PasswordEncoder;
import de.holarse.rest.encoder.NonePasswordEncoder;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class MultipleHttpSecurityConfig {

    @Autowired
    @Qualifier("webUserDetailsService")            
    UserDetailsService webUserDetailsService;
    
    @Autowired
    @Qualifier("apiUserDetailsService")
    UserDetailsService apiUserDetailsService;    

    @Bean(name = "bcryptEncoder")
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "drupalEncoder")
    public PasswordEncoder drupalEncoder() {
        return new Drupal6PasswordEncoder();
    }
    
    @Bean(name = "noneEncoder")
    public PasswordEncoder noneEncoder() {
        return new NonePasswordEncoder();
    }    

    @Bean
    public DaoAuthenticationProvider drupal6AuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(webUserDetailsService);
        authProvider.setPasswordEncoder(drupalEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider holaCms3AuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(webUserDetailsService);
        authProvider.setPasswordEncoder(bcryptEncoder());
        return authProvider;
    }
    
    @Bean
    public DaoAuthenticationProvider apiAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(apiUserDetailsService);
        authProvider.setPasswordEncoder(noneEncoder());
        return authProvider;
    }    
    
    //
    // REST
    //
    @Configuration
    @Order(1)
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        Logger log = LoggerFactory.getLogger(ApiWebSecurityConfigurationAdapter.class);

        @Override
        public AuthenticationManager authenticationManager() {
            return new ProviderManager(Arrays.asList(apiAuthenticationProvider()));
        }        

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // API
            http.csrf().disable().antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("API").and().httpBasic();
        }

    }
    
    //
    // HTTP-FORM
    //
    @Configuration
    @Order(2)
    public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        Logger log = LoggerFactory.getLogger(FormLoginWebSecurityConfigurerAdapter.class);

        @Override
        public AuthenticationManager authenticationManager() {
            return new ProviderManager(Arrays.asList(drupal6AuthenticationProvider(), holaCms3AuthenticationProvider()));
        }

        @Override
        public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**", "/favicon.ico", "/sitemap.xml");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // WEB
            http.csrf()
                    .and().authorizeRequests()
//                    .antMatchers("/tags/**", "/category/stichworte/**").permitAll()
//                    .antMatchers("/search/**").permitAll()
                    .antMatchers("/login", "/register", "/verify").hasRole("ANONYMOUS")
//                    .antMatchers("/news/*", "/shortnews/", "/finder/", "/categories/*", "/wiki/*", "/wiki/*/branches/*", "/articles/*", "/attachments/*").permitAll()
//                    .antMatchers(HttpMethod.GET, "/users/*").permitAll()
//                    .antMatchers(HttpMethod.GET, "/users/*/*").hasRole("USER")
//                    .antMatchers(HttpMethod.GET, "/articles/new", "/wiki/new").hasRole("USER")
//                    .antMatchers(HttpMethod.GET, "/articles/*/edit", "/wiki/*/edit", "/shortnews/*/edit/").hasRole("USER")
//                    .antMatchers(HttpMethod.POST, "/articles/*", "/wiki/*", "/news/*", "/shortnews/*").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/").permitAll()
                    .antMatchers("/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/logout").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .usernameParameter("login")
                    .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                    .loginPage("/login")
                    .permitAll().and()
                    .logout().permitAll();

        }

    }
}
