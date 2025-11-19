package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class LootComponente extends Component {


    @Override
    public void onRemoved() {
        lootSistema();
    }


    public void lootSistema()
    {

        if(chance(4))
        {
            spawn("caja_municion", entity.getCenter());
            return;

        }

        if (chance(5)) {
            spawn("botiquin_chico", entity.getCenter());
        }

        if (chance(2)) {
            spawn("botiquin_grande", entity.getCenter());
        }

    }




    public boolean chance(int chance)
    {
        return Math.random() * 100 < chance;
    }



}
