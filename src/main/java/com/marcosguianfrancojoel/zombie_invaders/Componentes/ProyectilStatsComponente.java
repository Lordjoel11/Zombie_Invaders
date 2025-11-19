package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;

public class ProyectilStatsComponente extends Component {

    private  double tiempoDeVida;

    public ProyectilStatsComponente( double tiempoDeVida) {
        this.tiempoDeVida = tiempoDeVida;
    }

    @Override
    public void onAdded() {
        getGameTimer().runOnceAfter(() -> {if(entity != null) entity.removeFromWorld();}, Duration.seconds(tiempoDeVida));
    }


    public double getTiempoDeVida() {
        return tiempoDeVida;
    }

    public void setTiempoDeVida(double tiempoDeVida) {
        this.tiempoDeVida = tiempoDeVida;
    }
}
