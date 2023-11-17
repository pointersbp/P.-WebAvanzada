package clonemocky.practicaclonemocky.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockProject {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slb64")
    @GenericGenerator(name = "slb64", strategy = "clonemocky.practicaclonemocky.utilidades.generadores.SequenceGenerator", parameters = {
            @Parameter(name = "initial_value", value = "0")
    })
    @Id
    @Column(length = 50)
    private String id;

    @OneToMany(mappedBy = "proyecto", orphanRemoval = true)
    private List<MockEndpoint> endpoints = new ArrayList<>();

    @Column(name = "usuario_id")
    private String usuarioId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private User user;
}
