package com.example.practicaCrud.config;


import com.example.practicaCrud.servicios.SeguridadServicio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    //Configuación para la validación del acceso modo JDBC
    private DataSource dataSource;
    @Value("${query.user-jdbc}")
    private String queryUsuario;
    @Value("${query.rol-jdbc}")
    private String queryRol;
    //Opción JPA
    private SeguridadServicio seguridadServices;
    private PasswordEncoder passwordEncoder;

    public ConfiguracionSeguridad(DataSource dataSource, SeguridadServicio seguridadServices, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.seguridadServices = seguridadServices;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth =
                http.getSharedObject(AuthenticationManagerBuilder.class);


        System.out.println("Autentificación en JPA");
        auth.userDetailsService(seguridadServices)
                .passwordEncoder(passwordEncoder);


        return auth.build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers(mvc.pattern("/")).permitAll() //permitiendo llamadas a esas urls.
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**"), AntPathRequestMatcher.antMatcher("/js/**"), AntPathRequestMatcher.antMatcher("/webjars/**"), AntPathRequestMatcher.antMatcher("*.html")).permitAll() //permitiendo llamadas a esas urls.
                                .requestMatchers(mvc.pattern("/h2-console/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/thymeleaf/**"), AntPathRequestMatcher.antMatcher("/freemarker/**"), AntPathRequestMatcher.antMatcher("/api/**"), AntPathRequestMatcher.antMatcher("/jpa/**")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/api-docs/**"), AntPathRequestMatcher.antMatcher("/api-docs.yaml"), AntPathRequestMatcher.antMatcher("/swagger-ui.html"), AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll() //para OpenApi
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/estudiantes/")).permitAll() //hasAnyRole("ADMIN", "USER")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/crearestudiante")).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/logout")).permitAll()
                                .anyRequest().authenticated() //cualquier llamada debe ser validada
                )
                .formLogin((form) -> form
                        .loginPage("/login") //indicando la ruta que estaremos utilizando.
                        .failureUrl("/login?error") //en caso de fallar puedo indicar otra pagina.
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/logout"))
                        .permitAll());
        return http.build();
    }

}


