package com.marcosguianfrancojoel.zombie_invaders.Clases;

public class UsuarioFree extends Usuarios{

    public UsuarioFree( String nombre, String gmail, String password, int puntaje, int kills, int rondaMaxima) {
        super( nombre, gmail, password, puntaje, kills, rondaMaxima);
    }

    public UsuarioFree( String nombre, String gmail, String password) {
        super( nombre, gmail, password);
    }

    public UsuarioFree() {
    }


}
