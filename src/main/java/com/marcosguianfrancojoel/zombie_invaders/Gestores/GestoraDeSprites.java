package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.marcosguianfrancojoel.zombie_invaders.Enums.Skins;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class GestoraDeSprites {

    static public Texture imagenPixel(String rutaImagen, int size)
    {
        Texture texture = texture(rutaImagen, size, size);
        texture.setSmooth(false);
        return texture;
    }


    static public void setSpriteEntidad(Entity entidad, String texturaPath) {

        Texture texture = GestoraDeSprites.imagenPixel(texturaPath, 125);

        var view = entidad.getViewComponent();
        view.clearChildren();

        // centramos la textura dentro del bounding box
        var bbox = entidad.getBoundingBoxComponent();
        double bw = bbox.getWidth();
        double bh = bbox.getHeight();

        double tw = texture.getWidth();
        double th = texture.getHeight();

        texture.setTranslateX((bw - tw) / 2.0);
        texture.setTranslateY((bh - th) / 2.0);

        view.addChild(texture);
    }

    static public void setSpriteEntidad(Entity entidad, Skins texturaPath)
    {

        entidad.getViewComponent().clearChildren();
        entidad.getViewComponent().addChild(new Texture(image(texturaPath.getRuta())));
    }

    static public void removeSpriteEntidad(Entity entidad)
    {
        entidad.getViewComponent().clearChildren();
    }

}
