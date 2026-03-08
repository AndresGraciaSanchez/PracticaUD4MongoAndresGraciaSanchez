package main;

import gui.Controlador;
import gui.Modelo;
import gui.Vista;

public class Principal {

    public static void main(String[] args) {
        new Controlador(new Modelo(), new Vista());
    }

}
