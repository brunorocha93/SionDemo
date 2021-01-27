package com.example.siondemoapp.models;

public class RespuestaLogin {
    private boolean estado;
    private  String mensaje;
    private Usuario usuario;

    public RespuestaLogin() {
    }

    public RespuestaLogin(boolean estado, String mensaje, Usuario usuario) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    public boolean getEstado() {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
