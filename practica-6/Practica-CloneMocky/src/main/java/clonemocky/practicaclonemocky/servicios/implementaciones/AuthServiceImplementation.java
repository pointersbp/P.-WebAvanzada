package clonemocky.practicaclonemocky.servicios.implementaciones;

import clonemocky.practicaclonemocky.entidades.Rols;
import clonemocky.practicaclonemocky.entidades.User;
import clonemocky.practicaclonemocky.entidades.repositorios.UserRepository;
import clonemocky.practicaclonemocky.servicios.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImplementation implements AuthService {
    @Autowired
    UserRepository usuarioRepository;
    public Boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public User getUserAuthenticated() {
        if (isAuthenticated()) {
            User actual = usuarioRepository.findByUsername(
                    SecurityContextHolder.getContext().getAuthentication().getName());
            return actual;
        }
        return null;
    }

    public Boolean containsRole(String rol) {
        User usuario = getUserAuthenticated();
        if (usuario != null) {
            Set<String> data = usuario.getRoles().stream().map(Rols::getRole).collect(Collectors.toSet());
            if (data.contains(rol)) {
                return true;
            }
        }
        return false;
    }
}
