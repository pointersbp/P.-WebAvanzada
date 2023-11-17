package clonemocky.practicaclonemocky.entidades.repositorios;

import clonemocky.practicaclonemocky.entidades.MockEndpoint;
import clonemocky.practicaclonemocky.entidades.composite.MockEndpointPK;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface MockEndpointRepository  extends CrudRepository<MockEndpoint, MockEndpointPK> {
    public void deleteAllByExpirationLessThan(Timestamp now);
}
