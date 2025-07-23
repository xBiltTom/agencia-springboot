package com.teleinformatica.spring.app.agencia.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "empleado_id",nullable = false)
    private Empleado empleado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "estado_id",nullable = false)
    private EstadoReserva estadoReserva;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "viaje_id",nullable = false)
    private Viaje viaje;

    public Reserva(){
        super();
    }

    public Reserva(Date fechaRerva, Cliente cliente, Empleado empleado, EstadoReserva estadoReserva, Viaje viaje) {
        this.fechaReserva = fechaRerva;
        this.cliente = cliente;
        this.empleado = empleado;
        this.estadoReserva = estadoReserva;
        this.viaje = viaje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
