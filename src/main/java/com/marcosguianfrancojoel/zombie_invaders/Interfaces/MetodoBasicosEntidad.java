package com.marcosguianfrancojoel.zombie_invaders.Interfaces;

import com.almasb.fxgl.entity.Entity;

public interface MetodoBasicosEntidad {

    default public void daniarEntidad(int danio, double chance_critico){};
    default public boolean curarEntidad(int hp){return false;};
    default public void muerte(){};
    default public void eliminarDeLaLista(){};
    default public void loot(){};

}
