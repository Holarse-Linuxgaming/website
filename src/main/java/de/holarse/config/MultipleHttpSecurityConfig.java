package de.holarse.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import de.holarse.auth.web.SecureAccountFailureHandler;
import de.holarse.drupal.Drupal6PasswordEncoder;
import de.holarse.utils.NonePasswordEncoder;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(securedEnabled = true)
public class MultipleHttpSecurityConfig {

    private final static transient Logger log = LoggerFactory.getLogger(MultipleHttpSecurityConfig.class);
    
    
    @Autowired
    @Qualifier("webUserDetailsService")            
    private UserDetailsService webUserDetailsService;
    
    @Autowired
    @Qualifier("apiUserDetailsService")
    private UserDetailsService apiUserDetailsService;    

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
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(webUserDetailsService);
        authProvider.setPasswordEncoder(drupalEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider holaCms3AuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(webUserDetailsService);
        authProvider.setPasswordEncoder(bcryptEncoder());
        return authProvider;
    }
    
    @Bean
    public DaoAuthenticationProvider apiAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
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

    @Bean
    @Order(2)
    public SecurityFilterChain webFormSecurityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        // Workaround für CVS 2023-34035 - https://spring.io/security/cve-2023-34035        
//        var mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        
        log.debug("webFormSecurityFilterChain");
        
        return http.cors(Customizer.withDefaults())
        
        // Authorisierungsverfahren Drupal6 (md5) und holaCms3 (bcrypt)
        .authenticationProvider(drupal6AuthenticationProvider()).authenticationProvider(holaCms3AuthenticationProvider())
       
        // Was ignoriert werden soll und keiner Authentifizierung bedarf
        .authorizeHttpRequests((requests) -> requests.requestMatchers(antMatcher("/assets/**"),
                                                                      antMatcher("/favicon.ico"),
                                                                      antMatcher("/sitemap.xml"),
                                                                      antMatcher("/age.xml"),
                                                                      antMatcher("/age-de.xml"),
                                                                      antMatcher("/miracle.xml"),
                                                                      antMatcher("/robots.txt"),
                                                                      antMatcher("/humans.txt")).permitAll())

        // Admin-Bereich nur für Admins
        .authorizeHttpRequests((requests) -> requests.requestMatchers(antMatcher("/admin/**")).hasRole("ADMIN"))
        
        // Login- und Registrierungsbereich
        .authorizeHttpRequests((requests) -> requests.requestMatchers(antMatcher("/register/**")).permitAll())


        // Bereich nur für authentifizierte Benutzer jeglicher Rollen, z.B. Profil, edit-Seiten, logout
        .authorizeHttpRequests((requests) -> requests.requestMatchers(antMatcher("/profile"),
                                                                      antMatcher("/workspace/**"),
                                                                      antMatcher("/wiki/*/edit"),
                                                                      antMatcher("/news/*/edit"),
                                                                      antMatcher("/webapi/**"),
                                                                      antMatcher("/logout")).hasRole("USER"))
        
        // Normale Webseite, auch als Gast nutzbar
        .authorizeHttpRequests((requests) -> requests.requestMatchers(antMatcher("/"),
                                                                      antMatcher("/search/**"),
                                                                      antMatcher("/tags/**"),
                                                                      antMatcher("/wiki/**"),
                                                                      antMatcher("/news/**"),
                                                                      antMatcher("/help/**"),
                                                                      antMatcher("/spielefinder/**"),
                                                                      antMatcher("/category/*/*"), // Legacy-Tag-URLs
                                                                      antMatcher("/datenschutz"),
                                                                      antMatcher("/privacy"),
                                                                      antMatcher("/impressum"),
                                                                      antMatcher("/imprint")).permitAll())

        // Form-Login
        .formLogin(form -> form.loginPage("/login").permitAll()
                               .successHandler(successHandler())
                               .failureHandler(failureHandler()))
        
        // Logout
        .logout(logout -> logout.logoutUrl("/logout"))
        
        // Fertig
        .build();
    }
  
    /**
     * REST-API Authentication
     * @param http
     * @param introspector
     * @return
     * @throws Exception 
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        // Workaround für CVS 2023-34035 - https://spring.io/security/cve-2023-34035        
        //var mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        
        return http
            .securityMatcher("/api/**")
            .csrf((csrf) -> csrf.disable()) // Für normale API-Abfragen ist kein CSRF notwendig                
            // Jede Anfrage muss grundsätzlich erstmal ROLE_API haben, Details sind dann an der Method-Security definiert
            .authorizeHttpRequests((requests) -> requests.anyRequest().hasRole("API")) 
            .authenticationProvider(apiAuthenticationProvider())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .build();
    }    
}
