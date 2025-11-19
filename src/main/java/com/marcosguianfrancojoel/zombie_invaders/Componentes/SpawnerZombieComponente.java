package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SpawnerZombieComponente extends Component {

    private boolean activacion = false;
    private double enfriamiento;
    private int cantZombies;

    public SpawnerZombieComponente(boolean activacion, double enfriamiento, int cantZombies) {
        this.activacion = activacion;
        this.enfriamiento = enfriamiento;
        this.cantZombies = cantZombies;
    }

    @Override
    public void onUpdate(double tpf) {

        spawnearZombies();

    }

    private void spawnearZombies()
    {
        if(activacion)
        {
            activacion = false;
            spawnRand();
            getGameTimer().runOnceAfter(()-> activacion = true, Duration.seconds(enfriamiento));

        }
    }

    private void spawnRand()
    {
        for (int i = 0; i < cantZombies ; i++) {
            int x = random(-100,100);
            int y = random(-100,100);

            Point2D puntoAparicion = new Point2D(entity.getX() + x,entity.getY() + y);
            spawn("zombie", puntoAparicion);
        }
    }

    public boolean isActivacion() {
        return activacion;
    }

    public void setActivacion(boolean activacion) {
        this.activacion = activacion;
    }

    public double getEnfriamiento() {
        return enfriamiento;
    }

    public void setEnfriamiento(double enfriamiento) {
        this.enfriamiento = enfriamiento;
    }

    public int getCantZombies() {
        return cantZombies;
    }

    public void setCantZombies(int cantZombies) {
        this.cantZombies = cantZombies;
    }
}
