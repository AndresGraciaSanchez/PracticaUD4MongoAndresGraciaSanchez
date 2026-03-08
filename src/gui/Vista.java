package gui;

import base.Desarrollador;
import base.Producto;
import base.Resena;
import com.github.lgooddatepicker.components.DatePicker;
import enumerados.Categoria;
import enumerados.Licencia;

import javax.swing.*;
import java.awt.*;

public class Vista extends JFrame {

    public JFrame frame;
    public JPanel panel1;
    public JTabbedPane tabbedPane1;
    public JPanel JPanelProducto;
    public JPanel JPanelDesarrollador;
    public JPanel JPanelResena;
    public JTextField textFieldNombreProducto;
    public JTextField textFieldVersionProducto;
    public JTextArea textAreaDescripcionProducto;
    public JComboBox comboBoxCategoriaProducto;
    public JComboBox comboBoxLicenciaProducto;
    public JList listDesarrolladoresProducto;
    public JList listProductos;
    public JButton modificarButtonProducto;
    public JButton anadirButtonProducto;
    public JButton borrarButtonProducto;
    public JTextField textFieldRepositorio;
    public JTextField textFieldDescargasProducto;
    public DatePicker datePickerFechaLanzamientoProducto;
    public JTextField textFieldNombreDesarrollador;
    public JTextField textFieldEmailDesarrollador;
    public JTextField textFieldPaisDesarrollador;
    public JTextField textFieldContribucionesDesarrollador;
    public JList listDesarrolladores;
    public JButton modificarButtonDesarrollador;
    public JButton anadirButtonDesarrollador;
    public JButton borrarButtonDesarrollador;
    public DatePicker datePickerFechaRegistroDesarrollador;
    public JTextField textFieldUsuarioResena;
    public JTextField textFieldComentarioResena;
    public JSlider sliderPuntuacionResena;
    public DatePicker datePickerFechaPublicacionResena;
    public JList listResenas;
    public JButton modificarButtonResena;
    public JButton anadirButtonResena;
    public JButton borrarButtonResena;
    public JList listProductosDesarrolladores;
    public JComboBox comboBoxProductoResena;
    public JSpinner spinnerEstrellas;

    public DefaultListModel<Producto> dlmProducto;
    public DefaultListModel<Desarrollador> dlmDesarrollador;
    public DefaultListModel<Resena> dlmResena;
    public DefaultListModel<Producto> dlmProductosDesarrolladores;
    public DefaultListModel<Desarrollador> dlmDesarrolladoresProducto;

    public Vista() {
        frame = new JFrame();
        frame.setTitle("Tienda de software y herramientas de desarrollo open-source - [SIN CONEXIÓN]");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(new Dimension(frame.getWidth()+50,frame.getHeight()));
        frame.setVisible(true);

        setEnumComboBox();
        inicializarModelos();

        SpinnerNumberModel modeloEstrellas = new SpinnerNumberModel(0.0, 0.0, 5.0, 0.1);
        spinnerEstrellas.setModel(modeloEstrellas);
        spinnerEstrellas.setEditor(new JSpinner.NumberEditor(spinnerEstrellas, "0.0"));

        frame.setLocationRelativeTo(null);
    }

    public void inicializarModelos(){
        dlmProducto = new DefaultListModel<>();
        listProductos.setModel(dlmProducto);

        dlmDesarrollador = new DefaultListModel<>();
        listDesarrolladores.setModel(dlmDesarrollador);

        dlmResena = new DefaultListModel<>();
        listResenas.setModel(dlmResena);

        dlmDesarrolladoresProducto = new DefaultListModel<>();
        listDesarrolladoresProducto.setModel(dlmDesarrolladoresProducto);

        dlmProductosDesarrolladores = new DefaultListModel<>();
        listProductosDesarrolladores.setModel(dlmProductosDesarrolladores);

        listDesarrolladoresProducto.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listProductosDesarrolladores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    private void setEnumComboBox() {
        for (Licencia licencia : Licencia.values()) {
            comboBoxLicenciaProducto.addItem(licencia.getValor());
        }
        comboBoxLicenciaProducto.setSelectedIndex(-1);

        for (Categoria categoria : Categoria.values()) {
            comboBoxCategoriaProducto.addItem(categoria.getValor());
        }
        comboBoxCategoriaProducto.setSelectedIndex(-1);
    }

}
