package clonemocky.practicaclonemocky.entidades;

import clonemocky.practicaclonemocky.entidades.composite.MockEndpointPK;
import clonemocky.practicaclonemocky.utilidades.MockEndpointsMethods;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Entity
@IdClass(MockEndpointPK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockEndpoint {

    @Id
    @Column(name = "proyecto_id", length = 50)
    private String proyectoId;

    @Id
    @Column
    private String path;

    @Id
    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private MockEndpointsMethods method;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "proyecto_id", insertable = false, updatable = false)
    private MockProject proyecto;


    @ElementCollection
    @Column(length=32)
    private Map<String, String> headers;
    @Column
    private String contentType;
    @Column
    private int httpCode;
    @Column
    private String body;
    @Basic
    private int sendTime;
    @Basic
    private Timestamp expiration;
    @Column
    private String name;
    @Column
    private String description;

    @Column(length = 1000)
    private String jwtToken;
    @Basic
    private Boolean jwtRequired;


    public MockEndpoint(MockProject proyecto, String proyectoId, String path) {
        this.proyecto = proyecto;
        this.proyectoId = proyectoId;
        this.path = path;
    }

    public MockEndpoint(MockEndpointPK pk) {
        this.proyectoId = pk.getProyectoId();
        this.path = pk.getPath();
        this.method = pk.getMethod();
    }

    public String getRootPath() {
        if(this.jwtRequired){
            return "/mock/"+"jwt/"+proyectoId+path;
        }else{
            return "/mock/"+proyectoId+path;
        }
    }

    public Boolean getJwtRequired() {
        return jwtRequired;
    }

    public void setJwtRequired(Boolean jwtRequired) {
        this.jwtRequired = jwtRequired;
    }

    public Boolean isExpired() {
        return this.expiration.before(Timestamp.from(Instant.now()));
    }

    public String getRemainingTimeAsString() {
        Duration duration = Duration.between(Instant.now(),expiration.toInstant());
        return String.format("%d %02d:%02d:%02d", duration.toDaysPart(), duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }
    public int getSendTime() {
        return sendTime;
    }
    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
