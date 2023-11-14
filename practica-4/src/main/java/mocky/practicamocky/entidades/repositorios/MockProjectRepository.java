package mocky.practicamocky.entidades.repositorios;


import mocky.practicamocky.entidades.MockProject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MockProjectRepository extends CrudRepository<MockProject, String> {
    public List<MockProject> findAllByUsuarioId(String usuarioId);

    public Boolean existsByIdAndUsuarioId(String id, String usuarioId);
}
