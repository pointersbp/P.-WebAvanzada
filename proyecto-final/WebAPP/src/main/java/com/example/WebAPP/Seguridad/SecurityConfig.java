package com.example.WebAPP.Seguridad;

import com.example.WebAPP.Filtros.CustomFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll()
                // Pagina para poder comprobar que estamos dentro.
                .antMatchers("/verifyAuthentication").hasAnyAuthority("ROL_SUPER_ADMIN","ROL_ADMIN","ROL_EMPLEADO", "ROL_CLIENTE")
                .antMatchers("/dashboard").hasAnyAuthority("ROL_SUPER_ADMIN","ROL_ADMIN","ROL_EMPLEADO")
                .antMatchers("/dashboard/perfil").hasAnyAuthority("ROL_SUPER_ADMIN","ROL_ADMIN","ROL_EMPLEADO", "ROL_CLIENTE")
                .antMatchers("/dashboard/usuarios").hasAnyAuthority("ROL_SUPER_ADMIN","ROL_ADMIN")
                .antMatchers("/tienda/**").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                .antMatchers("/dashboard/ordenes").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO")
                .antMatchers("/dashboard/compras").hasAnyAuthority("ROL_SUPER_ADMIN", "ROL_ADMIN", "ROL_EMPLEADO", "ROL_CLIENTE")
                // Resources
                .antMatchers("/img/**").permitAll()
                .antMatchers("/img/**//**").permitAll()
                .antMatchers("/bootstrap-icons/**").permitAll()
                .antMatchers("/bootstrap-icons/**//**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/css/**//**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/fonts/**//**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/js/**//**").permitAll()
                .antMatchers("/vendor/**").permitAll()
                .antMatchers("/vendor/**//**").permitAll()
                // Login
                .antMatchers("/login").anonymous()
                .antMatchers("/registrar").anonymous()
                .anyRequest().authenticated().and().formLogin().loginPage("/login").and()
                .logout().logoutUrl("/logout").deleteCookies("JSESSION").logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");
        ;
        http.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
