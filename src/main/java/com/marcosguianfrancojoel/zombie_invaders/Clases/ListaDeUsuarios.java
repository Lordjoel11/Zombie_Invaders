package com.marcosguianfrancojoel.zombie_invaders.Clases;

import com.marcosguianfrancojoel.zombie_invaders.Exepciones.UsuarioNoEncontradoException;
import com.marcosguianfrancojoel.zombie_invaders.Exepciones.UsuarioYaExisteException;
import com.marcosguianfrancojoel.zombie_invaders.Gestores.GestoraJSON;
import org.json.JSONArray;

import java.util.*;

/*
List<Usuarios>:
Se usa como coleccion principal de usuarios (listaUsuarios).
 Permite mantener el orden de insercion, recorrerlos para mostrar ranking,
 puntajes y estadisticas, y acceder por indice. Elegimos ArrayList como
 implementacion porque nos interesa la eficiencia al recorrer y
 no necesitamos muchas inserciones en el medio.

Set<String>:
Usamos un HashSet (gmailsRegistrados) para almacenar
los correos electronicos de todos los usuarios registrados.
El objetivo es garantizar que no haya gmails repetidos y
 poder validar rapidamente si un correo ya fue utilizado al
 momento de registrar una nueva cuenta.

Map<String, Usuarios>:
Usamos un HashMap (usuariosPorGmail) para hacer
búsquedas rapidas de usuarios por su gmail (por ejemplo, en el login).
La clave es el correo (String) y el valor el objeto Usuarios. De esta
forma evitamos recorrer toda la lista de usuarios cada vez que queremos
verificar un login.
 */

public class ListaDeUsuarios {

    private List<Usuarios> listaUsuarios = new ArrayList<>();

    private Set<String> gmailsRegistrados = new HashSet<>();

    private Map<String, Usuarios> usuariosPorGmail = new HashMap<>();

    public ListaDeUsuarios() {
    }

    public void cargarDesdeJSON(JSONArray arr, GestoraJSON gjson) {

        List<Usuarios> cargados = gjson.deserializarListaUsuarios(arr);

        listaUsuarios.clear();
        gmailsRegistrados.clear();
        usuariosPorGmail.clear();

        for (Usuarios u : cargados) {
            registrarUsuarioInterno(u);
        }

        actualizarContador();
    }

    private void registrarUsuarioInterno(Usuarios u) {
        listaUsuarios.add(u);
        gmailsRegistrados.add(u.getGmail());
        usuariosPorGmail.put(u.getGmail(), u);
    }

    public void registrarUsuario(Usuarios u) throws UsuarioYaExisteException {
        if (existeGmail(u.getGmail())) {
            throw new UsuarioYaExisteException("Ya existe un usuario con el gmail: " + u.getGmail());
        }
        registrarUsuarioInterno(u);
    }

    public void actualizarContador() {
        int max = 0;
        for (Usuarios u : listaUsuarios) {
            if (u.getId() > max) max = u.getId();
        }
        Usuarios.setContador(max + 1);
    }

    public void add(Usuarios u) {
        registrarUsuarioInterno(u);
    }

    public void remover(Usuarios u) {
        if (u == null) return;
        listaUsuarios.remove(u);
        gmailsRegistrados.remove(u.getGmail());
        usuariosPorGmail.remove(u.getGmail());
    }


    public boolean existeGmail(String gmail) {
        return gmailsRegistrados.contains(gmail);
    }

    public Usuarios buscarPorGmail(String gmail) {
        Usuarios u = usuariosPorGmail.get(gmail);
        if (u == null) {
            System.out.println("No se encontró al Usuario Gmail: " + gmail);
        }
        return u;
    }

    public Usuarios obtenerPorGmail(String gmail) throws UsuarioNoEncontradoException {
        Usuarios u = usuariosPorGmail.get(gmail);
        if (u == null) {
            throw new UsuarioNoEncontradoException("No existe un usuario con ese gmail.");
        }
        return u;
    }

    public Usuarios buscarID(int idUsuario) {
        for (Usuarios aux : listaUsuarios) {
            if (aux.getId() == idUsuario) {
                return aux;
            }
        }
        System.out.println("No se encontró al Usuario ID: " + idUsuario);
        return null;
    }

    public Usuarios removerUsuario(String gmail) {
        Usuarios usuario = buscarPorGmail(gmail);
        if (usuario != null) {
            remover(usuario);
        } else {
            System.out.println("No se encontró al Usuario Gmail: " + gmail);
        }
        return usuario;
    }

    public Usuarios actualizarContrasenia(String gmail, String contrasenia) {
        Usuarios usuario = buscarPorGmail(gmail);
        if (usuario != null) {
            usuario.setPassword(contrasenia);
        } else {
            System.out.println("No se actualizó la contraseña del Usuario Gmail: " + gmail);
        }
        return usuario;
    }

    public ArrayList<Usuarios> getListaUsuarios() {
        return new ArrayList<>(listaUsuarios);
    }
}
