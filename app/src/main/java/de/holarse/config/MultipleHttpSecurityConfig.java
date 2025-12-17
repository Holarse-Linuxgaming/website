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
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import de.holarse.auth.web.SecureAccountFailureHandler;
import de.holarse.drupal.Drupal6PasswordEncoder;
import de.holarse.utils.NonePasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class MultipleHttpSecurityConfig {

    private final static transient Logger log = LoggerFactory.getLogger(MultipleHttpSecurityConfig.class);
    
    @Value("${holarse.roles.hierarchy}")
    private String hierarchy;

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
    public GrantedAuthoritiesMapper authoritiesMapper(RoleHierarchy roleHierarchy) {
        return new RoleHierarchyAuthoritiesMapper(roleHierarchy);
    }

    // Needed for pre/post-authorization annotations
    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler(final RoleHierarchy roleHierarchy) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
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
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/assets/**",
                                                              "/favicon.ico",
                                                              "/sitemap.xml",
                                                              "/age.xml",
                                                              "/age-de.xml",
                                                              "/miracle.xml",
                                                              "/robots.txt",
                                                              "/humans.txt").permitAll())

        // Admin-Bereich nur für Admins
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/admin/**").hasRole("ADMIN"))
        
        // Login- und Registrierungsbereich
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/register/**").permitAll())


        // Bereich nur für authentifizierte Benutzer jeglicher Rollen, z.B. Profil, edit-Seiten, logout
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/profile",
                                                                      "/workspace/**",
                                                                      "/wiki/*/edit",
                                                                      "/news/*/edit",
                                                                      "/webapi/**",
                                                                      "/logout").hasRole("USER"))
        
        // Normale Webseite, auch als Gast nutzbar
        .authorizeHttpRequests((auth) -> auth.requestMatchers("/",
                                                              "/login**",
                                                              "/search/**",
                                                              "/tags/**",
                                                              "/wiki/**",
                                                              "/news/**",
                                                              "/help/**",
                                                              "/spielefinder/**",
                                                              "/category/*/*", // Legacy-Tag-URLs
                                                              "/categories/**",
                                                              "/holarse-services/**",
                                                              "/downloads/**",
                                                              "/datenschutz",
                                                              "/privacy",
                                                              "/impressum",
                                                              "/imprint").permitAll())

        // Form-Login
        .formLogin(form -> form
                .loginPage("/login").permitAll()
                .failureHandler(failureHandler())
                .successHandler(successHandler()))

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
