package gui;

import base.Desarrollador;
import base.Producto;
import base.Resena;
import org.bson.types.ObjectId;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements ActionListener, ListSelectionListener, KeyListener {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.vista = vista;
        this.modelo = modelo;

        conectar();
        inicializar();

        addActionListeners(this);
        addListSelectionListeners(this);
        addKeyListeners();
    }

    private void conectar() {
        if (modelo.getCliente() == null) {
            modelo.conectar();
            vista.frame.setTitle("Tienda de software y herramientas de desarrollo open-source - [CONECTADO]");
        }
    }

    private void inicializar() {
        listarProductos(modelo.getProductos());
        listarDesarrolladores(modelo.getDesarrolladores());
        listarResenas(modelo.getResenas());

        cargarDesarrolladoresEnListaProducto(null);
        cargarProductosEnListaDesarrollador(null);
        cargarProductosEnComboResena();
    }

    private void addActionListeners(ActionListener listener) {
        vista.anadirButtonProducto.addActionListener(listener);
        vista.modificarButtonProducto.addActionListener(listener);
        vista.borrarButtonProducto.addActionListener(listener);

        vista.anadirButtonDesarrollador.addActionListener(listener);
        vista.modificarButtonDesarrollador.addActionListener(listener);
        vista.borrarButtonDesarrollador.addActionListener(listener);

        vista.anadirButtonResena.addActionListener(listener);
        vista.modificarButtonResena.addActionListener(listener);
        vista.borrarButtonResena.addActionListener(listener);
    }

    private void addListSelectionListeners(ListSelectionListener listener) {
        vista.listProductos.addListSelectionListener(listener);
        vista.listDesarrolladores.addListSelectionListener(listener);
        vista.listResenas.addListSelectionListener(listener);
    }

    private void addKeyListeners() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String comando = e.getActionCommand();

        if (source == vista.anadirButtonProducto) {
            anadirProducto();
        } else if (source == vista.modificarButtonProducto) {
            modificarProducto();
        } else if (source == vista.borrarButtonProducto) {
            borrarProducto();
        }
        else if (source == vista.anadirButtonDesarrollador) {
            anadirDesarrollador();
        } else if (source == vista.modificarButtonDesarrollador) {
            modificarDesarrollador();
        } else if (source == vista.borrarButtonDesarrollador) {
            borrarDesarrollador();
        }
        else if (source == vista.anadirButtonResena) {
            anadirResena();
        } else if (source == vista.modificarButtonResena) {
            modificarResena();
        } else if (source == vista.borrarButtonResena) {
            borrarResena();
        }
    }

    private void anadirProducto() {
        Producto producto = new Producto();
        if (modificarProductoDesdeCampos(producto)) {
            modelo.guardarObjeto(producto);
            limpiarCamposProducto();
            listarProductos(modelo.getProductos());
            cargarProductosEnComboResena();
            actualizarTodasLasListas();
        }
    }

    private void modificarProducto() {
        if (vista.listProductos.getSelectedValue() != null) {
            Producto producto = (Producto) vista.listProductos.getSelectedValue();
            if (modificarProductoDesdeCampos(producto)) {
                modelo.modificarProducto(producto);
                limpiarCamposProducto();
                listarProductos(modelo.getProductos());
                cargarProductosEnComboResena();
                actualizarTodasLasListas();
                vista.anadirButtonProducto.setEnabled(true);
            }
        } else {
            Util.showWarningAlert("No has seleccionado un producto");
        }
    }

    private void borrarProducto() {
        if (vista.listProductos.getSelectedValue() != null) {
            Producto producto = (Producto) vista.listProductos.getSelectedValue();

            int confirmacion = JOptionPane.showConfirmDialog(vista.frame,
                    "¿Estás seguro de que quieres borrar este producto?\nSe borrarán también todas las reseñas asociadas.",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                ArrayList<Resena> resenasProducto = modelo.getResenasByProducto(producto.getId());
                for (Resena resena : resenasProducto) {
                    modelo.borrarResena(resena);
                }

                modelo.borrarProducto(producto);
                limpiarCamposProducto();
                listarProductos(modelo.getProductos());
                listarResenas(modelo.getResenas());
                cargarProductosEnComboResena();
                actualizarTodasLasListas();
                vista.anadirButtonProducto.setEnabled(true);

                Util.showInfoAlert("Producto y sus reseñas borrados correctamente");
            }
        } else {
            Util.showWarningAlert("No has seleccionado un producto");
        }
    }

    private void anadirDesarrollador() {
        Desarrollador desarrollador = new Desarrollador();
        if (modificarDesarrolladorDesdeCampos(desarrollador)) {
            modelo.guardarObjeto(desarrollador);
            limpiarCamposDesarrollador();
            listarDesarrolladores(modelo.getDesarrolladores());
            actualizarTodasLasListas();
        }
    }

    private void modificarDesarrollador() {
        if (vista.listDesarrolladores.getSelectedValue() != null) {
            Desarrollador desarrollador = (Desarrollador) vista.listDesarrolladores.getSelectedValue();
            if (modificarDesarrolladorDesdeCampos(desarrollador)) {
                modelo.modificarDesarrollador(desarrollador);
                limpiarCamposDesarrollador();
                listarDesarrolladores(modelo.getDesarrolladores());
                actualizarTodasLasListas();
                vista.anadirButtonDesarrollador.setEnabled(true);
            }
        } else {
            Util.showWarningAlert("No has seleccionado un desarrollador");
        }
    }

    private void borrarDesarrollador() {
        if (vista.listDesarrolladores.getSelectedValue() != null) {
            Desarrollador desarrollador = (Desarrollador) vista.listDesarrolladores.getSelectedValue();
            modelo.borrarDesarrollador(desarrollador);
            limpiarCamposDesarrollador();
            listarDesarrolladores(modelo.getDesarrolladores());
            actualizarTodasLasListas();
            vista.anadirButtonDesarrollador.setEnabled(true);
        } else {
            Util.showWarningAlert("No has seleccionado un desarrollador");
        }
    }

    private void anadirResena() {
        Resena resena = new Resena();
        if (modificarResenaDesdeCampos(resena)) {
            modelo.guardarObjeto(resena);
            limpiarCamposResena();
            listarResenas(modelo.getResenas());
            cargarProductosEnComboResena();
        }
    }

    private void modificarResena() {
        if (vista.listResenas.getSelectedValue() != null) {
            Resena resena = (Resena) vista.listResenas.getSelectedValue();
            if (modificarResenaDesdeCampos(resena)) {
                modelo.modificarResena(resena);
                limpiarCamposResena();
                listarResenas(modelo.getResenas());
                cargarProductosEnComboResena();
                vista.anadirButtonResena.setEnabled(true);
            }
        } else {
            Util.showWarningAlert("No has seleccionado una reseña");
        }
    }

    private void borrarResena() {
        if (vista.listResenas.getSelectedValue() != null) {
            Resena resena = (Resena) vista.listResenas.getSelectedValue();
            modelo.borrarResena(resena);
            limpiarCamposResena();
            listarResenas(modelo.getResenas());
            cargarProductosEnComboResena();
            vista.anadirButtonResena.setEnabled(true);
        } else {
            Util.showWarningAlert("No has seleccionado una reseña");
        }
    }

    private void actualizarTodasLasListas() {
        cargarDesarrolladoresEnListaProducto(null);
        cargarProductosEnListaDesarrollador(null);

        if (vista.listProductos.getSelectedValue() != null) {
            Producto productoSeleccionado = (Producto) vista.listProductos.getSelectedValue();
            Producto productoActualizado = buscarProductoPorId(productoSeleccionado.getId());
            if (productoActualizado != null) {
                cargarDesarrolladoresEnListaProducto(productoActualizado);
            }
        }

        if (vista.listDesarrolladores.getSelectedValue() != null) {
            Desarrollador desarrolladorSeleccionado = (Desarrollador) vista.listDesarrolladores.getSelectedValue();
            Desarrollador desarrolladorActualizado = buscarDesarrolladorPorId(desarrolladorSeleccionado.getId());
            if (desarrolladorActualizado != null) {
                cargarProductosEnListaDesarrollador(desarrolladorActualizado);
            }
        }
    }

    private boolean modificarProductoDesdeCampos(Producto producto) {
        if (vista.textFieldNombreProducto.getText().trim().isEmpty()){
            Util.showErrorAlert("El nombre es obligatorio");
            return false;
        }
        if (vista.textAreaDescripcionProducto.getText().trim().isEmpty()){
            Util.showErrorAlert("La descripción es obligatoria");
            return false;
        }
        if (vista.textFieldVersionProducto.getText().trim().isEmpty()){
            Util.showErrorAlert("La versión es obligatoria");
            return false;
        }

        if (vista.datePickerFechaLanzamientoProducto.getDate() == null) {
            Util.showErrorAlert("La fecha de lanzamiento es obligatoria");
            return false;
        }

        if (vista.comboBoxCategoriaProducto.getSelectedItem() == null) {
            Util.showErrorAlert("Debes seleccionar una categoría");
            return false;
        }

        if (vista.comboBoxLicenciaProducto.getSelectedItem() == null) {
            Util.showErrorAlert("Debes seleccionar una licencia");
            return false;
        }

        if (vista.textFieldRepositorio.getText().trim().isEmpty()) {
            Util.showErrorAlert("El repositorio es obligatorio");
            return false;
        }

        if (vista.textFieldDescargasProducto.getText().trim().isEmpty()) {
            Util.showErrorAlert("El número de descargas es obligatorio");
            return false;
        }

        try {
            producto.setNombre(vista.textFieldNombreProducto.getText().trim());
            producto.setDescripcion(vista.textAreaDescripcionProducto.getText().trim());
            producto.setVersion(vista.textFieldVersionProducto.getText().trim());
            producto.setFechaLanzamiento(vista.datePickerFechaLanzamientoProducto.getDate());
            producto.setCategoria(vista.comboBoxCategoriaProducto.getSelectedItem().toString());
            producto.setLicencia(vista.comboBoxLicenciaProducto.getSelectedItem().toString());
            producto.setRepositorio(vista.textFieldRepositorio.getText().trim());

            double estrellas = (double) vista.spinnerEstrellas.getValue();
            producto.setEstrellas((float) estrellas);

            int descargas = Integer.parseInt(vista.textFieldDescargasProducto.getText().trim());
            if (descargas < 0) {
                Util.showErrorAlert("El número de descargas no puede ser negativo");
                return false;
            }
            producto.setDescargas(descargas);

            actualizarDesarrolladoresIdsProducto(producto);
            return true;

        } catch (NumberFormatException ex) {
            Util.showErrorAlert("El número de descargas debe ser un valor numérico válido");
            return false;
        }
    }

    private boolean modificarDesarrolladorDesdeCampos(Desarrollador desarrollador) {
        if (vista.textFieldNombreDesarrollador.getText().trim().isEmpty()) {
            Util.showErrorAlert("El nombre es obligatorio");
            return false;
        }

        if (vista.textFieldEmailDesarrollador.getText().trim().isEmpty()) {
            Util.showErrorAlert("El email es obligatorio");
            return false;
        }

        if (vista.textFieldPaisDesarrollador.getText().trim().isEmpty()) {
            Util.showErrorAlert("El país es obligatorio");
            return false;
        }

        if (vista.datePickerFechaRegistroDesarrollador.getDate() == null) {
            Util.showErrorAlert("La fecha de registro es obligatoria");
            return false;
        }

        if (vista.textFieldContribucionesDesarrollador.getText().trim().isEmpty()) {
            Util.showErrorAlert("El número de contribuciones es obligatorio");
            return false;
        }

        try {
            desarrollador.setNombre(vista.textFieldNombreDesarrollador.getText().trim());
            desarrollador.setEmail(vista.textFieldEmailDesarrollador.getText().trim());
            desarrollador.setPais(vista.textFieldPaisDesarrollador.getText().trim());
            desarrollador.setFechaRegistro(vista.datePickerFechaRegistroDesarrollador.getDate());

            int contribuciones = Integer.parseInt(vista.textFieldContribucionesDesarrollador.getText().trim());
            if (contribuciones < 0) {
                Util.showErrorAlert("El número de contribuciones no puede ser negativo");
                return false;
            }
            desarrollador.setContribuciones(contribuciones);

            actualizarProductosIdsDesarrollador(desarrollador);
            return true;

        } catch (NumberFormatException ex) {
            Util.showErrorAlert("El número de contribuciones debe ser un valor numérico válido");
            return false;
        }
    }

    private boolean modificarResenaDesdeCampos(Resena resena) {
        if (vista.textFieldUsuarioResena.getText().trim().isEmpty()) {
            Util.showErrorAlert("El usuario es obligatorio");
            return false;
        }

        if (vista.textFieldComentarioResena.getText().trim().isEmpty()) {
            Util.showErrorAlert("El comentario es obligatorio");
            return false;
        }

        if (vista.datePickerFechaPublicacionResena.getDate() == null) {
            Util.showErrorAlert("La fecha de publicación es obligatoria");
            return false;
        }

        if (vista.comboBoxProductoResena.getSelectedItem() == null) {
            Util.showErrorAlert("Debes seleccionar un producto para la reseña");
            return false;
        }

        try {
            resena.setUsuario(vista.textFieldUsuarioResena.getText().trim());
            resena.setComentario(vista.textFieldComentarioResena.getText().trim());
            resena.setPuntuacion(vista.sliderPuntuacionResena.getValue());
            resena.setFechaResena(vista.datePickerFechaPublicacionResena.getDate());

            Producto productoSeleccionado = (Producto) vista.comboBoxProductoResena.getSelectedItem();
            resena.setProductoId(productoSeleccionado.getId());

            return true;

        } catch (Exception ex) {
            Util.showErrorAlert("Error al crear la reseña");
            return false;
        }
    }

    private void limpiarCamposProducto() {
        vista.textFieldNombreProducto.setText("");
        vista.textAreaDescripcionProducto.setText("");
        vista.textFieldVersionProducto.setText("");
        vista.datePickerFechaLanzamientoProducto.clear();
        vista.comboBoxCategoriaProducto.setSelectedIndex(-1);
        vista.comboBoxLicenciaProducto.setSelectedIndex(-1);
        vista.textFieldRepositorio.setText("");
        vista.spinnerEstrellas.setValue(0.0);
        vista.textFieldDescargasProducto.setText("");

        vista.listDesarrolladoresProducto.clearSelection();

        cargarDesarrolladoresEnListaProducto(null);
    }

    private void limpiarCamposDesarrollador() {
        vista.textFieldNombreDesarrollador.setText("");
        vista.textFieldEmailDesarrollador.setText("");
        vista.textFieldPaisDesarrollador.setText("");
        vista.datePickerFechaRegistroDesarrollador.clear();
        vista.textFieldContribucionesDesarrollador.setText("");

        vista.listProductosDesarrolladores.clearSelection();

        cargarProductosEnListaDesarrollador(null);
    }

    private void limpiarCamposResena() {
        vista.textFieldUsuarioResena.setText("");
        vista.textFieldComentarioResena.setText("");
        vista.sliderPuntuacionResena.setValue(0);
        vista.datePickerFechaPublicacionResena.clear();
        vista.comboBoxProductoResena.setSelectedIndex(-1);
    }

    private void listarProductos(ArrayList<Producto> lista) {
        vista.dlmProducto.clear();
        for (Producto producto : lista) {
            vista.dlmProducto.addElement(producto);
        }
    }

    private void listarDesarrolladores(ArrayList<Desarrollador> lista) {
        vista.dlmDesarrollador.clear();
        for (Desarrollador desarrollador : lista) {
            vista.dlmDesarrollador.addElement(desarrollador);
        }
    }

    private void listarResenas(ArrayList<Resena> lista) {
        vista.dlmResena.clear();
        for (Resena resena : lista) {
            vista.dlmResena.addElement(resena);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (e.getSource() == vista.listProductos) {
                if (vista.listProductos.getSelectedValue() != null) {
                    vista.anadirButtonProducto.setEnabled(false);
                    Producto producto = (Producto) vista.listProductos.getSelectedValue();
                    cargarProductoEnCampos(producto);
                }
            } else if (e.getSource() == vista.listDesarrolladores) {
                if (vista.listDesarrolladores.getSelectedValue() != null) {
                    vista.anadirButtonDesarrollador.setEnabled(false);
                    Desarrollador desarrollador = (Desarrollador) vista.listDesarrolladores.getSelectedValue();
                    cargarDesarrolladorEnCampos(desarrollador);
                }
            } else if (e.getSource() == vista.listResenas) {
                if (vista.listResenas.getSelectedValue() != null) {
                    vista.anadirButtonResena.setEnabled(false);
                    Resena resena = (Resena) vista.listResenas.getSelectedValue();
                    cargarResenaEnCampos(resena);
                }
            }
        }
    }

    private void cargarProductoEnCampos(Producto producto) {
        vista.textFieldNombreProducto.setText(producto.getNombre());
        vista.textAreaDescripcionProducto.setText(producto.getDescripcion());
        vista.textFieldVersionProducto.setText(producto.getVersion());
        if (producto.getFechaLanzamiento() != null) {
            vista.datePickerFechaLanzamientoProducto.setDate(producto.getFechaLanzamiento());
        }
        vista.comboBoxCategoriaProducto.setSelectedItem(producto.getCategoria());
        vista.comboBoxLicenciaProducto.setSelectedItem(producto.getLicencia());
        vista.textFieldRepositorio.setText(producto.getRepositorio());
        vista.spinnerEstrellas.setValue((double) producto.getEstrellas());
        vista.textFieldDescargasProducto.setText(String.valueOf(producto.getDescargas()));

        cargarDesarrolladoresEnListaProducto(producto);
    }

    private void cargarDesarrolladorEnCampos(Desarrollador desarrollador) {
        vista.textFieldNombreDesarrollador.setText(desarrollador.getNombre());
        vista.textFieldEmailDesarrollador.setText(desarrollador.getEmail());
        vista.textFieldPaisDesarrollador.setText(desarrollador.getPais());
        if (desarrollador.getFechaRegistro() != null) {
            vista.datePickerFechaRegistroDesarrollador.setDate(desarrollador.getFechaRegistro());
        }
        vista.textFieldContribucionesDesarrollador.setText(String.valueOf(desarrollador.getContribuciones()));

        cargarProductosEnListaDesarrollador(desarrollador);
    }

    private void cargarResenaEnCampos(Resena resena) {
        vista.textFieldUsuarioResena.setText(resena.getUsuario());
        vista.textFieldComentarioResena.setText(resena.getComentario());
        vista.sliderPuntuacionResena.setValue(resena.getPuntuacion());
        if (resena.getFechaResena() != null) {
            vista.datePickerFechaPublicacionResena.setDate(resena.getFechaResena());
        }

        for (int i = 0; i < vista.comboBoxProductoResena.getItemCount(); i++) {
            Producto producto = (Producto) vista.comboBoxProductoResena.getItemAt(i);
            if (producto != null && producto.getId().equals(resena.getProductoId())) {
                vista.comboBoxProductoResena.setSelectedIndex(i);
                break;
            }
        }
    }

    private void cargarProductosEnListaDesarrollador(Desarrollador desarrollador) {

        vista.dlmProductosDesarrolladores.clear();

        ArrayList<Producto> todosProductos = modelo.getProductos();

        java.util.Set<ObjectId> productosSeleccionadosIds = new java.util.HashSet<>();
        if (desarrollador != null && desarrollador.getProductosIds() != null) {
            productosSeleccionadosIds.addAll(desarrollador.getProductosIds());
        }

        for (Producto producto : todosProductos) {
            vista.dlmProductosDesarrolladores.addElement(producto);
        }

        if (!productosSeleccionadosIds.isEmpty()) {
            int[] indicesToSelect = new int[productosSeleccionadosIds.size()];
            int index = 0;

            for (int i = 0; i < vista.dlmProductosDesarrolladores.getSize(); i++) {
                Producto producto = vista.dlmProductosDesarrolladores.getElementAt(i);
                if (productosSeleccionadosIds.contains(producto.getId())) {
                    indicesToSelect[index++] = i;
                }
            }

            if (index > 0) {
                int[] finalIndices = new int[index];
                System.arraycopy(indicesToSelect, 0, finalIndices, 0, index);
                vista.listProductosDesarrolladores.setSelectedIndices(finalIndices);
            }
        }
    }

    private void actualizarProductosIdsDesarrollador(Desarrollador desarrollador) {
        List<ObjectId> productosIds = new ArrayList<>();

        int[] indicesSeleccionados = vista.listProductosDesarrolladores.getSelectedIndices();

        for (int indice : indicesSeleccionados) {
            Producto producto = vista.dlmProductosDesarrolladores.getElementAt(indice);
            productosIds.add(producto.getId());
        }

        List<ObjectId> idsAnteriores = desarrollador.getProductosIds() != null ?
                new ArrayList<>(desarrollador.getProductosIds()) :
                new ArrayList<>();

        desarrollador.setProductosIds(productosIds);

        actualizarRelacionInversa(desarrollador, productosIds, idsAnteriores);
    }

    private void actualizarRelacionInversa(Desarrollador desarrollador,
                                           List<ObjectId> nuevosIds,
                                           List<ObjectId> idsAnteriores) {
        ObjectId desarrolladorId = desarrollador.getId();

        for (ObjectId productoId : idsAnteriores) {
            if (!nuevosIds.contains(productoId)) {
                Producto producto = buscarProductoPorId(productoId);
                if (producto != null && producto.getDesarrolladoresIds() != null) {
                    producto.getDesarrolladoresIds().remove(desarrolladorId);
                    modelo.modificarProducto(producto);
                }
            }
        }

        for (ObjectId productoId : nuevosIds) {
            if (!idsAnteriores.contains(productoId)) {
                Producto producto = buscarProductoPorId(productoId);
                if (producto != null) {
                    if (producto.getDesarrolladoresIds() == null) {
                        producto.setDesarrolladoresIds(new ArrayList<>());
                    }
                    if (!producto.getDesarrolladoresIds().contains(desarrolladorId)) {
                        producto.getDesarrolladoresIds().add(desarrolladorId);
                        modelo.modificarProducto(producto);
                    }
                }
            }
        }
    }

    private Producto buscarProductoPorId(ObjectId productoId) {
        for (int i = 0; i < vista.dlmProducto.getSize(); i++) {
            Producto producto = vista.dlmProducto.getElementAt(i);
            if (producto.getId().equals(productoId)) {
                return producto;
            }
        }
        return null;
    }

    private void cargarDesarrolladoresEnListaProducto(Producto producto) {

        vista.dlmDesarrolladoresProducto.clear();

        ArrayList<Desarrollador> todosDesarrolladores = modelo.getDesarrolladores();

        java.util.Set<ObjectId> desarrolladoresSeleccionadosIds = new java.util.HashSet<>();
        if (producto != null && producto.getDesarrolladoresIds() != null) {
            desarrolladoresSeleccionadosIds.addAll(producto.getDesarrolladoresIds());
        }

        for (Desarrollador desarrollador : todosDesarrolladores) {
            vista.dlmDesarrolladoresProducto.addElement(desarrollador);
        }

        if (!desarrolladoresSeleccionadosIds.isEmpty()) {
            int[] indicesToSelect = new int[desarrolladoresSeleccionadosIds.size()];
            int index = 0;

            for (int i = 0; i < vista.dlmDesarrolladoresProducto.getSize(); i++) {
                Desarrollador desarrollador = vista.dlmDesarrolladoresProducto.getElementAt(i);
                if (desarrolladoresSeleccionadosIds.contains(desarrollador.getId())) {
                    indicesToSelect[index++] = i;
                }
            }

            if (index > 0) {
                int[] finalIndices = new int[index];
                System.arraycopy(indicesToSelect, 0, finalIndices, 0, index);
                vista.listDesarrolladoresProducto.setSelectedIndices(finalIndices);
            }
        }
    }

    private void actualizarDesarrolladoresIdsProducto(Producto producto) {
        List<ObjectId> desarrolladoresIds = new ArrayList<>();

        int[] indicesSeleccionados = vista.listDesarrolladoresProducto.getSelectedIndices();

        for (int indice : indicesSeleccionados) {
            Desarrollador desarrollador = vista.dlmDesarrolladoresProducto.getElementAt(indice);
            desarrolladoresIds.add(desarrollador.getId());
        }

        List<ObjectId> idsAnteriores = producto.getDesarrolladoresIds() != null ?
                new ArrayList<>(producto.getDesarrolladoresIds()) :
                new ArrayList<>();

        producto.setDesarrolladoresIds(desarrolladoresIds);

        actualizarRelacionInversaProducto(producto, desarrolladoresIds, idsAnteriores);
    }

    private void actualizarRelacionInversaProducto(Producto producto,
                                                   List<ObjectId> nuevosIds,
                                                   List<ObjectId> idsAnteriores) {
        ObjectId productoId = producto.getId();

        for (ObjectId desarrolladorId : idsAnteriores) {
            if (!nuevosIds.contains(desarrolladorId)) {
                Desarrollador desarrollador = buscarDesarrolladorPorId(desarrolladorId);
                if (desarrollador != null && desarrollador.getProductosIds() != null) {
                    desarrollador.getProductosIds().remove(productoId);
                    modelo.modificarDesarrollador(desarrollador);
                }
            }
        }

        for (ObjectId desarrolladorId : nuevosIds) {
            if (!idsAnteriores.contains(desarrolladorId)) {
                Desarrollador desarrollador = buscarDesarrolladorPorId(desarrolladorId);
                if (desarrollador != null) {
                    if (desarrollador.getProductosIds() == null) {
                        desarrollador.setProductosIds(new ArrayList<>());
                    }
                    if (!desarrollador.getProductosIds().contains(productoId)) {
                        desarrollador.getProductosIds().add(productoId);
                        modelo.modificarDesarrollador(desarrollador);
                    }
                }
            }
        }
    }

    private Desarrollador buscarDesarrolladorPorId(ObjectId desarrolladorId) {
        for (int i = 0; i < vista.dlmDesarrollador.getSize(); i++) {
            Desarrollador desarrollador = vista.dlmDesarrollador.getElementAt(i);
            if (desarrollador.getId().equals(desarrolladorId)) {
                return desarrollador;
            }
        }
        return null;
    }

    private void cargarProductosEnComboResena() {
        DefaultComboBoxModel<Producto> modeloCombo = new DefaultComboBoxModel<>();
        ArrayList<Producto> todosProductos = modelo.getProductos();

        for (Producto producto : todosProductos) {
            modeloCombo.addElement(producto);
        }

        vista.comboBoxProductoResena.setModel(modeloCombo);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}