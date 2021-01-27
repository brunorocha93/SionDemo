package com.example.siondemoapp.models;

import java.util.List;

public class RespuestaCliente {
    private boolean estado;
    private String mensaje;
    private List<Cliente> clientes;

    public RespuestaCliente() {
    }

    public RespuestaCliente(boolean estado, String mensaje, List<Cliente> clientes) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.clientes = clientes;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
