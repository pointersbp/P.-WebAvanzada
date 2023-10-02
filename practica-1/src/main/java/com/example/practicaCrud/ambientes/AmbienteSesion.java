package com.example.practicaCrud.ambientes;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class AmbienteSesion {

    private int contador = 0;

    public int getContador(){
        return ++contador;
    }

}
