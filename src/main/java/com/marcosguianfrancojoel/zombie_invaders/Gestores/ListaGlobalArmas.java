package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.core.math.FXGLMath;
import com.marcosguianfrancojoel.zombie_invaders.Clases.CreadorArmas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ListaGlobalArmas {

    private static final List<CreadorArmas> listaArmas =  new CopyOnWriteArrayList<>();

    private ListaGlobalArmas() {}

    public static void add(CreadorArmas e) {
        listaArmas.add(e);
    }

    public static void remove(CreadorArmas e) {
        listaArmas.remove(e);
    }

    public static List<CreadorArmas> getAll() {
        return List.copyOf(listaArmas); // inmutable
    }

    public static int count() {
        return listaArmas.size();
    }

    public static CreadorArmas getArmaID(int id)
    {
        for (CreadorArmas aux : listaArmas)
        {
            if(aux.getId() == id)
            {
                return aux;
            }
        }
        System.out.println("No se encontro el arma de ID: " + id);
        return listaArmas.get(1);
    }

    public static CreadorArmas getArmaName(String nameArma)
    {
        for (CreadorArmas aux : listaArmas)
        {
            if(aux.getNombreArma().equals(nameArma))
            {
                return aux;
            }
        }
        System.out.println("No se encontro el arma de nombre: " + nameArma);
        return listaArmas.get(1);
    }

    public static CreadorArmas getRandom() {
        if (listaArmas.isEmpty()) return null;
        return listaArmas.get(FXGLMath.random(0, listaArmas.size() - 1));
    }


}
