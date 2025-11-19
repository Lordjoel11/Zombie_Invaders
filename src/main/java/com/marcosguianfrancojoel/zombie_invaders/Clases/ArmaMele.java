package com.marcosguianfrancojoel.zombie_invaders.Clases;

import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposArmas;

public class ArmaMele extends CreadorArmas{

    public ArmaMele(String nombreArma, String texture, int id, TiposArmas tipoAarma, int danio_mele, double enfriamiento) {
        super(nombreArma, texture, id, tipoAarma, danio_mele, enfriamiento);
    }

    public ArmaMele() {
    }
}
