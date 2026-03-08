package base;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Resena {

    private ObjectId id;
    private ObjectId productoId;
    private String usuario;
    private String comentario;
    private int puntuacion;
    private LocalDate fechaResena;

    public Resena(ObjectId productoId, String usuario, String comentario, int puntuacion, LocalDate fechaResena) {
        this.productoId = productoId;
        this.usuario = usuario;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fechaResena = fechaResena;
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

    @Override
    public String toString() {
        return "Reseña { " +
                usuario + " | " +
                comentario +
                " }";
    }
}
