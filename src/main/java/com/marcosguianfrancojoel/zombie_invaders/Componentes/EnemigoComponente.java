package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.component.Component;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.ClaseUtilidad;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraDeSprites;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.ListaGlobalEntidades;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.ParticleEmitter;
import com.marcosguianfrancojoel.zombie_invaders.Interfaces.MetodoBasicosEntidad;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class EnemigoComponente extends Component implements MetodoBasicosEntidad {

    private int hp_max;
    private int hp;
    private double velocidad;
    private int danio;
    private double chance_critico;
    private int puntajeAlMorir;
    private String nombreEntidad;

    public EnemigoComponente(int hp_max, double velocidad, int danio, double chance_critico, int puntajeAlMorir, String nombreEntidad) {
        this.hp_max = hp_max;
        this.hp = hp_max;
        this.velocidad = velocidad;
        this.danio = danio;
        this.chance_critico = chance_critico;
        this.puntajeAlMorir = puntajeAlMorir;
        this.nombreEntidad = nombreEntidad;
    }

    @Override
    public void onAdded() {
        ListaGlobalEntidades.add(entity);
        inc("enemigos_vivos", 1);

    }

    @Override
    public void onRemoved() {

        ListaGlobalEntidades.remove(entity);
        play("enemies/zombie/zombie_dead.wav");
        ParticleEmitter.spawnImage( "enemies/" + nombreEntidad + "/" + nombreEntidad + "_died.png", entity.getPosition(), entity.getRotation(), 110);
        ParticleEmitter.spawnBloodImage(entity.getPosition());
        inc("enemigos_vivos", -1);

        inc("kills", 1);

        set("puntaje", ClaseUtilidad.calcularPuntaje());
    }

    @Override
    public void daniarEntidad(int danio, double chance_Critico ) {
        //Para que entidad reciba danio
        if((random(0.0, 100.0) < chance_Critico))
        {
            hp -= danio * 2;
        }else { hp -= danio;}
        muerte();
    }

    @Override
    public boolean curarEntidad(int curacion) {
        //Para que entidad reciba curacion
        if(hp == hp_max) return false;

        hp += curacion;
        if (hp > hp_max) hp = hp_max;
        set("hp", hp);
        return true;
    }



    @Override
    public void muerte() {
        if(hp <= 0) {entity.removeFromWorld();}
    }

    public int getHp_max() {
        return hp_max;
    }

    public void setHp_max(int hp_max) {
        this.hp_max = hp_max;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public double getChance_critico() {
        return chance_critico;
    }

    public void setChance_critico(double chance_critico) {
        this.chance_critico = chance_critico;
    }

    public int getPuntajeAlMorir() {
        return puntajeAlMorir;
    }

    public void setPuntajeAlMorir(int puntajeAlMorir) {
        this.puntajeAlMorir = puntajeAlMorir;
    }
}
