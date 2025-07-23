package com.teleinformatica.spring.app.agencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teleinformatica.spring.app.agencia.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado,Integer>{

}
