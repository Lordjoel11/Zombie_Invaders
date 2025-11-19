package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.marcosguianfrancojoel.zombie_invaders.Clases.ArmaDeFuego;
import com.marcosguianfrancojoel.zombie_invaders.Clases.ArmaMele;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraDeSonidos;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.play;

public class ConsumibleMunicionComponente extends Component {

    public ConsumibleMunicionComponente() {

    }

    @Override
    public void onAdded() {
        getGameTimer().runOnceAfter(() -> { if(entity != null)entity.removeFromWorld(); }, Duration.seconds(20));
    }

    public void sumarBalas(Entity jugador)
    {
       var jugadorC = jugador.getComponent(ArmaFuegoComponente.class);

       ArmaDeFuego armaActual = null;
       if(jugadorC.getArmaActual() instanceof ArmaMele) return;

       armaActual = (ArmaDeFuego) jugadorC.getArmaActual();

       int balasReservaActual = jugadorC.getBalasReserva();
       int balasMaxCargador = jugadorC.getCapacidadCargador();

       //No hace nada si el jugador tiene la reserva llena
       if(balasReservaActual == armaActual.getCapasidadMaximaReserva()) return;

       //Si no tiene se hace un calculo para dar las balas sin pasarse del limite
        // Si no tiene, se hace un cálculo para dar las balas sin pasarse del límite
        int balasNuevas = Math.min(balasReservaActual + balasMaxCargador, armaActual.getCapasidadMaximaReserva());
        jugadorC.setBalasReserva(balasNuevas);
        GestoraDeSonidos.play("player/ammoSound");
        if(entity != null)entity.removeFromWorld();

    }

}
