package com.teleinformatica.spring.app.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleinformatica.spring.app.agencia.entity.Seguro;

public interface SeguroRepository extends JpaRepository<Seguro, Integer> {
    // Aquí puedes agregar métodos personalizados si es necesario

}
