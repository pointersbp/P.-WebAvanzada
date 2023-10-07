package clonemocky.practicaclonemocky.entidades.repositorios;

import clonemocky.practicaclonemocky.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
