package mocky.practicamocky.entidades.repositorios;


import mocky.practicamocky.entidades.MockEndpoint;
import mocky.practicamocky.entidades.composite.MockEndpointPK;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface MockEndpointRepository  extends CrudRepository<MockEndpoint, MockEndpointPK> {
    public void deleteAllByExpirationLessThan(Timestamp now);
}
