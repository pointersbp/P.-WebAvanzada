package clonemocky.practicaclonemocky.seguridad;

import mocky.practicamocky.entidades.repositorios.MockEndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Transactional
public class RemoveExpiredEndpoints {
    @Autowired
    MockEndpointRepository mockEndpointRepo;

    @Scheduled(cron = "0 0 5 * * ?")
    public void deleteExpiredEndpoints() {
        mockEndpointRepo.deleteAllByExpirationLessThan(Timestamp.from(Instant.now()));
    }
}
