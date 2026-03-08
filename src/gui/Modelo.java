package gui;

import base.Desarrollador;
import base.Producto;
import base.Resena;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Modelo {
    private MongoClient cliente;
    private MongoCollection<Document> productos;
    private MongoCollection<Document> desarrolladores;
    private MongoCollection<Document> resenas;

    public void conectar() {
        cliente = new MongoClient();
        String DATABASE = "tienda_opensource";
        MongoDatabase db = cliente.getDatabase(DATABASE);

        String COLECCION_PRODUCTOS = "productos";
        productos = db.getCollection(COLECCION_PRODUCTOS);
        String COLECCION_DESARROLLADORES = "desarrolladores";
        desarrolladores = db.getCollection(COLECCION_DESARROLLADORES);
        String COLECCION_RESENAS = "resenas";
        resenas = db.getCollection(COLECCION_RESENAS);
    }

    public void desconectar() {
        if (cliente != null) {
            cliente.close();
            cliente = null;
        }
    }

    public void guardarObjeto(Object obj) {
        if (obj instanceof Producto) {
            productos.insertOne(objectToDocument(obj));
        } else if (obj instanceof Desarrollador) {
            desarrolladores.insertOne(objectToDocument(obj));
        } else if (obj instanceof Resena) {
            resenas.insertOne(objectToDocument(obj));
        }
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        for (Document document : productos.find()) {
            lista.add(documentToProducto(document));
        }
        return lista;
    }

    public ArrayList<Desarrollador> getDesarrolladores() {
        ArrayList<Desarrollador> lista = new ArrayList<>();

        for (Document document : desarrolladores.find()) {
            lista.add(documentToDesarrollador(document));
        }
        return lista;
    }

    public ArrayList<Resena> getResenas() {
        ArrayList<Resena> lista = new ArrayList<>();

        for (Document document : resenas.find()) {
            lista.add(documentToResena(document));
        }
        return lista;
    }

    public MongoClient getCliente() {
        return cliente;
    }

    public void borrarProducto(Producto producto) {
        productos.deleteOne(new Document("_id", producto.getId()));
    }

    public void borrarDesarrollador(Desarrollador desarrollador) {
        desarrolladores.deleteOne(new Document("_id", desarrollador.getId()));
    }

    public void borrarResena(Resena resena) {
        resenas.deleteOne(new Document("_id", resena.getId()));
    }

    public void modificarProducto(Producto producto) {
        productos.replaceOne(new Document("_id", producto.getId()), objectToDocument(producto));
    }

    public void modificarDesarrollador(Desarrollador desarrollador) {
        desarrolladores.replaceOne(new Document("_id", desarrollador.getId()), objectToDocument(desarrollador));
    }

    public void modificarResena(Resena resena) {
        resenas.replaceOne(new Document("_id", resena.getId()), objectToDocument(resena));
    }

    @SuppressWarnings("unchecked")
    public Producto documentToProducto(Document dc) {
        Producto producto = new Producto();

        producto.setId(dc.getObjectId("_id"));
        producto.setNombre(dc.getString("nombre"));
        producto.setDescripcion(dc.getString("descripcion"));
        producto.setVersion(dc.getString("version"));
        producto.setFechaLanzamiento(Util.parsearFecha(dc.getString("fechaLanzamiento")));
        producto.setCategoria(dc.getString("categoria"));
        producto.setLicencia(dc.getString("licencia"));
        producto.setRepositorio(dc.getString("repositorio"));
        producto.setEstrellas(dc.getDouble("estrellas").floatValue());
        producto.setDescargas(dc.getInteger("descargas"));

        List<ObjectId> desarrolladoresIds = new ArrayList<>();
        List<Document> docs = (List<Document>) dc.get("desarrolladoresIds");
        if (docs != null) {
            for (Document doc : docs) {
                desarrolladoresIds.add(doc.getObjectId("_id"));
            }
        }
        producto.setDesarrolladoresIds(desarrolladoresIds);

        return producto;
    }

    @SuppressWarnings("unchecked")
    public Desarrollador documentToDesarrollador(Document dc) {
        Desarrollador desarrollador = new Desarrollador();

        desarrollador.setId(dc.getObjectId("_id"));
        desarrollador.setNombre(dc.getString("nombre"));
        desarrollador.setEmail(dc.getString("email"));
        desarrollador.setPais(dc.getString("pais"));
        desarrollador.setFechaRegistro(Util.parsearFecha(dc.getString("fechaRegistro")));
        desarrollador.setContribuciones(dc.getInteger("contribuciones"));

        List<ObjectId> productosIds = new ArrayList<>();
        List<Document> docs = (List<Document>) dc.get("productosIds");
        if (docs != null) {
            for (Document doc : docs) {
                productosIds.add(doc.getObjectId("_id"));
            }
        }
        desarrollador.setProductosIds(productosIds);

        return desarrollador;
    }

    public Resena documentToResena(Document dc) {
        Resena resena = new Resena();

        resena.setId(dc.getObjectId("_id"));
        resena.setProductoId(dc.getObjectId("productoId"));
        resena.setUsuario(dc.getString("usuario"));
        resena.setComentario(dc.getString("comentario"));
        resena.setPuntuacion(dc.getInteger("puntuacion"));
        resena.setFechaResena(Util.parsearFecha(dc.getString("fechaResena")));

        return resena;
    }

    public ArrayList<Resena> getResenasByProducto(ObjectId productoId) {
        ArrayList<Resena> lista = new ArrayList<>();
        for (Resena resena : getResenas()) {
            if (resena.getProductoId().equals(productoId)) {
                lista.add(resena);
            }
        }
        return lista;
    }

    public Document objectToDocument(Object obj) {
        Document dc = new Document();

        if (obj instanceof Producto) {
            Producto producto = (Producto) obj;

            dc.append("nombre", producto.getNombre());
            dc.append("descripcion", producto.getDescripcion());
            dc.append("version", producto.getVersion());
            dc.append("fechaLanzamiento", Util.formatearFecha(producto.getFechaLanzamiento()));
            dc.append("categoria", producto.getCategoria());
            dc.append("licencia", producto.getLicencia());
            dc.append("repositorio", producto.getRepositorio());
            dc.append("estrellas", producto.getEstrellas());
            dc.append("descargas", producto.getDescargas());

            List<Document> desarrolladoresDocs = new ArrayList<>();
            if (producto.getDesarrolladoresIds() != null) {
                for (ObjectId id : producto.getDesarrolladoresIds()) {
                    desarrolladoresDocs.add(new Document("_id", id));
                }
            }
            dc.append("desarrolladoresIds", desarrolladoresDocs);

        } else if (obj instanceof Desarrollador) {
            Desarrollador desarrollador = (Desarrollador) obj;

            dc.append("nombre", desarrollador.getNombre());
            dc.append("email", desarrollador.getEmail());
            dc.append("pais", desarrollador.getPais());
            dc.append("fechaRegistro", Util.formatearFecha(desarrollador.getFechaRegistro()));
            dc.append("contribuciones", desarrollador.getContribuciones());

            List<Document> productosDocs = new ArrayList<>();
            if (desarrollador.getProductosIds() != null) {
                for (ObjectId id : desarrollador.getProductosIds()) {
                    productosDocs.add(new Document("_id", id));
                }
            }
            dc.append("productosIds", productosDocs);

        } else if (obj instanceof Resena) {
            Resena resena = (Resena) obj;

            dc.append("productoId", resena.getProductoId());
            dc.append("usuario", resena.getUsuario());
            dc.append("comentario", resena.getComentario());
            dc.append("puntuacion", resena.getPuntuacion());
            dc.append("fechaResena", Util.formatearFecha(resena.getFechaResena()));
        }
        return dc;
    }
}