package com.example.siondemoapp.models;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String edad;
    private String correo;
    private String nacionalidad;
    private String ci;
    private int idUsuario;

    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String apellidos, String edad, String correo, String nacionalidad, String ci, int idUsuario) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correo = correo;
        this.nacionalidad = nacionalidad;
        this.ci = ci;
        this.idUsuario = idUsuario;
    }

    public Cliente(String nombre, String apellidos, String edad, String correo, String nacionalidad, String ci, int idUsuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.correo = correo;
        this.nacionalidad = nacionalidad;
        this.ci = ci;
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
