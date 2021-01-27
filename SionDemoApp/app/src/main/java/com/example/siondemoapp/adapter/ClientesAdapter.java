package com.example.siondemoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.siondemoapp.R;
import com.example.siondemoapp.models.Cliente;
import com.example.siondemoapp.models.RespuestaCliente;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesAdapterVH> implements View.OnClickListener {

    //private RespuestaCliente respuestaCliente;
    private List<Cliente> clienteList;
    private Context context;
    private View.OnClickListener listener;

    public ClientesAdapter() {
    }

    public void setData(List<Cliente> clienteList){
        this.clienteList = clienteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_clientes, parent, false);
        view.setOnClickListener(this);
        //return  new ClientesAdapter.ClientesAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_clientes, parent, false));
        return new ClientesAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesAdapterVH holder, int position) {
        Cliente cliente = clienteList.get(position);
        String nombre = cliente.getNombre();
        String apellidos = cliente.getApellidos();
        String edad = cliente.getEdad();
        String nacionalidad = cliente.getNacionalidad();
        String correo = cliente.getCorreo();

        holder.nombre.setText(nombre + " " + apellidos);
        holder.edadNac.setText(edad + " años, " + nacionalidad);
        holder.correo.setText(correo);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }


    public class ClientesAdapterVH extends RecyclerView.ViewHolder {
        TextView nombre;
        TextView edadNac;
        TextView correo;

        public ClientesAdapterVH(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txvNombre);
            edadNac = itemView.findViewById(R.id.txvEdadNac);
            correo = itemView.findViewById(R.id.txvCorreo);
        }
    }
}
