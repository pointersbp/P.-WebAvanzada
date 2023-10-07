package mocky.practicamocky.controladores;





import mocky.practicamocky.entidades.MockEndpoint;
import mocky.practicamocky.entidades.composite.MockEndpointPK;
import mocky.practicamocky.entidades.repositorios.MockEndpointRepository;
import mocky.practicamocky.entidades.repositorios.UserRepository;
import mocky.practicamocky.utilidades.MockEndpointsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Controller()
@RequestMapping("/mock")
public class MockController {
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private MockEndpointRepository mockEndpointRepo;

    @Autowired
    UserRepository usuarioRepository;

    @RequestMapping("/{proyectoID}/{*path}")
    public ResponseEntity<Object> getMock(HttpServletRequest request, @PathVariable("proyectoID") String proyectoID, @PathVariable("path") String path) {
        Optional<MockEndpoint> optional = mockEndpointRepo.findById(new MockEndpointPK(proyectoID, MockEndpointsMethods.valueOf(request.getMethod().toUpperCase()), path));
        if (optional.isPresent()) {
            MockEndpoint endpoint = optional.get();
            if (!endpoint.getJwtRequired()) {
                if (endpoint.getExpiration().after(Timestamp.from(Instant.now()))) {
                    HttpHeaders headers = new HttpHeaders();
                    for (Map.Entry<String, String> header : endpoint.getHeaders().entrySet()) {
                        headers.add(header.getKey(), header.getValue());
                    }
                    headers.add(HttpHeaders.CONTENT_TYPE, endpoint.getContentType());
                    if (endpoint.getSendTime() > 0) {
                        try {
                            Thread.sleep(endpoint.getSendTime() * 1000);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    ResponseEntity<Object> respuesta = ResponseEntity.status(endpoint.getHttpCode()).headers(headers).body(endpoint.getBody());
                    return respuesta;
                } else {
                    mockEndpointRepo.delete(endpoint);
                }
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "NO SE ENCUENTRA EL ENDPOINT!"
        );
    }

    @RequestMapping("/jwt/{proyectoID}/{*path}")
    public ResponseEntity<Object> getMockJwt(HttpServletRequest request, @PathVariable("proyectoID") String proyectoID, @PathVariable("path") String path) {
        Optional<MockEndpoint> optional = mockEndpointRepo.findById(new MockEndpointPK(proyectoID, MockEndpointsMethods.valueOf(request.getMethod().toUpperCase()), path));
        if (optional.isPresent()) {
            MockEndpoint endpoint = optional.get();
            if (endpoint.getExpiration().after(Timestamp.from(Instant.now()))) {
                HttpHeaders headers = new HttpHeaders();
                for (Map.Entry<String, String> header : endpoint.getHeaders().entrySet()) {
                    headers.add(header.getKey(), header.getValue());
                }

                headers.add(HttpHeaders.CONTENT_TYPE, endpoint.getContentType());
                if (endpoint.getSendTime() > 0) {
                    try {
                        Thread.sleep(endpoint.getSendTime() * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
                ResponseEntity<Object> respuesta = ResponseEntity.status(endpoint.getHttpCode()).headers(headers).body(endpoint.getBody());
                return respuesta;
            } else {
                mockEndpointRepo.delete(endpoint);
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "NO SE ENCUENTRA EL ENDPOINT!"
        );
    }
}
