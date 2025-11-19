package com.marcosguianfrancojoel.zombie_invaders.Clases;

public class UsuarioPremiun extends Usuarios {
    //el premiun tendra acceso a skins y tendra una buffo en partida
    private int extraPlusEstadisticas;
    private boolean premiunActivado;

    public UsuarioPremiun(String nombre, String gmail, String password, int puntaje, int kills, int rondaMaxima, int extraPlusEstadisticas) {
        super(nombre, gmail, password, puntaje, kills, rondaMaxima);
        this.extraPlusEstadisticas = extraPlusEstadisticas;
        this.premiunActivado = true;
    }

    public UsuarioPremiun(String nombre, String gmail, String password, int extraPlusEstadisticas) {
        super(nombre, gmail, password);
        this.extraPlusEstadisticas = extraPlusEstadisticas;
        this.premiunActivado = true;
    }

    public UsuarioPremiun() {
    }


    public int multiPorcentual(int cantidad, int porcentaje)
    {
        porcentaje = porcentaje / 100;
        return cantidad * porcentaje;
    }

    public boolean verificarPremiun()
    {
        if(premiunActivado)
        {
            return true;
        }
        return false;
    }

    public int getExtraPlusEstadisticas() {
        return extraPlusEstadisticas;
    }

    public void setExtraPlusEstadisticas(int extraPlusEstadisticas) {
        this.extraPlusEstadisticas = extraPlusEstadisticas;
    }

    public boolean isPremiunActivado() {
        return premiunActivado;
    }

    public void setPremiunActivado(boolean premiunActivado) {
        this.premiunActivado = premiunActivado;
    }
}
