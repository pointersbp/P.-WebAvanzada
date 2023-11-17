package clonemocky.practicaclonemocky.servicios;

import clonemocky.practicaclonemocky.entidades.User;
import clonemocky.practicaclonemocky.servicios.implementaciones.AuthServiceImplementation;

public interface AuthService {
    public abstract Boolean isAuthenticated();
    public abstract User getUserAuthenticated();
    public abstract Boolean containsRole(String rol);
}

