package mocky.practicamocky.servicios;


import mocky.practicamocky.entidades.User;

public interface AuthService {
    public abstract Boolean isAuthenticated();
    public abstract User getUserAuthenticated();
    public abstract Boolean containsRole(String rol);
}

