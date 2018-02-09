package de.holarse.config;

import de.holarse.drupal.Drupal6PasswordEncoder;
import de.holarse.auth.HolarseUserDetailsService;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired
    HolarseUserDetailsService userDetailsService;
     
    @Bean
    public DaoAuthenticationProvider drupal6AuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(drupalEncoder());
        return authProvider;
    }
    
    @Bean
    public DaoAuthenticationProvider holaCms3AuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bcryptEncoder());
        return authProvider;
    }    

    @Bean(name = "bcryptEncoder")
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean(name = "drupalEncoder")
    public PasswordEncoder drupalEncoder() {
        return new Drupal6PasswordEncoder();
    }
    
    @Override
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(drupal6AuthenticationProvider(), holaCms3AuthenticationProvider()));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                .antMatchers(HttpMethod.GET, "/articles/*").permitAll()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .successForwardUrl("/")
                .loginPage("/login").permitAll();
                
    }

}
