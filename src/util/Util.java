package util;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String formatearFecha(LocalDate fechaMatriculacion) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formateador.format(fechaMatriculacion);
    }

    public static LocalDate parsearFecha(String fecha){
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(fecha, formateador);
    }

    public static void showErrorAlert(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningAlert(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfoAlert(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

}
