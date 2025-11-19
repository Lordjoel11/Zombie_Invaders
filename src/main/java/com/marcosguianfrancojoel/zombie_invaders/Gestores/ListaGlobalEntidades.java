package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ListaGlobalEntidades {

    private static final List<Entity> enemies = new LinkedList<>();

    private ListaGlobalEntidades() {}

    public static void add(Entity e) {
        enemies.add(e);
    }

    public static void remove(Entity e) {
        enemies.remove(e);
    }

    public static List<Entity> getAll() {
        return List.copyOf(enemies); // inmutable
    }

    public static int count() {
        return enemies.size();
    }

    public static Entity getClosest(Point2D point) {
        return enemies.stream()
                .min(Comparator.comparingDouble(e -> e.getCenter().distance(point)))
                .orElse(null);
    }

    public static Entity getRandom() {
        if (enemies.isEmpty()) return null;
        return enemies.get(FXGLMath.random(0, enemies.size() - 1));
    }
}
