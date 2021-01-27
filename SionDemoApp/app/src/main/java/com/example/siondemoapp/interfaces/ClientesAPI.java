package com.example.siondemoapp.interfaces;

import com.example.siondemoapp.models.Cliente;
import com.example.siondemoapp.models.RespuestaCliente;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientesAPI {
    @GET("/sionweb/clientes")
    public Call<RespuestaCliente> getClientes(@Header("Authorization") String claveApi);

    @PUT("/sionweb/clientes/{id}/")
    public Call<RespuestaCliente> updateCliente(
            @Header("Authorization") String claveApi,
            @Path("id") int idCliente,
            @Body Map<String, String> params);

    @DELETE("/sionweb/clientes/{id}/")
    public Call<RespuestaCliente> eliminarCliente(@Header("Authorization") String claveApi, @Path("id") int idCliente);

    @POST("/sionweb/clientes")
    public Call<RespuestaCliente> registrarCliente(@Header("Authorization") String claveApi, @Body Map<String, String> params);
}
