package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.entity.Entity;
import com.marcosguianfrancojoel.zombie_invaders.Clases.UsuarioActual;
import com.marcosguianfrancojoel.zombie_invaders.Clases.UsuarioFree;
import com.marcosguianfrancojoel.zombie_invaders.Clases.UsuarioPremiun;
import com.marcosguianfrancojoel.zombie_invaders.Componentes.ArmaFuegoComponente;
import com.marcosguianfrancojoel.zombie_invaders.Componentes.ComponenteJugador;
import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposEntidades;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;


public class ClaseUtilidad {

    public static int calcularPuntaje() {
        if (UsuarioActual.getUsuarioActual() == null) {
            System.out.println("No EXISTE JUGADOR INICIALISADO");
            return 0;
        }

        int rondasJugadas = UsuarioActual.getUsuarioActual().getRondaMaxima();
        int kills = UsuarioActual.getUsuarioActual().getKills();
        int totalPuntaje = kills * 10 * (int) (1 + rondasJugadas * 0.2);
        ;

        return totalPuntaje;
    }

    public static void estabalbecerEstadisticasPremiun() {
        Entity jugador = getGameWorld().getSingleton(TiposEntidades.JUGADOR);

        var jugadorC = jugador.getComponent(ComponenteJugador.class);
        var armasC = jugador.getComponent(ArmaFuegoComponente.class);

        var usuarioActual = UsuarioActual.getUsuarioActual();
        if(jugador == null) return;
        if (usuarioActual == null) return;
        if (usuarioActual instanceof UsuarioFree) return;

        UsuarioPremiun usuarioPremiun = (UsuarioPremiun) UsuarioActual.getUsuarioActual();

        armasC.setVelocidadRecarga(armasC.getVelocidadRecarga() + (armasC.getVelocidadRecarga() * usuarioPremiun.getExtraPlusEstadisticas()));
        armasC.setEnfriamiento(armasC.getEnfriamiento() + (armasC.getEnfriamiento() * usuarioPremiun.getExtraPlusEstadisticas()));
        armasC.setDanio_distancia(armasC.getDanio_distancia() + (armasC.getDanio_distancia() * usuarioPremiun.getExtraPlusEstadisticas()));
        armasC.setDanio_distancia(armasC.getDanio_distancia() + (armasC.getDanio_distancia() * usuarioPremiun.getExtraPlusEstadisticas()));
        jugadorC.setHp_max(jugadorC.getHp_max() + (jugadorC.getHp_max() * usuarioPremiun.getExtraPlusEstadisticas()));
    }


}
