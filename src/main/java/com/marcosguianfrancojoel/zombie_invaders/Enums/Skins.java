package com.marcosguianfrancojoel.zombie_invaders.Enums;

public enum Skins {
    SKIN_DEFAULT("skin_default"),
    SKIN_DORADO("skin_golden");

    private final String ruta;

    // fondo/preview para el menú
    public String getPreviewPath() {
        // assets/textures/skins/<id>/<id>_preview.png
        return "skins/" + ruta + "/" + ruta + "_military_knife.png";
    }


    Skins(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
