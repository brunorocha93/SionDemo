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

public class DetalleClienteActivity extends AppCompatActivity {
    private static String BASE_URL = "http://192.168.42.12";
    EditText etNombre, etApellidos, etEdad, etNacionalidad, etCorreo;
    Button btnEditar, btnEliminar;
    int idCliente = 0;
    String ci = "";
    int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        etNombre = findViewById(R.id.edtNombre);
        etApellidos = findViewById(R.id.edtApellidos);
        etEdad = findViewById(R.id.edtEdad);
        etNacionalidad = findViewById(R.id.edtNacionalidad);
        etCorreo = findViewById(R.id.edtCorreo);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);



        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idCliente = extras.getInt("idCliente");
            idUsuario = extras.getInt("idCliente");
            ci = extras.getString("ci");
            etNombre.setText(extras.getString("nombre"));
            etApellidos.setText(extras.getString("apellidos"));
            etEdad.setText(extras.getString("edad"));
            etNacionalidad.setText(extras.getString("nacionalidad"));
            etCorreo.setText(extras.getString("correo"));
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String apellidos = etApellidos.getText().toString().trim();
                String edad = etEdad.getText().toString().trim();
                String nacionalidad = etNacionalidad.getText().toString().trim();
                String email = etCorreo.getText().toString().trim();

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
                    etNombre.setError("Contraseña es requerido");
                    etNombre.requestFocus();
                    return;
                }
                if (apellidos.isEmpty()){
                    etApellidos.setError("Contraseña es requerido");
                    etApellidos.requestFocus();
                    return;
                }
                if (edad.isEmpty()){
                    etEdad.setError("Contraseña es requerido");
                    etEdad.requestFocus();
                    return;
                }
                if (nacionalidad.isEmpty()){
                    etNacionalidad.setError("Contraseña es requerido");
                    etNacionalidad.requestFocus();
                    return;
                }
                Cliente cliente = new Cliente(
                        idCliente,
                        nombre,
                        apellidos,
                        edad,
                        email,
                        nacionalidad,
                        ci,
                        idUsuario
                );

                Usuario usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
                ActualizarCliente(cliente, usuario.getClaveApi());
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
                EliminarCliente(idCliente, usuario.getClaveApi());
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void ActualizarCliente(Cliente cliente, String claveApi){
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

        Call<RespuestaCliente> call = clientesAPI.updateCliente(claveApi, cliente.getIdCliente(), params);
        call.enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(DetalleClienteActivity.this, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(DetalleClienteActivity.this, "Mal", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                Toast.makeText(DetalleClienteActivity.this, "Mal. " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void EliminarCliente(int clienteId, String claveApi){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ClientesAPI clientesAPI = retrofit.create((ClientesAPI.class));
        Call<RespuestaCliente> call = clientesAPI.eliminarCliente(claveApi, clienteId);
        call.enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(DetalleClienteActivity.this, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(DetalleClienteActivity.this, "Mal", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {

            }
        });

    }
}