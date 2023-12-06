package com.example.CartBase.Controladores;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class TestController {
    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;
    @GetMapping("/")
    public String getname() {
        return appName+" ["+port+"]";
    }



    @GetMapping("/headers")
    public ResponseEntity<Map<String, String>> listAllHeaders(
            @RequestHeader Map<String, String> headers) {

        return new ResponseEntity(
                headers, HttpStatus.OK);
    }

    @GetMapping("/cookies")
    public Cookie[] cookies(HttpServletRequest request) {
        return request.getCookies();
    }
}
