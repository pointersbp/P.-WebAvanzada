package com.example.UserBase.Filtros;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OriginFilter extends OncePerRequestFilter {
    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String appName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Microservicio",String.format("%s[%s]",appName,serverPort));
        filterChain.doFilter(request, response);
    }
}
