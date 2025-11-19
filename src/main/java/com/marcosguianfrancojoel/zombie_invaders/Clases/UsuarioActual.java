package com.marcosguianfrancojoel.zombie_invaders.Clases;

import com.marcosguianfrancojoel.zombie_invaders.Enums.Skins;

import static com.almasb.fxgl.dsl.FXGLForKtKt.geti;

public class UsuarioActual {

    private static Usuarios usuarioActual;
    private static Skins skins = Skins.SKIN_DEFAULT;

    public static UsuarioFree getUsuarioFreeActual()
    {
        if(usuarioActual instanceof UsuarioFree) return (UsuarioFree) usuarioActual;
        System.out.println("No se pudo castear porque no es el dato correcto: UsuarioFree");
        return null;
    }

    public static UsuarioPremiun getUsuarioPremiunActual()
    {
        if(usuarioActual instanceof UsuarioPremiun) return (UsuarioPremiun) usuarioActual;
        System.out.println("No se pudo castear porque no es el dato correcto: UsuarioPremiun");
        return null;
    }

    public static void establecerPuntajes()
    {
        usuarioActual.setPuntaje(geti("puntaje"));
        usuarioActual.setKills(geti("kills"));
        usuarioActual.setRondaMaxima(geti("ronda_actual"));
    }

    public static Usuarios getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuarios usuarioActual) {
        UsuarioActual.usuarioActual = usuarioActual;
    }

    public static Skins getSkins() {
        return skins;
    }

    public static void setSkins(Skins skinsP) {
        skins = skinsP;
    }


}
