package com.marcosguianfrancojoel.zombie_invaders.Clases;

import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposArmas;

public class ArmaDeFuego extends CreadorArmas{

    private double velocidadRecarga;
    private int capacidadCargador;
    private int balasEnCargador;
    private int balasReserva;
    private int capasidadMaximaReserva;

    public ArmaDeFuego(String nombreArma, String texture, int id, TiposArmas tipoAarma, int danio, double enfriamiento, double velocidadRecarga, int capacidadCargador, int capasidadMaximaReserva) {
        super(nombreArma, texture, id, tipoAarma, danio, enfriamiento);
        this.velocidadRecarga = velocidadRecarga;
        this.capacidadCargador = capacidadCargador;
        this.capasidadMaximaReserva = capasidadMaximaReserva;
        this.balasEnCargador = capacidadCargador;
        this.balasReserva = capasidadMaximaReserva;
    }

    public ArmaDeFuego() {

    }

    public double getVelocidadRecarga() {
        return velocidadRecarga;
    }

    public void setVelocidadRecarga(double velocidadRecarga) {
        this.velocidadRecarga = velocidadRecarga;
    }

    public int getCapacidadCargador() {
        return capacidadCargador;
    }

    public void setCapacidadCargador(int capacidadCargador) {
        this.capacidadCargador = capacidadCargador;
    }

    public int getBalasEnCargador() {
        return balasEnCargador;
    }

    public void setBalasEnCargador(int balasEnCargador) {
        this.balasEnCargador = balasEnCargador;
    }

    public int getBalasReserva() {
        return balasReserva;
    }

    public void setBalasReserva(int balasReserva) {
        this.balasReserva = balasReserva;
    }

    public int getCapasidadMaximaReserva() {
        return capasidadMaximaReserva;
    }

    public void setCapasidadMaximaReserva(int capasidadMaximaReserva) {
        this.capasidadMaximaReserva = capasidadMaximaReserva;
    }
}
