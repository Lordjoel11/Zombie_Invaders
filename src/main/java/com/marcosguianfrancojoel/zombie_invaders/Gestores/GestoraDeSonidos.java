package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;


public class GestoraDeSonidos {

    //Sonidos cortos
    private static final Map<String, List<Sound>> soundGroups = new HashMap<>();

    //Música agrupada por carpeta
    private static final Map<String, List<Music>> musicGroups = new HashMap<>();
    private static Music currentMusic;



    public static void init() {
        loadSounds(Paths.get("src/main/resources/assets/sounds"));
        loadMusic(Paths.get("src/main/resources/assets/music"));

        System.out.println("Sonidos cargados: " + soundGroups.keySet());
        System.out.println("Música cargada: " + musicGroups.keySet());
    }


    //Cargar sonidos

    private static void loadSounds(Path baseDir) {
        try {
            Files.walk(baseDir)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".wav") || p.toString().endsWith(".mp3"))
                    .forEach(p -> loadSoundInGroup(baseDir, p));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadSoundInGroup(Path baseDir, Path soundFile) {
        Path relativePath = baseDir.relativize(soundFile);
        String relativeStr = relativePath.toString().replace("\\", "/");
        String groupName = relativeStr.substring(0, relativeStr.lastIndexOf("/"));
        Sound sound = getAssetLoader().loadSound(relativeStr);
        soundGroups.computeIfAbsent(groupName, k -> new ArrayList<>()).add(sound);
    }

    public static void play(String groupName) {
        List<Sound> group = soundGroups.get(groupName);
        if (group == null || group.isEmpty()) {
            System.err.println("Grupo de sonido no encontrado: " + groupName);
            return;
        }
        int idex = random(0, group.size() - 1);

        Sound sound = group.get(idex);
        getAudioPlayer().playSound(sound);
    }

    //Cargar sonidos
    private static void loadMusic(Path baseDir) {
        try {
            Files.walk(baseDir)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".mp3") || p.toString().endsWith(".wav"))
                    .forEach(p -> loadMusicInGroup(baseDir, p));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadMusicInGroup(Path baseDir, Path musicFile) {
        Path relativePath = baseDir.relativize(musicFile);
        String relativeStr = relativePath.toString().replace("\\", "/");

        // ⚙️ Si el archivo está en la raíz (sin carpeta), usar su nombre como grupo
        int lastSlash = relativeStr.lastIndexOf("/");
        String groupName = (lastSlash == -1) ? "" : relativeStr.substring(0, lastSlash);

        Music music = getAssetLoader().loadMusic(relativeStr);

        musicGroups.computeIfAbsent(groupName, k -> new ArrayList<>()).add(music);
    }

}
