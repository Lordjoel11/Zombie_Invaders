package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ParticleEmitter  {

    public static void spawnBloodImage(Point2D position) {
        int sizeRand = random(20, 100);

        Texture blood = texture("effects/blood_1.png", sizeRand, sizeRand);
        blood.setRotate(Math.random() * 360);

        blood.setOpacity(0.7);

        var entity = entityBuilder()
                .at(position)
                .view(blood)
                .zIndex(-50)
                .buildAndAttach();

        // eliminarla después de 1 segundo
        getGameTimer().runOnceAfter(() -> entity.removeFromWorld(), Duration.seconds(10));
    }

    public static void spawnImage(String texture, Point2D position, double angle, int size) {


        Texture textureAux = texture(texture, size, size);
        textureAux.setRotate(angle);
        var entity = entityBuilder()
                .at(position)
                .view(textureAux)
                .zIndex(-49)
                .buildAndAttach();

        // eliminarla después de 1 segundo
        getGameTimer().runOnceAfter(() -> entity.removeFromWorld(), Duration.seconds(10));
    }


}
