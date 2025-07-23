package com.teleinformatica.spring.app.agencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleinformatica.spring.app.agencia.entity.ReservaSeguro;

public interface ReservaSeguroRepository extends JpaRepository<ReservaSeguro, Integer> {
    
    List<ReservaSeguro> findByReservaId(Integer id); 
    ReservaSeguro findBySeguroId(Integer seguroId);
}
