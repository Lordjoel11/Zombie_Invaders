package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Iterator;


public class GestoraDeAnimacion {

    private AnimatedTexture texture;
    private AnimationChannel animacion;

    private Entity entidadActual;

    public GestoraDeAnimacion(Entity entidadActual,String nombreEntidadAnim, String tipoAnimacion, int sizePixel, int frames, double duracion) {
        this.animacion =crearAnim(nombreEntidadAnim, tipoAnimacion, sizePixel,frames, duracion);
        this.texture = new AnimatedTexture(animacion);
        this.entidadActual = entidadActual;
    }

    private AnimationChannel crearAnim (String nombreEntidad, String tipoAnimacion, int sizePixel, int frames, double duracion)
    {
        return new AnimationChannel(
                encontrarImagen(nombreEntidad, tipoAnimacion),
                frames, sizePixel, sizePixel,
                Duration.seconds(duracion),
                0, frames-1

        );
    }

    private Image encontrarImagen(String nombreEntidad, String tipoAnimacion)
    {
        return new Image("enemies/"+  nombreEntidad + "/animations/" + nombreEntidad + "_" + tipoAnimacion + ".png");
    }

    public void play (Entity entity)
    {
        animActual(entity);
        texture.playAnimationChannel(animacion);
    }

    public void playLoop (Entity entity)
    {
        animActual(entity);
        texture.loopAnimationChannel(animacion);
    }

    public void stop (Entity entity)
    {
        if(texture != null){
            texture.stop();
        }
    }

    private void animActual(Entity entity) {
        if (entidadActual != entity) {
            if (entidadActual != null) {
                entidadActual.getViewComponent().removeChild(texture);
            }
            entidadActual = entity;
            entity.getViewComponent().addChild(texture);
        }
    }
}
