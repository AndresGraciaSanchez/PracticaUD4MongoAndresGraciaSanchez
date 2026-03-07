package base;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Resena {

    private ObjectId id;
    private ObjectId productoId;
    private ObjectId desarrolladorId;
    private String usuario;
    private String comentario;
    private int puntuacion;
    private LocalDate fechaResena;
    private int utilidades;

    public Resena(ObjectId productoId, ObjectId desarrolladorId, String usuario, String comentario, int puntuacion, LocalDate fechaResena, int utilidades) {
        this.productoId = productoId;
        this.desarrolladorId = desarrolladorId;
        this.usuario = usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fechaResena = fechaResena;
        this.utilidades = utilidades;
    }

    public Resena(){

    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getProductoId() {
        return productoId;
    }

    public void setProductoId(ObjectId productoId) {
        this.productoId = productoId;
    }

    public ObjectId getDesarrolladorId() {
        return desarrolladorId;
    }

    public void setDesarrolladorId(ObjectId desarrolladorId) {
        this.desarrolladorId = desarrolladorId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDate getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDate fechaResena) {
        this.fechaResena = fechaResena;
    }

    public int getUtilidades() {
        return utilidades;
    }

    public void setUtilidades(int utilidades) {
        this.utilidades = utilidades;
    }

    @Override
    public String toString() {
        return "Resena{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", desarrolladorId=" + desarrolladorId +
                ", usuario='" + usuario + '\'' +
                ", comentario='" + comentario + '\'' +
                ", puntuacion=" + puntuacion +
                ", fechaResena=" + fechaResena +
                ", utilidades=" + utilidades +
                '}';
    }
}
