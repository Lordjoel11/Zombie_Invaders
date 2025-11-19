package com.marcosguianfrancojoel.zombie_invaders.Gestores;

import com.marcosguianfrancojoel.zombie_invaders.Clases.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class GestoraJSON {

    // =====================
    // SERIALIZAR
    // =====================

    public JSONObject serializarUsuarioFree(UsuarioFree j) throws JSONException {

        JSONObject obj;
        try{
            obj = new JSONObject();
            obj.put("id", j.getId());
            obj.put("nombre", j.getNombre());
            obj.put("gmail", j.getGmail());
            obj.put("contrasenia", j.getPassword());
            obj.put("puntaje", j.getPuntaje());
            obj.put("kills", j.getKills());
            obj.put("rondaMaxima", j.getRondaMaxima());
        }catch (JSONException e)
        {
            throw new JSONException(e);
        }

        return obj;
    }

    public JSONObject serializarUsuarioPremiun(UsuarioPremiun j) throws JSONException {

        JSONObject obj;
        try{
            obj = new JSONObject();
            obj.put("id", j.getId());
            obj.put("nombre", j.getNombre());
            obj.put("gmail", j.getGmail());
            obj.put("contrasenia", j.getPassword());
            obj.put("puntaje", j.getPuntaje());
            obj.put("kills", j.getKills());
            obj.put("rondaMaxima", j.getRondaMaxima());
            obj.put("plusStats", j.getExtraPlusEstadisticas());
            obj.put("premiun", j.isPremiunActivado());

        }catch (JSONException e)
        {
            throw new JSONException(e);
        }

        return obj;
    }

    public JSONArray serializarListaUsuarios(ArrayList<Usuarios> lista) {
        JSONArray arr = new JSONArray();
        try {
            for (Usuarios u : lista) {

                if (u instanceof UsuarioPremiun){
                    arr.put(serializarUsuarioPremiun((UsuarioPremiun) u));
                }else {
                    arr.put(serializarUsuarioFree((UsuarioFree) u));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }


    // =====================
    // DESERIALIZAR
    // =====================

    public UsuarioFree deserializarUsuarioFree(JSONObject json) throws JSONException {
        UsuarioFree usuarioFree = null;
        try {
            usuarioFree = new UsuarioFree();
            usuarioFree.setId(json.getInt("id"));
            usuarioFree.setNombre(json.getString("nombre"));
            usuarioFree.setGmail(json.getString("gmail"));
            usuarioFree.setPassword(json.getString("contrasenia"));
            usuarioFree.setPuntaje(json.getInt("puntaje"));
            usuarioFree.setKills(json.getInt("kills"));
            usuarioFree.setRondaMaxima(json.getInt("rondaMaxima"));
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return usuarioFree;
    }

    public UsuarioPremiun deserializarUsuarioPremiun(JSONObject json) throws JSONException {
        UsuarioPremiun usuarioPremiun = null;
        try {
            usuarioPremiun = new UsuarioPremiun();
            usuarioPremiun.setId(json.getInt("id"));
            usuarioPremiun.setNombre(json.getString("nombre"));
            usuarioPremiun.setGmail(json.getString("gmail"));
            usuarioPremiun.setPassword(json.getString("contrasenia"));
            usuarioPremiun.setPuntaje(json.getInt("puntaje"));
            usuarioPremiun.setKills(json.getInt("kills"));
            usuarioPremiun.setRondaMaxima(json.getInt("rondaMaxima"));
            usuarioPremiun.setExtraPlusEstadisticas(json.getInt("plusStats"));
            usuarioPremiun.setPremiunActivado(json.getBoolean("premiun"));

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return usuarioPremiun;
    }

    public Usuarios deserializarUsuario(JSONObject json) {
        Usuarios u = null;
        try {
            //Verifica si es premiun o no
            boolean esPremium = json.optBoolean("premiun", false);

            if (esPremium) {
                u = deserializarUsuarioPremiun(json);
            } else {
                u = deserializarUsuarioFree(json);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return u;
    }


    public ArrayList<Usuarios> deserializarListaUsuarios(JSONArray arr) {
        ArrayList<Usuarios> lista = new ArrayList<>();

        try {
            for (int i = 0; i < arr.length(); i++) {

                lista.add(deserializarUsuario(arr.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
