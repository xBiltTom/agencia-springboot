package com.teleinformatica.spring.app.agencia.entity;
import jakarta.persistence.*;

@Entity
public class ReservaSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "seguro_id", nullable = false)
    private Seguro seguro;

    public ReservaSeguro() {
        super();
    }

    public ReservaSeguro(Reserva reserva, Seguro seguro) {
        this.reserva = reserva;
        this.seguro = seguro;
    }

    public Integer getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }
}
