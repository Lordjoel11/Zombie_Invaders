package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.marcosguianfrancojoel.zombie_invaders.Clases.CreadorArmas;

import java.util.ArrayList;
import java.util.List;

public class InventarioDeArmas {

    private final List<CreadorArmas> armas = new ArrayList<>();
    private int indiceActual = 2;

    public CreadorArmas getArmaId (int id)
    {
        for (CreadorArmas aux : armas)
        {
            if(aux.getId() == id) return aux;

        }

        return null;
    }

    public void agregar(CreadorArmas arma) {
        armas.add(arma);
    }

    public CreadorArmas getActual() {
        return armas.isEmpty() ? null : armas.get(indiceActual);
    }

    public void siguiente() {
        if (armas.isEmpty()) return;
        indiceActual = (indiceActual + 1) % armas.size();
    }

    public void anterior() {
        if (armas.isEmpty()) return;
        indiceActual = (indiceActual - 1 + armas.size()) % armas.size();
    }

    public int count() {
        return armas.size();
    }

}
