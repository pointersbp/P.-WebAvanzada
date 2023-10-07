package clonemocky.practicaclonemocky.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "UserLxy")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    String username;
    String password;
    String nombre;

    @OneToMany
    private List<MockProject> proyectos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private
    Set<Rols> roles;
    private boolean activo;
    private String rol;
}
