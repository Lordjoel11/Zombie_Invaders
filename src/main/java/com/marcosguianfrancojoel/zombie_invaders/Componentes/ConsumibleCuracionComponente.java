package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.play;

public class ConsumibleCuracionComponente extends Component {

    private int cantidadCurar;

    public ConsumibleCuracionComponente(int cantidadCurar) {
        this.cantidadCurar = cantidadCurar;
    }


    @Override
    public void onAdded() {
        getGameTimer().runOnceAfter(() -> { if(entity != null)entity.removeFromWorld(); }, Duration.seconds(20));
    }

    public void curarEntidad(Entity jugador)
    {
        var jugadorC = jugador.getComponent(ComponenteJugador.class);

        if(!jugadorC.curarEntidad(cantidadCurar))return;
        play("player/cure_1.wav");
        if(entity != null)entity.removeFromWorld();

    }

    public int getCantidadCurar() {
        return cantidadCurar;
    }

    public void setCantidadCurar(int cantidadCurar) {
        this.cantidadCurar = cantidadCurar;
    }
}
