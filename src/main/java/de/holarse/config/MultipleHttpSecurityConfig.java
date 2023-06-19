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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
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
     * REST-API Authentication
     * @param http
     * @return
     * @throws Exception 
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(final HttpSecurity http) throws Exception {
        // Für normale API-Abfragen ist kein CSRF notwendig
        http.mvcMatcher("/api/**").csrf().disable().authorizeHttpRequests().anyRequest().hasRole("API").and().httpBasic();
        http.authenticationProvider(apiAuthenticationProvider());
        
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFormSecurityFilterChain(final HttpSecurity http) throws Exception {
        http.authenticationProvider(drupal6AuthenticationProvider());
        http.authenticationProvider(holaCms3AuthenticationProvider());
       
        // Was ignoriert werden soll und keiner Authentifizierung bedarf
        http.csrf().disable().authorizeHttpRequests((requests) -> requests.mvcMatchers("/assets/**", 
                                                                      "/favicon.ico", 
                                                                      "/sitemap.xml", 
                                                                      "/age.xml", "/age-de.xml", "/miracle.xml",
                                                                      "/robots.txt", "/humans.txt",
                                                                      "/webapi/**").permitAll());
        
        // Admin-Bereich nur für Admins
        http.csrf().and().authorizeHttpRequests((requests) -> requests.mvcMatchers("/admin/**").hasRole("ADMIN"));
        
        // Login- und Registrierungsbereich
        http.authorizeHttpRequests((requests) -> requests.mvcMatchers("/login", "/register", "/verify").permitAll());

        // Bereich nur für authentifizierte Benutzer jeglicher Rollen, z.B. Profil, edit-Seiten
        http.authorizeHttpRequests((requests) -> requests.mvcMatchers("/profile").authenticated());
        
        // Normale Webseite, auch als Gast nutzbar
        http.authorizeHttpRequests((requests) -> requests.mvcMatchers("/",
                                                                      "/datenschutz",
                                                                      "/impressum", "/imprint").permitAll());
        
        // Alles andere prinzipiell verbieten anstatt pauschal zu erlauben
        http.authorizeHttpRequests().anyRequest().denyAll();
        
        // Form-Login
        http.formLogin().loginPage("/login").successHandler(successHandler()).failureHandler(failureHandler()).and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
        
        return http.build();
    }
  
}
