package de.holarse.config;

import de.holarse.auth.HolarseApiAuthenticationProvider;
import de.holarse.auth.HolarseUserDetailsService;
import de.holarse.drupal.Drupal6PasswordEncoder;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableWebSecurity
public class MultipleHttpSecurityConfig {

    @Autowired
    HolarseUserDetailsService userDetailsService;

    @Bean(name = "bcryptEncoder")
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "drupalEncoder")
    public PasswordEncoder drupalEncoder() {
        return new Drupal6PasswordEncoder();
    }

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
    
    @Bean
    public AuthenticationProvider holarseApiAuthenticationProvider() {
        AuthenticationProvider authProvider = new HolarseApiAuthenticationProvider();
        return authProvider;
    }    

    //
    // REST
    //
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        Logger log = LoggerFactory.getLogger(ApiWebSecurityConfigurationAdapter.class);


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
    public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        Logger log = LoggerFactory.getLogger(FormLoginWebSecurityConfigurerAdapter.class);

        @Override
        public AuthenticationManager authenticationManager() {
            return new ProviderManager(Arrays.asList(holarseApiAuthenticationProvider(), drupal6AuthenticationProvider(), holaCms3AuthenticationProvider()));
        }

        @Override
        public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // WEB
            http.csrf().ignoringAntMatchers("/admin/imports/articles/")
                    .and().authorizeRequests()
                    .antMatchers("/sitemap.xml").permitAll()
                    .antMatchers("/tags/**", "/category/stichworte/**").permitAll()
                    .antMatchers("/search/**").permitAll()
                    .antMatchers("/login", "/register", "/verify").hasRole("ANONYMOUS")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/news/*", "/shortnews/", "/finder/", "/categories/*", "/wiki/*", "/articles/*", "/attachments/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/users/*/*").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/articles/new", "/wiki/new").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/articles/*/edit", "/wiki/*/edit", "/shortnews/*/edit/").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/articles/*", "/wiki/*", "/news/*", "/shortnews/*").hasRole("USER")
                    .antMatchers("/").permitAll()
                    .antMatchers("/**").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/logout").hasRole("USER")
                    .antMatchers("/admin/**").hasRole("ADMIN")
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
