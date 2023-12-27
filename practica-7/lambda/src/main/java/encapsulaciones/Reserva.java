package encapsulaciones;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Date;

@DynamoDBTable(tableName = "Reservas")
public class Reserva {
    @DynamoDBHashKey(attributeName = "Id")
    @DynamoDBAutoGeneratedKey
    private String id;
    @DynamoDBAttribute(attributeName = "matricula")
    private String Matricula;
    @DynamoDBAttribute(attributeName = "nombre")
    private String Nombre;
    @DynamoDBAttribute(attributeName = "carrera")
    private String Carrera;
    @DynamoDBAttribute(attributeName = "laboratorio")
    private String Laboratorio;
    @DynamoDBAttribute(attributeName = "fecha")
    private String Fecha;

    public Reserva(){

    }

    public Reserva(String id, String matricula, String nombre, String carrera, String laboratorio, String fecha) {
        this.id = id;
        this.Matricula = matricula;
        this.Nombre = nombre;
        this.Carrera = carrera;
        this.Laboratorio = laboratorio;
        this.Fecha = fecha;
    }

    @Override
    public String toString(){
        return "Reserva{"+
                ",matricula=" + Matricula +
                ", nombre='" + Nombre + '\'' +
                ", carrera='" + Carrera + '\'' +
                ", laboratorio='" + Laboratorio + '\'' +
                ", fecha='" + Fecha.toString() + '\'' +
                '}';
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        this.Matricula = matricula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        this.Carrera = carrera;
    }

    public String getLaboratorio() {
        return Laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.Laboratorio = laboratorio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }
}