package com.marcosguianfrancojoel.zombie_invaders.MENU;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.marcosguianfrancojoel.zombie_invaders.Clases.ListaDeUsuarios;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraJSON;

public class MiEscenaMenu extends SceneFactory {

    private ListaDeUsuarios listaUsuarios;
    private GestoraJSON gestoraJSON;

    public MiEscenaMenu(ListaDeUsuarios listaUsuarios, GestoraJSON gestoraJSON) {
        this.listaUsuarios = listaUsuarios;
        this.gestoraJSON = gestoraJSON;
    }

    @Override
    public FXGLMenu newMainMenu() {
        return new MiMenu(MenuType.MAIN_MENU, listaUsuarios, gestoraJSON);
    }

    @Override
    public FXGLMenu newGameMenu() {
        return new MiMenu(MenuType.GAME_MENU, listaUsuarios, gestoraJSON);
    }
}
