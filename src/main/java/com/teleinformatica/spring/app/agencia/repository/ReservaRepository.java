package com.teleinformatica.spring.app.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleinformatica.spring.app.agencia.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario
    
}
