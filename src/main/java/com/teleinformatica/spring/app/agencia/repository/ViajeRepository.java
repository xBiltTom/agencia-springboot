package com.teleinformatica.spring.app.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleinformatica.spring.app.agencia.entity.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje,Integer> {

}
