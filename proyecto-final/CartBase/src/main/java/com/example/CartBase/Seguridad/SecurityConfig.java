package com.example.CartBase.Seguridad;

import com.example.CartBase.Filtros.CustomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // Cualquiera
                .antMatchers(GET, "/api/ventas/servicios/**").permitAll()
                .antMatchers(GET, "/api/ventas/servicios").permitAll()
                // Clientes
                .antMatchers(POST, "/api/ventas/order").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                .antMatchers(GET, "/api/ventas/order").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                .antMatchers(GET, "/api/ventas/order/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                // Empleados
                .antMatchers(POST, "/api/ventas/order/resumen").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                // Admin
                .antMatchers(PUT , "/api/ventas/servicios/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN")
                .antMatchers(DELETE, "/api/ventas/servicios/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN")
                .antMatchers(POST, "/api/ventas/servicios").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN");
        http.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
