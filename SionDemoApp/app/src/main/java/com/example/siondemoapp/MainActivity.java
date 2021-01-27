package com.example.siondemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.siondemoapp.adapter.ClientesAdapter;
import com.example.siondemoapp.interfaces.ClientesAPI;
import com.example.siondemoapp.interfaces.UsuariosAPI;
import com.example.siondemoapp.models.Cliente;
import com.example.siondemoapp.models.RespuestaCliente;
import com.example.siondemoapp.models.RespuestaLogin;
import com.example.siondemoapp.models.Usuario;
import com.example.siondemoapp.storage.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String BASE_URL = "http://192.168.42.12";
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private ClientesAdapter clientesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar2);
        recyclerView = findViewById(R.id.rvwClientes);

        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        clientesAdapter = new ClientesAdapter();

        Usuario usuario = SharedPrefManager.getInstance(this).getUsuario();

        GetClientes(usuario.getClaveApi());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_logout:
                        Logout();
                        return true;
                    case R.id.menu_actualizar:
                        GetClientes(usuario.getClaveApi());
                        return true;
                    case R.id.menu_registrar:
                        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).estaLogueado()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void GetClientes(String claveApi){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ClientesAPI clientesAPI = retrofit.create((ClientesAPI.class));
        Call<RespuestaCliente> call = clientesAPI.getClientes(claveApi);
        call.enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                try {
                    if (response.isSuccessful()){
                        RespuestaCliente respuestaCliente = response.body();
                        clientesAdapter.setData(respuestaCliente.getClientes());
                        recyclerView.setAdapter(clientesAdapter);
                        //Toast.makeText(MainActivity.this, "Bien", Toast.LENGTH_SHORT).show();

                        clientesAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), DetalleClienteActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("idCliente", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getIdCliente());
                                intent.putExtra("nombre", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getNombre());
                                intent.putExtra("apellidos", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getApellidos());
                                intent.putExtra("edad", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getEdad());
                                intent.putExtra("nacionalidad", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getNacionalidad());
                                intent.putExtra("correo", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getCorreo());
                                intent.putExtra("ci", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getCi());
                                intent.putExtra("idUsuario", respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getIdUsuario());
                                startActivity(intent);
                                //Toast.makeText(MainActivity.this, "Selección " + respuestaCliente.getClientes().get(recyclerView.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Mal. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                String ca = call.request().toString();
                Toast.makeText(MainActivity.this, ca + " " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void Logout(){
        SharedPrefManager.getInstance(this).Clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}