package com.teleinformatica.spring.app.agencia.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente {

    @Id
    @Column(length = 8, nullable = false,unique = true)
    private String id;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String nombre;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String apellido;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank
    @Column(length = 9, nullable = false)
    private String telefono;

    @NotBlank
    @Column(length = 60, nullable = false)
    private String direccion;

    public Cliente(){
        super();
    }

    public Cliente(String id, String nombre, String apellido, String email, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
