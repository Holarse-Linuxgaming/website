package de.holarse.config;

//import de.holarse.auth.web.SecureAccountFailureHandler;
import de.holarse.utils.NonePasswordEncoder;
import de.holarse.auth.web.SecureAccountFailureHandler;
import de.holarse.drupal.Drupal6PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebSecurity
@Configuration
public class MultipleHttpSecurityConfig {

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
    @Order(1)
    public SecurityFilterChain webFormSecurityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        // Workaround für CVS 2023-34035 - https://spring.io/security/cve-2023-34035        
        var mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        
        http.securityContext(ctx -> ctx.requireExplicitSave(false));
        
        // Authorisierungsverfahren Drupal6 (md5) und holaCms3 (bcrypt)
        http.authenticationProvider(drupal6AuthenticationProvider()).authenticationProvider(holaCms3AuthenticationProvider());

        // Was ignoriert werden soll und keiner Authentifizierung bedarf
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/assets/**"),
                                                                          mvc.pattern("/favicon.ico"),
                                                                          mvc.pattern("/sitemap.xml"),
                                                                          mvc.pattern("/age.xml"),
                                                                          mvc.pattern("/age-de.xml"),
                                                                          mvc.pattern("/miracle.xml"),
                                                                          mvc.pattern("/robots.txt"),
                                                                          mvc.pattern("/humans.txt"),
                                                                          mvc.pattern("/webapi/**")).permitAll());

        // Admin-Bereich nur für Admins
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/admin/**")).hasRole("ADMIN"));
        
        // Login- und Registrierungsbereich
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(
                                                                          mvc.pattern("/login"),
                                                                          mvc.pattern("/register"),
                                                                          mvc.pattern("/verify")).permitAll());


        // Bereich nur für authentifizierte Benutzer jeglicher Rollen, z.B. Profil, edit-Seiten, logout
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/profile"),
                                                                          mvc.pattern("/logout")
        ).authenticated());
        
        // Normale Webseite, auch als Gast nutzbar
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/"),
                                                                          mvc.pattern("/datenschutz"),
                                                                          mvc.pattern("/impressum"),
                                                                          mvc.pattern("/imprint")).permitAll());
        
        // Form-Login
        http.formLogin(form -> form
            .loginPage("/login").permitAll()
            .successHandler(successHandler())
            .failureHandler(failureHandler())
        );
        
        // Logout
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        
        return http.build();
    }
  
    /**
     * REST-API Authentication
     * @param http
     * @param introspector
     * @return
     * @throws Exception 
     */
    @Bean
    @Order(2)
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        // Workaround für CVS 2023-34035 - https://spring.io/security/cve-2023-34035        
        var mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");
        
        return http
            .securityMatcher("/api/**")
            .csrf((csrf) -> csrf.disable()) // Für normale API-Abfragen ist kein CSRF notwendig                
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(mvc.pattern("/api/**")).hasRole("API")
            )
            .authenticationProvider(apiAuthenticationProvider())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .build();
    }    
}
