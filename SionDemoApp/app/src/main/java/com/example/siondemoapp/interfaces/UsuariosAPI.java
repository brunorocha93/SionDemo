package com.example.siondemoapp.interfaces;

import com.example.siondemoapp.models.RespuestaLogin;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuariosAPI {

    @POST("/sionWeb/usuarios/login/")
    public Call<RespuestaLogin> login(@Body Map<String, String> params);
    //public Call<Map<String, String>> login(@Body Map<String, String> params);
    //public Call<Usuarios> login(@FieldMap Map<String, String> params);
//    public Call<Usuarios> login(@Field("correo") String correo, @Field("contrasena") String contrasena);
}
