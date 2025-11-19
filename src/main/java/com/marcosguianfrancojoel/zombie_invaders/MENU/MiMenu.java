package com.marcosguianfrancojoel.zombie_invaders.MENU;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import com.marcosguianfrancojoel.zombie_invaders.Clases.*;
import com.marcosguianfrancojoel.zombie_invaders.Enums.Skins;
import com.marcosguianfrancojoel.zombie_invaders.Exepciones.UsuarioNoEncontradoException;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.ClaseUtilidad;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraJSON;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.OperacionLectoEscritura;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.JSONArray;

import java.util.Comparator;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MiMenu extends FXGLMenu {
    private ListaDeUsuarios listaUsuarios;
    private GestoraJSON gestoraJSON;
    UsuarioActual usuarioActual = new UsuarioActual();

    private final Skins[] todasLasSkins = Skins.values();
    private int skinIndex = 0;

    public MiMenu(MenuType mainMenu, ListaDeUsuarios listaUsuarios, GestoraJSON gestoraJSON) {
        super(MenuType.MAIN_MENU);
        this.listaUsuarios = listaUsuarios;
        this.gestoraJSON = gestoraJSON;

        var ui = FXGL.getUIFactoryService();

        // Fondo
        Texture fondo = texture("ui/fondoMenu.png");
        fondo.setFitWidth(FXGL.getAppWidth());
        fondo.setFitHeight(FXGL.getAppHeight());
        getContentRoot().getChildren().add(fondo);

        // Título
        Text titulo = ui.newText("ZOMBIE INVADERS", Color.LIMEGREEN, 48);

        // Botones
        Button btnJugar = ui.newButton("Jugar");
        btnJugar.setOnAction(e -> {
            playClick();
            fireNewGame();
        });

        Button btnSkins = ui.newButton("Skins");
        btnSkins.setOnAction(e -> {
            playClick();
            var usuarioActual = UsuarioActual.getUsuarioActual();

            if (usuarioActual == null) {

                playError();
                getDialogService().showMessageBox("Primero inicia sesión.");
                return;
            }

            if (usuarioActual instanceof UsuarioPremiun) {
                mostrarSkins();
                return;
            }

            playError();
            getDialogService().showMessageBox("No eres usuario premium.");
        });

        Button btnPuntajes = ui.newButton("Puntajes");
        btnPuntajes.setOnAction(e -> {
            playClick();
            mostrarVentanaPuntajes();
        });

        Button btnLogin = ui.newButton("Iniciar sesión");
        btnLogin.setOnAction(e -> {
            playClick();

            if(!verificarExistenciaDeUsuario())
            {
                mostrarVentanaLogin();
            }else {
                playError();
                getDialogService().showMessageBox("Ya hay una cuenta logeada.");
            }
        });

        Button btnRegistro = ui.newButton("Registrarse");
        btnRegistro.setOnAction(e -> {
            playClick();
            if(!verificarExistenciaDeUsuario())
            {
                mostrarVentanaRegistrar();

            }else {
                playError();
                getDialogService().showMessageBox("Ya hay una cuenta logeada.");
            }
        });

        Button btnRegistroPremiun = ui.newButton("Registrarse Premiun");
        btnRegistroPremiun.setOnAction(e -> {
            playClick();
            if(!verificarExistenciaDeUsuario())
            {
                mostrarVentanaPremiuRegistrar();
            }else {
                playError();
                getDialogService().showMessageBox("Ya hay una cuenta logeada.");
            }
        });

        Button btnCerrarCuenta = ui.newButton("Cerrar cuenta");
        btnCerrarCuenta.setOnAction(e ->{
            playClick();
            if(UsuarioActual.getUsuarioActual() != null)
            {
                cerrarCuenta();
            }else {
                playError();
                getDialogService().showMessageBox("No hay cuenta que cerrar.");
            }
        });

        Button btnSalir = ui.newButton("Salir");
        btnSalir.setOnAction(e -> {
            playClick();
            if(UsuarioActual.getUsuarioActual() != null) {
                if(UsuarioActual.getUsuarioActual().getPuntaje() > geti("puntaje"))
                {
                    UsuarioActual.establecerPuntajes();
                }
            }
            guardarUsuariosEnJSON();
            fireExit();
        });

        VBox menu = new VBox(15, titulo, btnJugar, btnSkins, btnPuntajes, btnLogin, btnRegistro, btnRegistroPremiun, btnCerrarCuenta, btnSalir);
        menu.setAlignment(Pos.CENTER);

        menu.layoutBoundsProperty().addListener((obs, oldV, newV) -> {
            menu.setTranslateX((FXGL.getAppWidth() - newV.getWidth()) / 2);
            menu.setTranslateY((FXGL.getAppHeight() - newV.getHeight()) / 2);
        });

        getContentRoot().getChildren().add(menu);
    }

    private void mostrarVentanaPuntajes() {
        var ui = getUIFactoryService();

        var contenido = new VBox(5);
        contenido.setAlignment(Pos.CENTER_LEFT);

        var usuariosLista = listaUsuarios.getListaUsuarios();

        var usuarios = usuariosLista.stream()
                .filter(u -> u instanceof Usuarios)
                .map(u -> (Usuarios) u)
                // 3) Ordenamos por kills descendente
                .sorted(Comparator.comparingInt(Usuarios::getKills).reversed())
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            contenido.getChildren().add(ui.newText("No hay jugadores registrados aún."));
        } else {
            for (Usuarios j : usuarios) {
                String linea = j.getNombre()
                        + "  Puntaje: " + j.getPuntaje()
                        + " - Kills: " + j.getKills()
                        + " - Ronda Máxima: " + j.getRondaMaxima();
                contenido.getChildren().add(ui.newText(linea));
            }
        }

        var btnCerrar = ui.newButton("Cerrar");

        getDialogService().showBox(
                "Puntajes",
                contenido,
                btnCerrar
        );
    }

    private void mostrarVentanaLogin() {
        var ui = getUIFactoryService();

        var txtGmail = new TextField();
        txtGmail.setPromptText("Gmail");

        var txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");

        var contenido = new VBox(10,
                ui.newText("Iniciar sesión"),
                txtGmail,
                txtPassword
        );
        contenido.setAlignment(Pos.CENTER);

        var btnEntrar = ui.newButton("Entrar");

        btnEntrar.setOnAction(e -> {
            String gmail = txtGmail.getText();
            String pass = txtPassword.getText();

            try {
                Usuarios u = listaUsuarios.obtenerPorGmail(gmail);

                if (!u.getPassword().equals(pass)) {
                    throw new UsuarioNoEncontradoException("Contraseña incorrecta.");
                }

                getDialogService().showMessageBox("¡Bienvenido, " + u.getNombre() + "!");
                UsuarioActual.setUsuarioActual(u);

            } catch (UsuarioNoEncontradoException ex) {
                getDialogService().showMessageBox(ex.getMessage());
            }
        });

        var btnCerrar = ui.newButton("Cerrar");

        getDialogService().showBox(
                "Login",
                contenido,
                btnEntrar,
                btnCerrar
        );
    }

    private void mostrarVentanaRegistrar() {
        var ui = getUIFactoryService();

        var textName = new TextField();
        textName.setPromptText("Apodo");

        var txtGmail = new TextField();
        txtGmail.setPromptText("Gmail");

        var txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");

        var contenido = new VBox(10,
                ui.newText("Registrarse"),
                textName,
                txtGmail,
                txtPassword
        );
        contenido.setAlignment(Pos.CENTER);

        var btnEntrar = ui.newButton("Crear cuenta");

        btnEntrar.setOnAction(e -> {
            String name = textName.getText();
            String gmail = txtGmail.getText();
            String pass = txtPassword.getText();

            Usuarios u = listaUsuarios.buscarPorGmail(gmail);

            if (listaUsuarios.existeGmail(gmail)) {
                getDialogService().showMessageBox("Ya existe un usuario con ese gmail.");
                return;
            }


            getDialogService().showMessageBox("¡Bienvenido, " + name + " su cuenta a sido registrada!");
            Usuarios usuarioFree = new UsuarioFree(name, gmail, pass );
            listaUsuarios.add(usuarioFree);
            UsuarioActual.setUsuarioActual(usuarioFree);
        });

        var btnCerrar = ui.newButton("Cerrar");

        getDialogService().showBox(
                "Login",
                contenido,
                btnEntrar,
                btnCerrar
        );
    }

    private void mostrarVentanaPremiuRegistrar() {
        var ui = getUIFactoryService();

        var textName = new TextField();
        textName.setPromptText("Apodo");

        var txtGmail = new TextField();
        txtGmail.setPromptText("Gmail");

        var txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");

        var contenido = new VBox(10,
                ui.newText("Registrarse"),
                textName,
                txtGmail,
                txtPassword
        );
        contenido.setAlignment(Pos.CENTER);

        var btnEntrar = ui.newButton("Crear cuenta");

        btnEntrar.setOnAction(e -> {
            String name = textName.getText();
            String gmail = txtGmail.getText();
            String pass = txtPassword.getText();

            Usuarios u = listaUsuarios.buscarPorGmail(gmail);

            if (listaUsuarios.existeGmail(gmail)) {
                playError();
                getDialogService().showMessageBox("Ya existe un usuario con ese gmail.");
                return;
            }


            getDialogService().showMessageBox("¡Bienvenido, " + name + " su cuenta a sido registrada!");
            Usuarios UsuarioPremiun = new UsuarioPremiun(name, gmail, pass, 25 );
            listaUsuarios.add(UsuarioPremiun);
            UsuarioActual.setUsuarioActual(UsuarioPremiun);
        });

        var btnCerrar = ui.newButton("Cerrar");

        getDialogService().showBox(
                "Login",
                contenido,
                btnEntrar,
                btnCerrar
        );
    }

    private void mostrarSkins() {

        var ui = getUIFactoryService();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Text titulo = ui.newText("Seleccionar Skin", Color.WHITE, 24);

        // Tenemos solo 2 skins
        Skins[] opciones = { Skins.SKIN_DEFAULT, Skins.SKIN_DORADO };

        // Empezamos en la skin que tenga el usuario actualmente
        final int[] index = { 0 };
        if (UsuarioActual.getSkins() == Skins.SKIN_DORADO) {
            index[0] = 1;
        }

        // Preview inicial
        Texture skinPreview = texture(opciones[index[0]].getPreviewPath());
        skinPreview.setFitWidth(100);
        skinPreview.setFitHeight(100);

        Button btnAnterior = ui.newButton("<");
        Button btnSiguiente = ui.newButton(">");

        HBox botones = new HBox(10, btnAnterior, btnSiguiente);
        botones.setAlignment(Pos.CENTER);

        // Cambiar a la otra skin (
        Runnable actualizarPreview = () -> {
            skinPreview.setImage(texture(opciones[index[0]].getPreviewPath()).getImage());
        };

        btnSiguiente.setOnAction(e -> {
            playChange();
            index[0] = 1 - index[0];  // 0->1, 1->0
            actualizarPreview.run();
        });

        btnAnterior.setOnAction(e -> {
            playChange();
            index[0] = 1 - index[0];  // 0->1, 1->0
            actualizarPreview.run();
        });

        Button btnAceptar = ui.newButton("Aplicar");
        btnAceptar.setOnAction(e -> {
            playClick();
            Skins seleccionada = opciones[index[0]];
            UsuarioActual.setSkins(seleccionada);
            System.out.println("Skin aplicada: " + seleccionada);
            getDialogService().showMessageBox("Skin aplicada: " + seleccionada.name());
        });

        Button btnCerrar  = ui.newButton("Cerrar");

        root.getChildren().addAll(titulo, skinPreview, botones, btnAceptar, btnCerrar);

        getDialogService().showBox(
                "Skins",
                root,
                btnCerrar
        );
    }


    //Boton que cierra la cuenta de usuario
    private void cerrarCuenta()
    {
        UsuarioActual.setUsuarioActual(null);
    }

    //Metodo para verificar si hay una cuenta activa
    private boolean verificarExistenciaDeUsuario()
    {
        if(UsuarioActual.getUsuarioActual() != null)
        {
            return true;
        }
        return false;
    }
    private void guardarUsuariosEnJSON() {
        try {
            UsuarioFree usuarioFree = null;
            UsuarioPremiun usuarioPremiun = null;

            if(UsuarioActual.getUsuarioPremiunActual() != null)
            {
                if(UsuarioActual.getUsuarioPremiunActual().isPremiunActivado()) {
                    usuarioPremiun = UsuarioActual.getUsuarioPremiunActual();
                    if (usuarioPremiun.getPuntaje() < geti("puntaje"))
                    {
                        usuarioPremiun.setPuntaje(geti("puntaje") + usuarioPremiun.multiPorcentual(geti("puntaje"), usuarioPremiun.getExtraPlusEstadisticas()));
                        usuarioPremiun.setKills(geti("kills"));
                        usuarioPremiun.setRondaMaxima(geti("ronda_actual"));
                    }
                }
            }else if(UsuarioActual.getUsuarioFreeActual() != null)
            {
                usuarioFree = UsuarioActual.getUsuarioFreeActual();
                if (usuarioFree.getPuntaje() > geti("puntaje"))
                {
                    usuarioFree.setPuntaje(geti("puntaje"));
                    usuarioFree.setKills(geti("kills"));
                    usuarioFree.setRondaMaxima(geti("ronda_actual"));
                }
            }

            JSONArray arr = gestoraJSON.serializarListaUsuarios(listaUsuarios.getListaUsuarios());
            OperacionLectoEscritura.grabarJSON("Usuarios.json", arr);

            System.out.println("Usuarios guardados correctamente en usuarios.json");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void playClick()
    {
        play("sonidosUI/ui_click.wav");
    }

    private void playError()
    {
        play("sonidosUI/ui_error.wav");
    }

    private void playChange()
    {

        play("sonidosUI/ui_change.wav");
    }

}


