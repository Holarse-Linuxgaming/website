package de.holarse.config;

//import de.holarse.auth.web.SecureAccountFailureHandler;
import de.holarse.auth.web.SecureAccountFailureHandler;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

    /**
     * Erhöht den Zähler für die fehlgeschlagenen Logins und sperrt
     * ggf. auch das Konto.
     * @return 
     */
    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SecureAccountFailureHandler();
    }

    //
    // REST
    // Deprecatated: siehe https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
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
        protected void configure(final HttpSecurity http) throws Exception {
            // API
            http.csrf().disable()
                    .mvcMatcher("/api/**").authorizeRequests().anyRequest().hasRole("API").and().httpBasic();
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
            final ProviderManager man = new ProviderManager(Arrays.asList(drupal6AuthenticationProvider(), holaCms3AuthenticationProvider()));
            man.setEraseCredentialsAfterAuthentication(false); // Sonst haben wir keine Chance das unsichere Drupal-Passwort zu migrieren
            return man;
        }

        /**
         * Definiert die URLs die nicht von der Sicherheit geprüft werden
         * müssen
         * @param web
         * @throws Exception 
         */
        @Override
        public void configure(final WebSecurity web) throws Exception {
            web.ignoring()
                    .mvcMatchers(
                            "/assets/**",
                            "/favicon.ico",
                            "/sitemap.xml",
                            "/age.xml", "/age-de.xml", "/miracle.xml",
                            "/robots.txt", "/humans.txt",
                            "/webapi/**");
        }

        /**
         * Detail-Berechtigungen werden auf Methoden-Ebene definiert
         * @param http
         * @throws java.lang.Exception
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // WEB
            http.csrf()
                    .and().authorizeRequests()
                        .mvcMatchers("/login", "/register", "/verify").anonymous()
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .mvcMatchers("/**").permitAll()
//                    .and().authorizeRequests()
//                        .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .usernameParameter("login")
                        .successHandler(successHandler())
                        .failureHandler(failureHandler())
                        .loginPage("/login")
                    .and()
                        .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true");
        }

    }
}
