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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableWebMvc
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

    /**
     * Workaround für CVS 2023-34035 - https://spring.io/security/cve-2023-34035
     * @param introspector
     * @return
     */
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector).servletPath("/");
    }

    /**
     * REST-API Authentication
     * @param http
     * @param mvc
     * @return
     * @throws Exception 
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        // Für normale API-Abfragen ist kein CSRF notwendig
        return http
            .authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/api/**")).hasRole("API"))
            .csrf((csrf) -> csrf.disable())
            .authenticationProvider(apiAuthenticationProvider())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFormSecurityFilterChain(final HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        // Authorisierungsverfahren Drupal6 (md5) und holaCms3 (bcrypt)
        http.authenticationProvider(drupal6AuthenticationProvider()).authenticationProvider(holaCms3AuthenticationProvider());

        // Was ignoriert werden soll und keiner Authentifizierung bedarf
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((requests) -> requests.requestMatchers(
                                                                                        mvc.pattern("/assets/**"),
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
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/login"),
                                                                          mvc.pattern("/register"),
                                                                          mvc.pattern("/verify")).permitAll());

        // Bereich nur für authentifizierte Benutzer jeglicher Rollen, z.B. Profil, edit-Seiten
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/profile")).authenticated());
        
        // Normale Webseite, auch als Gast nutzbar
        http.authorizeHttpRequests((requests) -> requests.requestMatchers(mvc.pattern("/"),
                                                                          mvc.pattern("/datenschutz"),
                                                                          mvc.pattern("/impressum"),
                                                                          mvc.pattern("/imprint")).permitAll());
        
        // Alles andere prinzipiell verbieten anstatt pauschal zu erlauben

        
        // Form-Login
        http.formLogin((formlogin) -> formlogin.loginPage("/login").successHandler(successHandler()).failureHandler(failureHandler()));
        http.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"));
        
        return http.build();
    }
  
}
