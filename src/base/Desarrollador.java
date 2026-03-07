package base;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Desarrollador {

    private ObjectId id;
    private String nombre;
    private String email;
    private String pais;
    private LocalDate fechaRegistro;
    private int contribuciones;
    private String github;
    private float valoracionPromedio;
    private List<ObjectId> productosIds;

    public Desarrollador(String nombre, String email, String pais, LocalDate fechaRegistro, int contribuciones, String github, float valoracionPromedio, List<ObjectId> productosIds) {
        this.nombre = nombre;
        this.email = email;
        this.pais = pais;
        this.fechaRegistro = fechaRegistro;
        this.contribuciones = contribuciones;
        this.github = github;
        this.valoracionPromedio = valoracionPromedio;
        this.productosIds = new ArrayList<>();
    }

    public Desarrollador(){

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getContribuciones() {
        return contribuciones;
    }

    public void setContribuciones(int contribuciones) {
        this.contribuciones = contribuciones;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public float getValoracionPromedio() {
        return valoracionPromedio;
    }

    public void setValoracionPromedio(float valoracionPromedio) {
        this.valoracionPromedio = valoracionPromedio;
    }

    public List<ObjectId> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(List<ObjectId> productosIds) {
        this.productosIds = productosIds;
    }

    @Override
    public String toString() {
        return "Desarrollador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", pais='" + pais + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", contribuciones=" + contribuciones +
                ", github='" + github + '\'' +
                ", valoracionPromedio=" + valoracionPromedio +
                ", productosIds=" + productosIds +
                '}';
    }
}
