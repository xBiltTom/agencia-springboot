package com.teleinformatica.spring.app.agencia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teleinformatica.spring.app.agencia.entity.Seguro;
import com.teleinformatica.spring.app.agencia.repository.SeguroRepository;

import jakarta.validation.Valid;

@Controller
public class SeguroController {

    @Autowired
    SeguroRepository seguroRepository;

    @GetMapping("/seguros")
    public String listarSeguros(Model modelo, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Seguro> segurosPage = seguroRepository.findAll(pageable);
        modelo.addAttribute("seguros", segurosPage.getContent());
        modelo.addAttribute("currentPage", segurosPage.getNumber());
        modelo.addAttribute("totalPages", segurosPage.getTotalPages());
        modelo.addAttribute("totalItems", segurosPage.getTotalElements());
        return "seguros/index";
    }

    @GetMapping("/seguros/nuevo")
    public String nuevoSeguro(Model modelo) {
        modelo.addAttribute("seguro", new Seguro());
        return "seguros/create";
    }

    @PostMapping("/seguros/guardar")
    public String guardarSeguro(@Valid Seguro seguro,BindingResult result, RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "seguros/create"; // Retorna a la vista de creación si hay errores de validación
        }
        seguroRepository.save(seguro);
        flash.addFlashAttribute("success", "Seguro registrado correctamente");
        return "redirect:/seguros"; // Redirige a la lista de seguros después de guardar
    }

    @GetMapping("/seguros/editar/{id}")
    public String editarSeguro(@PathVariable("id") Integer id, Model modelo) {
        Seguro seguro = seguroRepository.findById(id).orElse(null);
        if (seguro == null) {
            return "redirect:/seguros"; 
        }
        modelo.addAttribute("seguro", seguro);
        return "seguros/edit";
    }

    @PostMapping("/seguros/eliminar/{id}")
    public String eliminarSeguro(@PathVariable("id") Integer id, RedirectAttributes flash) {
        Seguro seguro = seguroRepository.findById(id).orElse(null);
        if (seguro != null) {
            seguroRepository.delete(seguro);
            flash.addFlashAttribute("success", "Seguro eliminado correctamente");
        } else {
            flash.addFlashAttribute("error", "Seguro no encontrado");
        }
        return "redirect:/seguros"; // Redirige a la lista de seguros después de eliminar
    }
}
