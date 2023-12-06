package com.example.UserBase.Seguridad;

import com.example.UserBase.Filtros.CustomAuthFilter;
import com.example.UserBase.Filtros.CustomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomFilter customAuthFilter = new CustomFilter(authenticationManagerBean());
        customAuthFilter.setFilterProcessesUrl("/api/auth/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/auth/login/**", "/api/auth/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/auth/usuario/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(POST, "/api/auth/usuario/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(DELETE, "/api/auth/usuario/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(PUT, "/api/auth/usuario/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(GET, "/api/auth/rol/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(POST, "/api/auth/rol/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(DELETE, "/api/auth/rol/**").hasAnyAuthority("ROL_ADMIN")
                .antMatchers(PUT, "/api/auth/rol/**").hasAnyAuthority("ROL_ADMIN");
        // Perfil de los usuarios
        http.authorizeRequests().antMatchers(GET, "/api/auth/perfil/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE", "ROL_USUARIO")
                .antMatchers(PUT, "/api/auth/perfil/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE", "ROL_USUARIO");
        // Bots
        http.authorizeRequests().antMatchers("/api/auth/bot/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_ROBOT");
        // Registro
        http.authorizeRequests().antMatchers("/api/auth/registro/usuario").permitAll();
        // Validaciones.
        http.authorizeRequests().antMatchers("/api/auth/validate/**").permitAll();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthFilter);
        http.addFilterBefore(new CustomAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
