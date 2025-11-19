package com.marcosguianfrancojoel.zombie_invaders.Componentes;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.marcosguianfrancojoel.zombie_invaders.Clases.*;
import com.marcosguianfrancojoel.zombie_invaders.Enums.TiposArmas;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.*;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
/*
    Clase: ArmaFuegoComponente
    Extiende de: Component
 */
public class ArmaFuegoComponente extends Component {

    //Atributo
    private InventarioDeArmas inventario = new InventarioDeArmas();

    //Metodos
    public void initArmas() {
        inventario.agregar(ListaGlobalArmas.getArmaID(0));
        inventario.agregar(ListaGlobalArmas.getArmaID(1));
        inventario.agregar(ListaGlobalArmas.getArmaID(2));
        inventario.agregar(ListaGlobalArmas.getArmaID(3));
        inventario.agregar(ListaGlobalArmas.getArmaID(4));

        ArmaDeFuego aux = (ArmaDeFuego) inventario.getActual();
        danio_distancia = (aux.getDanio());
        enfriamiento = (aux.getEnfriamiento());
        velocidadRecarga = aux.getVelocidadRecarga();
        capacidadCargador = (aux.getCapacidadCargador());
        balasEnCargador = capacidadCargador;
        balasReserva = (aux.getBalasReserva());
    }

    public void cambiarSlotSiguiente() {
        if (recargando) return;
        guardarEnArma(getArmaActual());
        inventario.siguiente();

        cargarDesdeArma(getArmaActual());
        GestoraDeSonidos.play("player/switch_weapon");
        FXGL.<StringProperty>geto("nombreArma").set(getArmaActual().getNombreArma());
        cambiarSkin();

    }
    public void cambiarSlotAnterior() {
        if (recargando) return;
        guardarEnArma(getArmaActual());
        inventario.anterior();

        cargarDesdeArma(getArmaActual());
        GestoraDeSonidos.play("player/switch_weapon");
        FXGL.<StringProperty>geto("nombreArma").set(getArmaActual().getNombreArma());
        cambiarSkin();

    }

    private void guardarEnArma(CreadorArmas arma) {
        if(arma instanceof ArmaMele)
        {
            arma.setDanio(danio_distancia);
            arma.setEnfriamiento(enfriamiento);
            return;
        }

        ArmaDeFuego aux = (ArmaDeFuego) arma;
        aux.setDanio(danio_distancia);
        aux.setEnfriamiento(enfriamiento);
        aux.setCapacidadCargador(capacidadCargador);
        aux.setBalasEnCargador(balasEnCargador);
        aux.setBalasReserva(balasReserva);
        aux.setVelocidadRecarga(velocidadRecarga);
    }

    private void cargarDesdeArma(CreadorArmas arma) {
        if(arma instanceof ArmaMele){
            danio_distancia = arma.getDanio();
            enfriamiento = arma.getEnfriamiento();
            return;
        }
        ArmaDeFuego aux = (ArmaDeFuego) arma;
        danio_distancia = aux.getDanio();
        enfriamiento = aux.getEnfriamiento();
        capacidadCargador = aux.getCapacidadCargador();
        balasEnCargador = aux.getBalasEnCargador();
        balasReserva = aux.getBalasReserva();
        velocidadRecarga = aux.getVelocidadRecarga();
    }

    private int danio_distancia;
    private double enfriamiento;
    private double velocidadRecarga;

    private int capacidadCargador;   // balas maximas en el cargador
    private int balasEnCargador;     // balas actuales
    private int balasReserva;        // balas que tenes en la mochila
    private int capasidadMaxReserva;

    private boolean recargando = false;

    private boolean controlAtaque = false;

    public ArmaFuegoComponente() {}

    @Override
    public void onAdded() {
        GestoraDeSonidos.init();
        initArmas();
        cambiarSlotSiguiente();
    }

    @Override
    public void onUpdate(double tpf) {
        actualizarUI();
    }

    public void disparo()
    {
        CreadorArmas armaActual = getArmaActual();
        if(armaActual instanceof ArmaDeFuego && armaActual.getId() == 4)
        {
            disparoEscopeta();
        }else if(armaActual instanceof ArmaMele){
            golpeMele();
        }else {
            dispararNormal();
        }
    }

    public void recargar()
    {
        if(getArmaActual().getId() == 4)
        {
            recargarEscopeta();
        }else{
            recargarNormal();
        }
    }
    public void golpeMele() {
        if (!controlAtaque) {
            controlAtaque = true;
            sonidoDisparo();
            Point2D dir = entity.getComponent(ComponenteActualizarAngulo.class).getDirection();
            Point2D spawnPos = entity.getCenter().add(dir.multiply(100));

            spawn("meleHitbox", new SpawnData(spawnPos.getX() , spawnPos.getY())
                    .put("direction", dir));

            getGameTimer().runOnceAfter(() -> controlAtaque = false, Duration.seconds(enfriamiento));
        }
    }

    public void dispararNormal() {
        if (recargando) {
            return;
        }

        if (balasEnCargador == 0) {
            if (balasReserva > 0) {
                recargarNormal();
            }
            return;
        }

        if (!controlAtaque) {
            controlAtaque = true;
            sonidoDisparo();
            Point2D dir = entity.getComponent(ComponenteActualizarAngulo.class).getDirection();
            Point2D spawnPos = entity.getCenter().add(dir.multiply(40));

            spawn("bala", new SpawnData(spawnPos.getX() , spawnPos.getY())
                    .put("direction", dir));
            balasEnCargador--;

            getGameTimer().runOnceAfter(() -> controlAtaque = false, Duration.seconds(enfriamiento));
        }
    }

    public void recargarNormal() {
        if (recargando) return;
        if (balasEnCargador == capacidadCargador) return;
        if (balasReserva <= 0) return;

        recargando = true;
        sonidoRecarga();

        getGameTimer().runOnceAfter(() -> {
            recargando = false;

            int espacio = capacidadCargador - balasEnCargador;
            int recargar = Math.min(espacio, balasReserva);

            balasEnCargador += recargar;
            balasReserva -= recargar;

        }, Duration.seconds(velocidadRecarga));
    }

    public void disparoEscopeta() {
        if (recargando) {
            return;
        }

        if (balasEnCargador == 0) {
            if (balasReserva > 0) {
                recargarEscopeta();
            }
            return;
        }
        if(!controlAtaque)
        {
            controlAtaque = true;
            sonidoDisparo();
            balasEnCargador --;
            for (int i = 0; i < 10; i++) {
                Point2D dir = entity.getComponent(ComponenteActualizarAngulo.class).getDirection();
                double x = random(-0.5, 0.5);
                Point2D aux = new Point2D(x, 0);
                dir = dir.add(aux);
                Point2D spawnPos = entity.getCenter().add(dir.multiply(40));

                spawn("bala", new SpawnData(spawnPos.getX(), spawnPos.getY() )
                        .put("direction", dir));
            }
            getGameTimer().runOnceAfter(() -> controlAtaque = false, Duration.seconds(enfriamiento));

        }
    }

    public void recargarEscopeta() {
        if (recargando) return;
        if (balasEnCargador == capacidadCargador) return;
        if (balasReserva <= 0) return;

        recargando = true;

        cargarUnaBala();
    }

    private void cargarUnaBala() {

        // Si ya está lleno o no quedan balas → terminar
        if (balasEnCargador >= capacidadCargador || balasReserva <= 0) {
            CreadorArmas aux = getArmaActual();
            String nombreArma = aux.getTexture();
            play("weapons/"+ nombreArma + "/"+ nombreArma + "_finish.wav");
            recargando = false;
            return;
        }

        // Cargar 1 bala
        balasEnCargador++;
        balasReserva--;
        sonidoRecarga();

        // Programar la siguiente bala despues de cierto tiempo
        FXGL.getGameTimer().runOnceAfter(
                this::cargarUnaBala,
                Duration.seconds(velocidadRecarga)
        );
    }

    private void actualizarUI() {
        set("municion", balasEnCargador);
        set("municion_reserva", balasReserva);
    }

    private void sonidoRecarga() {
        CreadorArmas aux = getArmaActual();
        String nombreArma = aux.getTexture();
        play("weapons/" + nombreArma + "/" + nombreArma + "_reload.wav");
    }

    private void sonidoDisparo() {
        CreadorArmas aux = getArmaActual();
        String nombreArma = aux.getTexture();
        play("weapons/" + nombreArma + "/" + nombreArma + "_shoot.wav");
    }

    private void cambiarSkin()
    {
        String skinNombre = UsuarioActual.getSkins().getRuta();
        System.out.println("Skin cambio: " + skinNombre);
        String rutaFinal = "skins/" + skinNombre + "/" + skinNombre + "_" + getArmaActual().getTexture() + ".png";
        GestoraDeSprites.setSpriteEntidad(entity, rutaFinal);
    }

    public CreadorArmas getArmaActual() {
        return inventario.getActual();
    }

    public int getDanio_distancia() {
        return danio_distancia;
    }

    public void setDanio_distancia(int danio_distancia) {
        this.danio_distancia = danio_distancia;
    }

    public double getEnfriamiento() {
        return enfriamiento;
    }

    public void setEnfriamiento(double enfriamiento) {
        this.enfriamiento = enfriamiento;
    }

    public boolean isControlAtaque() {
        return controlAtaque;
    }

    public void setControlAtaque(boolean controlAtaque) {
        this.controlAtaque = controlAtaque;
    }

    public double getVelocidadRecarga() {
        return velocidadRecarga;
    }

    public void setVelocidadRecarga(double velocidadRecarga) {
        this.velocidadRecarga = velocidadRecarga;
    }

    public int getCapacidadCargador() {
        return capacidadCargador;
    }

    public void setCapacidadCargador(int capacidadCargador) {
        this.capacidadCargador = capacidadCargador;
    }

    public int getBalasEnCargador() {
        return balasEnCargador;
    }

    public void setBalasEnCargador(int balasEnCargador) {
        this.balasEnCargador = balasEnCargador;
    }

    public int getBalasReserva() {
        return balasReserva;
    }

    public void setBalasReserva(int balasReserva) {
        this.balasReserva = balasReserva;
    }

    public boolean isRecargando() {
        return recargando;
    }

    public void setRecargando(boolean recargando) {
        this.recargando = recargando;
    }


}
