package base;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Producto {

    private ObjectId id;
    private String nombre;
    private String descripcion;
    private String version;
    private LocalDate fechaLanzamiento;
    private float precio;
    private String categoria;
    private String licencia;
    private String repositorio;
    private float estrellas;
    private int descargas;
    private List<ObjectId> desarrolladoresIds;

    public Producto(String nombre, String descripcion, String version, LocalDate fechaLanzamiento, float precio, String categoria, String licencia, String repositorio, float estrellas, int descargas, List<ObjectId> desarrolladoresIds) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precio = precio;
        this.categoria = categoria;
        this.licencia = licencia;
        this.repositorio = repositorio;
        this.estrellas = estrellas;
        this.descargas = descargas;
        this.desarrolladoresIds = new ArrayList<>();
    }

    public Producto(){

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getRepositorio() {
        return repositorio;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    public List<ObjectId> getDesarrolladoresIds() {
        return desarrolladoresIds;
    }

    public void setDesarrolladoresIds(List<ObjectId> desarrolladoresIds) {
        this.desarrolladoresIds = desarrolladoresIds;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", version='" + version + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", licencia='" + licencia + '\'' +
                ", repositorio='" + repositorio + '\'' +
                ", estrellas=" + estrellas +
                ", descargas=" + descargas +
                ", desarrolladoresIds=" + desarrolladoresIds +
                '}';
    }
}
