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

import com.example.siondemoapp.interfaces.UsuariosAPI;
import com.example.siondemoapp.models.RespuestaLogin;
import com.example.siondemoapp.storage.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static String BASE_URL = "http://192.168.42.12";
    private EditText edtEmail;
    private EditText edtContrasena;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.txtEmail);
        edtContrasena = findViewById(R.id.txtContrasena);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String contrasena = edtContrasena.getText().toString().trim();

                if (email.isEmpty()){
                    edtEmail.setError("Correo es requerido");
                    edtEmail.requestFocus();
                    return;
                }
                if (!isValidEmail(email)){
                    edtEmail.setError("Correo no válido");
                    edtEmail.requestFocus();
                    return;
                }

                if (contrasena.isEmpty()){
                    edtContrasena.setError("Contraseña es requerido");
                    edtContrasena.requestFocus();
                    return;
                }
                Login(email, contrasena);
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).estaLogueado()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void Login(String email, String contrasena){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        UsuariosAPI usuariosAPI = retrofit.create(UsuariosAPI.class);

        Map<String, String> params = new HashMap<>();
        params.put("correo", email);
        params.put("contrasena", contrasena);

        Call<RespuestaLogin> call = usuariosAPI.login(params);
        call.enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                try {
                    if(response.isSuccessful()){
                        RespuestaLogin respuesta = response.body();
                        String mensaje = respuesta.getMensaje();
                        if (respuesta.getEstado()){
                            SharedPrefManager.getInstance(LoginActivity.this).GuardarUsuario(respuesta.getUsuario());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                String llamada = call.request().toString();
                Toast.makeText(LoginActivity.this, call.request() + " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}