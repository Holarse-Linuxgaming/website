/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.config;

import de.holarse.auth.HolarseAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired
    HolarseAuthenticationProvider authenticationProvider;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
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
