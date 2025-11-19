package com.marcosguianfrancojoel.zombie_invaders.Clases;

import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposArmas;

import java.util.Objects;


/*
    CLASE: CreadorArmas

 */

public abstract class CreadorArmas {


    //ATRIBUTOS
    private String nombreArma;
    private String texture;
    private TiposArmas tipoArma;
    private int id;
    private int danio;
    private double enfriamiento;

    public CreadorArmas(String nombreArma,String texture , int id, TiposArmas tipoAarma, int danio_mele, double enfriamiento) {
        this.nombreArma = nombreArma;
        this.texture = texture;
        this.id = id;
        this.tipoArma = tipoAarma;
        this.danio = danio_mele;
        this.enfriamiento = enfriamiento;
    }

    public CreadorArmas() {
    }

    //SETTERS Y GETTERS


    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getNombreArma() {
        return nombreArma;
    }

    public void setNombreArma(String nombreArma) {
        this.nombreArma = nombreArma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public double getEnfriamiento() {
        return enfriamiento;
    }

    public void setEnfriamiento(double enfriamiento) {
        this.enfriamiento = enfriamiento;
    }


    public TiposArmas getTipoArma() {
        return tipoArma;
    }

    public void setTipoArma(TiposArmas tipoArma) {
        this.tipoArma = tipoArma;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreadorArmas that = (CreadorArmas) o;
        return id == that.id && Objects.equals(nombreArma, that.nombreArma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreArma, id);
    }

    @Override
    public String toString() {
        return "CradorArmas{" +
                "nombreArma='" + nombreArma + '\'' +
                ", id=" + id +
                ", danio=" + danio +
                ", enfriamiento=" + enfriamiento +
                '}';
    }
}
