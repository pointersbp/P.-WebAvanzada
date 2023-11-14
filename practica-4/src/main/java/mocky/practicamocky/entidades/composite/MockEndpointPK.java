package mocky.practicamocky.entidades.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocky.practicamocky.utilidades.MockEndpointsMethods;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockEndpointPK implements Serializable {
    private String proyectoId;
    private String path;
    private MockEndpointsMethods method;

    public MockEndpointPK(String proyectoId, MockEndpointsMethods method, String path) {
        this.proyectoId = proyectoId;
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockEndpointPK that = (MockEndpointPK) o;
        return Objects.equals(proyectoId, that.proyectoId) && Objects.equals(path, that.path) && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(proyectoId, path, method);
    }


}
