package com.marcosguianfrancojoel.zombie_invaders;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.marcosguianfrancojoel.zombie_invaders.Clases.*;
import com.marcosguianfrancojoel.zombie_invaders.Componentes.*;
import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposArmas;
import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposEntidades;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraDeSonidos;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraJSON;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.ListaGlobalArmas;
import com.marcosguianfrancojoel.zombie_invaders.MENU.MiEscenaMenu;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;


import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


/*
    CLASE PRINCIPAL MAIN
    EXTIENDE de GameApplication

    Metodos:

     |> protected void initSettings(GameSettings settings){};
     |> protected void initGame() {};
     |> protected void initInput()
     |> protected void initGameVars(Map<String, Object> vars) {};
     |> protected void initUI() {};
     |> protected void initPhysics() {};
     |> protected void onUpdate(double tpf) {};
     |>  public static void main(String[] args) {};


 */

public class GameMain extends GameApplication {

    private GestoraJSON gestoraJSON = new GestoraJSON();
    private ListaDeUsuarios listaUsuarios = new ListaDeUsuarios();

    private final FabricaDeEntidades fabricaDeEntidades = new FabricaDeEntidades();


    private static int screenWidth;
    private static int screenHeight;

    private Entity jugador;

    //Ajustes iniciales del juego como ventana, titulo, icono, etc...🔧
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(screenWidth);
        settings.setHeight(screenHeight);
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(false);
        settings.setManualResizeEnabled(true);
        settings.setTitle("Zombie Invaders");
        settings.setAppIcon("icon.png");
        settings.setVersion("0.1");

        settings.setMainMenuEnabled(true);

        cargarUsuarios(listaUsuarios, gestoraJSON);
        settings.setSceneFactory(new MiEscenaMenu(listaUsuarios, gestoraJSON));
    }

    //Ejecuta codigo cuando el juego inicializa ✔
    @Override
    protected void initGame() {

        //< Cargar Armas en la lista
        ListaGlobalArmas.add(new ArmaMele("Cuchillo Militar","military_knife", 0, TiposArmas.MELE, 5, 0.4));
        ListaGlobalArmas.add(new ArmaDeFuego("Pistola","pistol", 1, TiposArmas.ARMAFUEGO, 5, 0.4, 2, 12, 48));
        ListaGlobalArmas.add(new ArmaDeFuego("Rifle De Asalto","rifle_assault", 2, TiposArmas.ARMAFUEGO, 7, 0.2, 2, 30, 120));
        ListaGlobalArmas.add(new ArmaDeFuego("Uzi", "uzi", 3, TiposArmas.ARMAFUEGO, 5, 0.08, 2, 40, 160));
        ListaGlobalArmas.add(new ArmaDeFuego("Escopeta Auto","automatic_shotgun", 4, TiposArmas.ARMAFUEGO, 3, 0.5, 0.5, 7, 32));
        //>

        getGameWorld().addEntityFactory(this.fabricaDeEntidades);
        spawn("fondo", new SpawnData(0, 0).put("width", 2000)
                .put("height", 2000));
        jugador = spawn("jugador", 500, 500);

        spawn("gestorRondas", -100, -100);
        spawn("caja_municion", 100, 100);
        spawn("botiquin_chico", 200, 200);

        var viewport = FXGL.getGameScene().getViewport();
        viewport.bindToEntity(jugador, FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() / 2.0);
        viewport.setBounds(0, 0, 2048, 2048);
        viewport.setLazy(true);
        super.initGame();
    }

    //Metodo para detectar ingresos del teclado ⌨
    @Override
    protected void initInput() {

        getInput().addAction(new UserAction("Arriba") {
            @Override
            protected void onAction() {
                jugador.getComponent(ComponenteJugador.class).setUp(true);
            }

            @Override
            protected void onActionEnd() {
                jugador.getComponent(ComponenteJugador.class).setUp(false);
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Abajo") {
            @Override
            protected void onAction() {
                jugador.getComponent(ComponenteJugador.class).setDown(true);
            }

            @Override
            protected void onActionEnd() {
                jugador.getComponent(ComponenteJugador.class).setDown(false);
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Izquierda") {
            @Override
            protected void onAction() {
                jugador.getComponent(ComponenteJugador.class).setLeft(true);
            }

            @Override
            protected void onActionEnd() {
                jugador.getComponent(ComponenteJugador.class).setLeft(false);
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Derecha") {
            @Override
            protected void onAction() {
                jugador.getComponent(ComponenteJugador.class).setRight(true);
            }

            @Override
            protected void onActionEnd() {
                jugador.getComponent(ComponenteJugador.class).setRight(false);
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Cargar_municion") {
            @Override
            protected void onAction() {
                jugador.getComponent(ArmaFuegoComponente.class).recargar();
            }
        }, KeyCode.R);

        getInput().addAction(new UserAction("CambiarArmaSiguiente") {
            @Override
            protected void onActionBegin() {
                jugador.getComponent(ArmaFuegoComponente.class).cambiarSlotSiguiente();

            }
        }, KeyCode.Q);

        getInput().addAction(new UserAction("CambiarArmaAnterior") {
            @Override
            protected void onActionBegin() {
                jugador.getComponent(ArmaFuegoComponente.class).cambiarSlotAnterior();

            }
        }, KeyCode.E);

        getInput().addAction(new UserAction("Disparar") {
            @Override
            protected void onAction() {
                jugador.getComponent(ArmaFuegoComponente.class).disparo();
            }


        }, MouseButton.PRIMARY);

        super.initInput();
    }

    //Crea variables globales 📂
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("hp_max", 100);
        vars.put("hp", 100);

        vars.put("puntaje", 0);
        vars.put("kills", 0);


        vars.put("mapaNivel", 0);
        vars.put("enemigos_vivos", 0);
        vars.put("ronda_actual", 0);

        vars.put("nombreArma", new SimpleStringProperty("escopeta"));
        vars.put("municion", 0);
        vars.put("municion_reserva", 0);

        super.initGameVars(vars);
    }


    //Crea Interfases de usuario 🖥
    @Override
    protected void initUI() {

        //< Barra de vida del jugador 🏥
        double maxWidthHP = 300;

        IntegerProperty hp = getip("hp");
        int hpMax = geti("hp_max");

        Rectangle barBG = new Rectangle(maxWidthHP, 60, Color.color(0.2, 0.2, 0.2, 0.8));
        barBG.setArcWidth(10);
        barBG.setArcHeight(10);


        Rectangle bar = new Rectangle(maxWidthHP, 60, Color.LIMEGREEN);
        bar.setArcWidth(10);
        bar.setArcHeight(10);

        bar.widthProperty().bind(
                hp.divide((double) hpMax).multiply(maxWidthHP)
        );

        double positionBarX = (screenWidth / 2) - 150;
        double positionBarY = (screenHeight/2) + 300;
        Group hpBar = new Group(barBG, bar);
        hpBar.setTranslateX(positionBarX);
        hpBar.setTranslateY(positionBarY);

        getGameScene().addUINode(hpBar);
        //>

        Text vidaJugador = new Text();
        vidaJugador.setTextAlignment(TextAlignment.CENTER);
        vidaJugador.setStroke(Color.WHITE);
        vidaJugador.setTranslateX(30);
        vidaJugador.setTranslateY(20);
        vidaJugador.textProperty().bind(hp.asString("Vida: %d"));

        Text puntaje = new Text();
        puntaje.setTextAlignment(TextAlignment.CENTER);
        puntaje.setStroke(Color.WHITE);
        puntaje.setTranslateX(30);
        puntaje.setTranslateY(50);
        puntaje.textProperty().bind(getip("puntaje").asString("puntaje: %d"));

        Text kills = new Text();
        kills.setTextAlignment(TextAlignment.CENTER);
        kills.setStroke(Color.WHITE);
        kills.setTranslateX(30);
        kills.setTranslateY(70);
        kills.textProperty().bind(getip("kills").asString("Kills: %d"));


        // --------- MUNICIóN ---------
        double municionTextX = (screenWidth / 2) - 50;
        double municionTextY = positionBarY - 50;

        Text municion = getUIFactoryService().newText("Hola", Color.WHITE, 32);
        municion.setTextAlignment(TextAlignment.CENTER);
        municion.setStroke(Color.WHITE);
        municion.setX(municionTextX);
        municion.setY(municionTextY);

        // propiedades
        var nombreArma = (SimpleStringProperty) FXGL.geto("nombreArma");
        var municionProp = FXGL.getip("municion");
        var municionReservaProp = FXGL.getip("municion_reserva");

        municion.textProperty().bind(
                Bindings.createStringBinding(
                        () -> String.format(
                                "|%s|\n %d / %d",
                                nombreArma.get(),
                                municionProp.get(),
                                municionReservaProp.get()
                        ),
                        nombreArma, municionProp, municionReservaProp
                )
        );
        // -----------------------------

        Text numRonda = new Text();
        numRonda.setTextAlignment(TextAlignment.CENTER);
        numRonda.setStroke(Color.WHITE);
        numRonda.setTranslateX(30);
        numRonda.setTranslateY(100);
        numRonda.textProperty().bind(getip("ronda_actual").asString("Ronda: %d"));


        Text zombisVivos = new Text();
        zombisVivos.setTextAlignment(TextAlignment.CENTER);
        zombisVivos.setStroke(Color.WHITE);
        zombisVivos.setTranslateX(30);
        zombisVivos.setTranslateY(130);
        zombisVivos.textProperty().bind(getip("enemigos_vivos").asString("Enemigos: %d"));

        getGameScene().addUINode(vidaJugador);
        getGameScene().addUINode(puntaje);
        getGameScene().addUINode(kills);
        getGameScene().addUINode(municion);
        getGameScene().addUINode(numRonda);
        getGameScene().addUINode(zombisVivos);
    }



    //Crea fisicas y coliciones para las entidades🥂
    @Override
    protected void initPhysics() {
        onCollisionBegin(TiposEntidades.JUGADOR, TiposEntidades.ENEMIGO, (jugador, enemigo) -> {
            var jugadorC = jugador.getComponent(ComponenteJugador.class);
            var enemigoC = enemigo.getComponent(EnemigoComponente.class);

            jugadorC.daniarEntidad(enemigoC.getDanio(), enemigoC.getChance_critico());
            GestoraDeSonidos.play("player/hurt");

            return null;
        });

        onCollision(TiposEntidades.JUGADOR, TiposEntidades.CONSUMIBLE_MUNICION, (jugador, consumible) -> {

            var cajaMunicionC = consumible.getComponent(ConsumibleMunicionComponente.class);
            cajaMunicionC.sumarBalas(jugador);
            return null;
        });

        onCollision(TiposEntidades.JUGADOR, TiposEntidades.CONSUMIBLE_CURA, (jugador, consumible) -> {

            var curativo = consumible.getComponent(ConsumibleCuracionComponente.class);
            curativo.curarEntidad(jugador);
            return null;
        });


        onCollisionBegin(TiposEntidades.PROYECTIL, TiposEntidades.ENEMIGO, (proyectil, enemigo) -> {
            var jugadorC = jugador.getComponent(ComponenteJugador.class);
            var armarFuegoC = jugador.getComponent(ArmaFuegoComponente.class);
            var enemigoC = enemigo.getComponent(EnemigoComponente.class);

            enemigoC.daniarEntidad(armarFuegoC.getDanio_distancia(), jugadorC.getChanceCritico());
            play("player/hit_1.wav");
            proyectil.removeFromWorld();

            return null;
        });
        super.initPhysics();
    }


    //Metodo que se ejecuta en cada frame 💨
    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    //Lanzador de la aplicacion 🛩
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;

        launch(args);
    }

    public static void cargarUsuarios(ListaDeUsuarios lista, GestoraJSON gjson) {
        try {
            // Ruta al archivo JSON
            String ruta = "Usuarios.json";

            String texto = Files.readString(Paths.get(ruta));
            JSONArray arr = new JSONArray(texto);

            lista.cargarDesdeJSON(arr, gjson);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar usuarios desde JSON.");
        }
    }

}
