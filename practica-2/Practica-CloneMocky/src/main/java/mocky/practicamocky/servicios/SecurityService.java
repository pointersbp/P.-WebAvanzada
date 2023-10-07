package mocky.practicamocky.servicios;


import mocky.practicamocky.entidades.Rols;
import mocky.practicamocky.entidades.User;
import mocky.practicamocky.entidades.repositorios.RolRepository;
import mocky.practicamocky.entidades.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    /**
     * Creando el usuario por defecto y su rol.
     */
    public void crearUsuarioAdmin(){
        if (usuarioRepository.findByUsername("admin") != null) {
            return;
        }

        System.out.println("AGREGANDO AL USUARIOP Y SU ROL A LA BASE DE DATOS");

        Rols rolAdmin = new Rols("ROLE_ADMIN");

        rolRepository.save(rolAdmin);
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(bCryptPasswordEncoder.encode("admin"));
        admin.setNombre("Administrador");
        admin.setActivo(true);
        admin.setRol("ROLE_ADMIN");
        admin.setRoles(new HashSet<>(Arrays.asList(rolAdmin)));

        usuarioRepository.save(admin);
    }
    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usuarioRepository.findByUsername(username);
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Rols role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isActivo(), true, true, true, grantedAuthorities);
    }
}
