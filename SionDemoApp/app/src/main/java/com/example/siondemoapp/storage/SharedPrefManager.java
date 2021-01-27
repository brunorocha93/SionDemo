package com.example.siondemoapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.siondemoapp.models.Usuario;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "shared_pref";
    private static SharedPrefManager instancia;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (instancia == null){
            instancia = new SharedPrefManager(context);
        }
        return instancia;
    }

    public void GuardarUsuario(Usuario usuario){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("idUsuario", usuario.getIdUsuario());
        editor.putString("nombre", usuario.getNombre());
        editor.putString("apellidos", usuario.getApellidos());
        editor.putString("correo", usuario.getCorreo());
        editor.putString("claveApi", usuario.getClaveApi());

        editor.apply();
    }

    public Boolean estaLogueado(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getInt("idUsuario", 0) > 0){
            return true;
        }
        return false;
    }

    public Usuario getUsuario(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Usuario usuario = new Usuario(
                sharedPreferences.getInt("idUsuario", 0),
                sharedPreferences.getString("nombre", null),
                sharedPreferences.getString("apellidos", null),
                sharedPreferences.getString("correo", null),
                sharedPreferences.getString("claveApi", null)
        );
        return usuario;
    }

    public void Clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
