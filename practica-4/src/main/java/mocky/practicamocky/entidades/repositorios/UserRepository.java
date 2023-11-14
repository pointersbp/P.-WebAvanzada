package mocky.practicamocky.entidades.repositorios;


import mocky.practicamocky.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
