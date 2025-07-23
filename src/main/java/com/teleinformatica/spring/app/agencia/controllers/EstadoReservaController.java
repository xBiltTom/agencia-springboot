package com.teleinformatica.spring.app.agencia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teleinformatica.spring.app.agencia.entity.EstadoReserva;
import com.teleinformatica.spring.app.agencia.repository.EstadoReservaRepository;

@Controller
public class EstadoReservaController {

    @Autowired
    EstadoReservaRepository estadoReservaRepository;

    @GetMapping("/estados")
    public String listarEstados(Model modelo,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<EstadoReserva> estadoPage = estadoReservaRepository.findAll(pageable);
        modelo.addAttribute("estados", estadoPage.getContent());
        modelo.addAttribute("currentPage", estadoPage.getNumber());
        modelo.addAttribute("totalPages", estadoPage.getTotalPages());
        modelo.addAttribute("totalItems", estadoPage.getTotalElements());
        return "estados-reserva/index";
    }

    @GetMapping("/estados/nuevo")
    public String nuevoEstado(Model modelo) {
        EstadoReserva estadoReserva = new EstadoReserva();
        modelo.addAttribute("estado", estadoReserva);
        return "estados-reserva/create";
    }

    @PostMapping("/estados/guardar")
    public String guardarEstado(EstadoReserva estadoReserva, RedirectAttributes flash) {
        estadoReservaRepository.save(estadoReserva);
        flash.addFlashAttribute("success", "Estado de reserva registrado correctamente");
        return "redirect:/estados";
    }

    @GetMapping("/estados/editar/{id}")
    public String editarEstado(@PathVariable("id") Integer id, Model modelo) {
        EstadoReserva estadoReserva = estadoReservaRepository.findById(id).orElse(null);
        if (estadoReserva == null) {
            return "redirect:/estados"; // Redirige si el estado no existe
        }
        modelo.addAttribute("estado", estadoReserva);
        return "estados-reserva/edit";
    }
}
