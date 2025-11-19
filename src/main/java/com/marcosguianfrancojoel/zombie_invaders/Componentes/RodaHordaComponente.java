package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGL.*;

public class RodaHordaComponente extends Component {

    private int mapaNivel;
    private int numeroDeRonda;
    private int nivelDificultad;

    private int enemigosPorRonda;
    private int enemigosRestantesPorSpawnear;

    private double tiempoEntreSpawns = 1.0;   // segundos entre zombies
    private double spawnTimer = 0;

    private double tiempoEntreRondas = 5.0;   // descanso entre rondas
    private double rondaTimer = 0;

    private boolean activo;
    private boolean enIntermedio;
    private boolean rondaEnCurso;

    public RodaHordaComponente() {
        this.mapaNivel = 0;
        this.numeroDeRonda = 0;
        this.nivelDificultad = 0;
        this.activo = false;
        this.enIntermedio = false;
        this.rondaEnCurso = false;
    }

    @Override
    public void onAdded() {
        activo = true;
        iniciarSiguienteRonda();
    }

    @Override
    public void onUpdate(double tpf) {
        if (!activo) {
            System.out.println("Esta desactivado");
            return;
        }

        if (enIntermedio) {
            rondaTimer -= tpf;

            if (rondaTimer <= 0) {
                iniciarSiguienteRonda();
            }
            return;
        }

        if (!rondaEnCurso) {
            System.out.println("Ronda en curso");
            return;
        }

        // 2) Spawn progresivo de enemigos
        if (enemigosRestantesPorSpawnear > 0) {
            spawnTimer -= tpf;
            if (spawnTimer <= 0) {
                spawnEnemigo();
                enemigosRestantesPorSpawnear--;
                spawnTimer = tiempoEntreSpawns;
            }
        }

        // 3) Chequear fin de ronda:
        //    - No quedan enemigos por spawnear
        //    - No quedan enemigos vivos en el mapa
        if (enemigosRestantesPorSpawnear == 0 && geti("enemigos_vivos") == 0) {
            spawn("caja_municion", 1000, 1000 );
            rondaEnCurso = false;
            enIntermedio = true;
            rondaTimer = tiempoEntreRondas;

        }
    }

    private void iniciarSiguienteRonda() {
        numeroDeRonda++;

        // dificultad: cada ronda tiene mas enemigos
        int base = 5;
        int extraPorRonda = 3;

        this.nivelDificultad = numeroDeRonda;
        this.enemigosPorRonda = base + (numeroDeRonda - 1) * extraPorRonda;
        this.enemigosRestantesPorSpawnear = enemigosPorRonda;

        this.rondaEnCurso = true;
        this.enIntermedio = false;
        this.spawnTimer = 0;

        // actualizar game var para la UI
        set("ronda_actual", numeroDeRonda);
        System.out.println("Iniciando ronda " + numeroDeRonda + " con " + enemigosPorRonda + " enemigos.");
    }

    private boolean chance(int chance)
    {
        return Math.random() * 100 < chance;
    }


    private void spawnEnemigo()
    {
        if (chance( 1 + numeroDeRonda) && numeroDeRonda >= 3)
        {
            spawnEnemigoRunner();
            return;
        }

        if (chance( 1 + numeroDeRonda) && numeroDeRonda >= 7)
        {
            spawnEnemigoRunner();
            return;
        }

        spawnEnemigoZombie();
    }
    private void spawnEnemigoZombie() {

            int mapWidth = 2000;
            int mapHeight = 2000;

            int numRand = random(1, 4);
            double posX = 0;
            double posY = 0;
            switch (numRand) {
                case 1:
                    posX = random(0, mapWidth);
                    posY = -100;
                    break;
                case 2:
                    posX = random(0, mapWidth);
                    posY = mapHeight + 50;
                    break;
                case 3:
                    posY = random(0, mapHeight);
                    posX = -100;
                case 4:
                    posY = random(0, mapHeight);
                    posX = mapWidth + 50;
                    break;
                default:
                    System.out.println("Error: numRand invalido.");
                    break;
            }

            spawn("zombie", posX, posY);
    }
    private void spawnEnemigoRunner() {

        int mapWidth = 2000;
        int mapHeight = 2000;

        int numRand = random(1, 4);
        double posX = 0;
        double posY = 0;
        switch (numRand) {
            case 1:
                posX = random(0, mapWidth);
                posY = -100;
                break;
            case 2:
                posX = random(0, mapWidth);
                posY = mapHeight + 50;
                break;
            case 3:
                posY = random(0, mapHeight);
                posX = -100;
            case 4:
                posY = random(0, mapHeight);
                posX = mapWidth + 50;
                break;
            default:
                System.out.println("Error: numRand invalido.");
                break;
        }

        spawn("zombie_runner", posX, posY);
    }



}
