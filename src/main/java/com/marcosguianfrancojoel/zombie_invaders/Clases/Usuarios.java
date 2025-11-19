package com.marcosguianfrancojoel.zombie_invaders.Clases;


import java.util.Objects;

public class Usuarios {

    private static int contador = 1;

    private int id;
    private String nombre;
    private String gmail;
    private String password;
    private int puntaje;
    private int kills;
    private int rondaMaxima;

    public Usuarios( String nombre, String gmail, String password, int puntaje, int kills, int rondaMaxima) {
        this.id = contador++;
        this.nombre = nombre;
        this.gmail = gmail;
        this.password = password;
        this.puntaje = puntaje;
        this.kills = kills;
        this.rondaMaxima = rondaMaxima;
    }

    public Usuarios( String nombre, String gmail, String password) {
        this.id = contador++;
        this.nombre = nombre;
        this.gmail = gmail;
        this.password = password;
    }

    public Usuarios() {
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Usuarios.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getRondaMaxima() {
        return rondaMaxima;
    }

    public void setRondaMaxima(int rondaMaxima) {
        this.rondaMaxima = rondaMaxima;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return id == usuarios.id && Objects.equals(nombre, usuarios.nombre) && Objects.equals(gmail, usuarios.gmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, gmail);
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", gmail='" + gmail + '\'' +
                ", password='" + password + '\'' +
                ", puntaje=" + puntaje +
                ", kills=" + kills +
                ", rondaMaxima=" + rondaMaxima +
                '}';
    }
}
