package com.example.siondemoapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siondemoapp.interfaces.ClientesAPI;
import com.example.siondemoapp.models.Cliente;
import com.example.siondemoapp.models.RespuestaCliente;
import com.example.siondemoapp.models.Usuario;
import com.example.siondemoapp.storage.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private static String BASE_URL = "http://192.168.42.12";
    EditText etNombre, etApellidos, etEdad, etNacionalidad, etCorreo, etCI;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.edtNombreRegistro);
        etApellidos = findViewById(R.id.edtApellidosRegistro);
        etEdad = findViewById(R.id.edtEdadRegistro);
        etNacionalidad = findViewById(R.id.edtNacionalidadRegistro);
        etCorreo = findViewById(R.id.edtCorreoRegistro);
        etCI = findViewById(R.id.edtCI);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();
                String nacionalidad = etNacionalidad.getText().toString().trim();
                String email = etCorreo.getText().toString().trim();
                String ci = etCI.getText().toString().trim();
                if (email.isEmpty()){
                    etCorreo.setError("Correo es requerido");
                    etCorreo.requestFocus();
                    return;
                }
                if (!isValidEmail(email)){
                    etCorreo.setError("Correo no válido");
                    etCorreo.requestFocus();
                    return;
                }

                if (nombre.isEmpty()){
                    etNombre.setError("Nombre es requerido");
                    etNombre.requestFocus();
                    return;
                }
                if (apellidos.isEmpty()){
                    etApellidos.setError("Apellidos es requerido");
                    etApellidos.requestFocus();
                    return;
                }
                if (edad.isEmpty()){
                    etEdad.setError("Edad es requerido");
                    etEdad.requestFocus();
                    return;
                }
                if (nacionalidad.isEmpty()){
                    etNacionalidad.setError("Nacionalidad es requerido");
                    etNacionalidad.requestFocus();
                    return;
                }
                if (ci.isEmpty()){
                    etCI.setError("CI es requerido");
                    etCI.requestFocus();
                    return;
                }
                Usuario usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();

                Cliente cliente = new Cliente(
                        nombre,
                        apellidos,
                        edad,
                        email,
                        nacionalidad,
                        ci,
                        usuario.getIdUsuario()
                );


                RegistrarCliente(cliente, usuario.getClaveApi());
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void RegistrarCliente(Cliente cliente, String claveApi){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ClientesAPI clientesAPI = retrofit.create((ClientesAPI.class));

        Map<String, String> params = new HashMap<>();
        params.put("nombre", cliente.getNombre());
        params.put("apellidos", cliente.getApellidos());
        params.put("edad", cliente.getEdad());
        params.put("nacionalidad", cliente.getNacionalidad());
        params.put("correo", cliente.getCorreo());
        params.put("ci", cliente.getCi());

        Call<RespuestaCliente> call = clientesAPI.registrarCliente(claveApi, params);
        call.enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(RegistroActivity.this, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}