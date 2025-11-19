package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.marcosguianfrancojoel.zombie_invaders.Clases.UsuarioActual;
import com.marcosguianfrancojoel.zombie_invaders.Enums.Skins;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraDeSonidos;
import com.marcosguianfrancojoel.zombie_invaders.Interfaces.MetodoBasicosEntidad;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ComponenteJugador extends Component implements  MetodoBasicosEntidad {

    Entity player;
    //< Estadisticas para el buffeos
    private int hp_max;
    private int hp;
    private double velocidad;
    private int danio;
    private int chanceCritico;
    private double velocidadRecarga;
    private double velociadadDisparo;
    boolean isStepSong;

    private Skins skinJuagdor;

    private boolean up, down, left, right;
    public Point2D moverDire = Point2D.ZERO;

    public void setUp(boolean value) {
        up = value;
    }

    public void setDown(boolean value) {
        down = value;
    }

    public void setLeft(boolean value) {
        left = value;
    }

    public void setRight(boolean value) {
        right = value;
    }


    public ComponenteJugador(int hp_max, double velocidad) {
        this.hp_max = hp_max;
        this.hp = hp_max;
        this.velocidad = velocidad;
        this.danio = 0;
        this.chanceCritico = 0;
        this.velocidadRecarga = 0;
        this.velociadadDisparo = 0;
    }

    @Override
    public void onAdded() {
        player = entity;

        //Establece los valores globales
        set("hp_max", this.hp_max);
        set("hp", this.hp_max);
    }

    @Override
    public void onUpdate(double tpf) {
        double x = 0;
        double y = 0;

        if (up) y -= 1;
        if (down) y += 1;
        if (left) x -= 1;
        if (right) x += 1;

        moverDire = new Point2D(x, y);

        checkForBounds();

        //Traslada la entida hacia alguna direccion
        if (moverDire.magnitude() > 0) {
            playStep();
            Point2D dir = moverDire.normalize();
            entity.translate(dir.multiply(velocidad * tpf));
        }
    }

    @Override
    public void daniarEntidad(int danio, double chance_critico) {
        //Para que entidad reciba danio
        if ((random(0.0, 100) < chance_critico)) {
            hp -= danio * 2;
        } else {
            hp -= danio;
        }

        if (hp < 0) hp = 0;
        if (hp == 0) {

            getDialogService().showMessageBox("💀Game Over💀\n\n" + "\nKills: " + geti("kills") + "\nRondas llegadas: " + geti("ronda_actual"),
                    () -> {
                        if(UsuarioActual.getUsuarioActual() != null)
                        {
                            UsuarioActual.establecerPuntajes();
                            System.out.println("Cargaron los puntos.");
                        }
                        getGameController().gotoGameMenu();
                    }
            );
        }
        set("hp", hp);
    }

    @Override
    public boolean curarEntidad(int curacion) {
        //Para que entidad reciba curacion
        if (hp == hp_max) return false;

        hp += curacion;
        if (hp > hp_max) hp = hp_max;
        set("hp", hp);
        return true;
    }

    private void checkForBounds() {

        double x = entity.getX();
        double y = entity.getY();

        double minX = 0;
        double maxX = 2000 - entity.getWidth();   // limite derecho
        double minY = 0;
        double maxY = 2000 - entity.getHeight();  // limite inferior

        // Si se pasa, lo corrige al limite
        if (x < minX) entity.setX(minX);
        if (x > maxX) entity.setX(maxX);
        if (y < minY) entity.setY(minY);
        if (y > maxY) entity.setY(maxY);
    }

    private void playStep()
    {
        if(isStepSong == false)
        {
            isStepSong = true;
            GestoraDeSonidos.play("player/steps");
            getGameTimer().runOnceAfter( () -> isStepSong = false , Duration.seconds(0.45));
        }
    }
    public double getVelocidadRecarga() {
        return velocidadRecarga;
    }

    public void setVelocidadRecarga(double velocidadRecarga) {
        this.velocidadRecarga = velocidadRecarga;
    }

    public double getVelociadadDisparo() {
        return velociadadDisparo;
    }

    public void setVelociadadDisparo(double velociadadDisparo) {
        this.velociadadDisparo = velociadadDisparo;
    }

    public int getChanceCritico() {
        return chanceCritico;
    }

    public void setChanceCritico(int chanceCritico) {
        this.chanceCritico = chanceCritico;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp_max() {
        return hp_max;
    }

    public void setHp_max(int hp_max) {
        this.hp_max = hp_max;
    }


}
