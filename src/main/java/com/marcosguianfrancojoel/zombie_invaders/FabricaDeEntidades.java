package com.marcosguianfrancojoel.zombie_invaders;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.FollowComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import com.marcosguianfrancojoel.zombie_invaders.Componentes.*;
import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposEntidades;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraDeSprites;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class FabricaDeEntidades implements EntityFactory {



    //Entidad Fondo de pantalla
    @Spawns("fondo")
    public Entity newBackground(SpawnData data) {
        return entityBuilder(data)
                .type(TiposEntidades.FONDO)
                .view(texture("maps/level_1.png", 2048, 2048))
                .with(new IrremovableComponent())
                .zIndex(-100)
                .build();
    }


    //El jugador
    @Spawns("jugador")
    public Entity newJugador(SpawnData data){
        int size = 100;
        Texture texture = GestoraDeSprites.imagenPixel("skin_default_pistol.png", size);

        return entityBuilder(data)

                .type(TiposEntidades.JUGADOR)
                .viewWithBBox(texture)
                .with(new ComponenteJugador(100, 300))
                .with(new ArmaFuegoComponente())
                .with(new ComponenteActualizarAngulo())
                .anchorFromCenter()
                .collidable()
                .zIndex(0)
                .build();
    }

    //El enemigo Zombi
    @Spawns("zombie")
    public Entity newZombie(SpawnData data){

        int size = 100;
        Texture texture = GestoraDeSprites.imagenPixel("enemies/zombie/zombie.png", size);

        int vida = 15;
        double velocidad = 100;
        int danio = 10;
        double chance_critico = 0.05;
        int minDistancia = 0;
        int maxDistancia = 0;
        int puntajeAlMorir = 1;

        return entityBuilder(data)
                .type(TiposEntidades.ENEMIGO)
                .viewWithBBox(texture)
                .with(new FollowComponent(getGameWorld().getSingleton(TiposEntidades.JUGADOR), velocidad, minDistancia, maxDistancia))
                .with(new EnemigoComponente(vida, velocidad, danio, chance_critico, puntajeAlMorir, "zombie" ))
                .with(new ComponenteActualizarAngulo(getGameWorld().getSingleton(TiposEntidades.JUGADOR)))
                .with(new LootComponente())
                .collidable()
                .zIndex(0)
                .build();
    }

    @Spawns("zombie_runner")
    public Entity newZombieRunner(SpawnData data){

        int size = 100;
        Texture texture = GestoraDeSprites.imagenPixel("enemies/zombie_runner/zombie_runner.png", size);

        int vida = 10;
        double velocidad = random(300, 350);
        int danio = 20;
        double chance_critico = 0.05;
        int minDistancia = 0;
        int maxDistancia = 0;
        int puntajeAlMorir = 1;

        return entityBuilder(data)
                .type(TiposEntidades.ENEMIGO)
                .viewWithBBox(texture)
                .with(new FollowComponent(getGameWorld().getSingleton(TiposEntidades.JUGADOR), velocidad, minDistancia, maxDistancia))
                .with(new EnemigoComponente(vida, velocidad, danio, chance_critico, puntajeAlMorir, "zombie_runner" ))
                .with(new ComponenteActualizarAngulo(getGameWorld().getSingleton(TiposEntidades.JUGADOR)))
                .with(new LootComponente())
                .collidable()
                .zIndex(0)
                .build();
    }

    @Spawns("nucleo_zombie")
    public Entity newNucleoZombie(SpawnData data) {
        return entityBuilder(data)
                .type(TiposEntidades.ENEMIGO)
                .viewWithBBox(new Rectangle(100, 100 , Color.CYAN))
                .with(new EnemigoComponente(300, 0, 0, 0, 100, "nucleo_zombie"))
                .with(new SpawnerZombieComponente(true, 10, 4))
                .collidable()
                .zIndex(0)
                .build();
    }

    @Spawns("bala")
    public Entity newBala(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.PROYECTIL)
                .viewWithBBox(new Rectangle(5, 5 , Color.YELLOW))
                .with(new ProjectileComponent(data.get("direction"),1000))
                .with(new ProyectilStatsComponente( 3))
                .collidable()
                .zIndex(-1)
                .build();

    }

    @Spawns("meleHitbox")
    public Entity newMeleHitbox(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.PROYECTIL)
                .bbox(new HitBox("MELEE", BoundingShape.box(40, 40)))
                .collidable()
                .with(new ProjectileComponent(data.get("direction"),0))
                .with(new ProyectilStatsComponente( 0.1))
                .buildAndAttach();

    }

    @Spawns("caja_municion")
    public Entity newCajaMunicion(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.CONSUMIBLE_MUNICION)
                .viewWithBBox(GestoraDeSprites.imagenPixel("consumibles/box_grenade.png", 50))
                .with(new ConsumibleMunicionComponente())
                .collidable()
                .zIndex(-1)
                .buildAndAttach();

    }

    @Spawns("botiquin_chico")
    public Entity newBotiquinChico(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.CONSUMIBLE_CURA)
                .viewWithBBox(GestoraDeSprites.imagenPixel("consumibles/botiquin_small.png", 30))
                .with(new ConsumibleCuracionComponente(15))
                .collidable()
                .zIndex(-2)
                .buildAndAttach();

    }

    @Spawns("botiquin_grande")
    public Entity newBotiquinGrande(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.CONSUMIBLE_CURA)
                .viewWithBBox(GestoraDeSprites.imagenPixel("consumibles/botiquin_big.png", 60))
                .with(new ConsumibleCuracionComponente(50))
                .collidable()
                .zIndex(-2)
                .buildAndAttach();

    }



    @Spawns("gestorRondas")
    public Entity newGestorRondas(SpawnData data){

        return entityBuilder(data)
                .type(TiposEntidades.RONDAS)
                .with(new RodaHordaComponente())
                .zIndex(0)
                .build();

    }


}
