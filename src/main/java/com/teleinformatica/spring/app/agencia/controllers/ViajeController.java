package com.teleinformatica.spring.app.agencia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.teleinformatica.spring.app.agencia.entity.Viaje;
import com.teleinformatica.spring.app.agencia.repository.ViajeRepository;

import jakarta.validation.Valid;


@Controller
public class ViajeController {

    @Autowired
    private ViajeRepository viajeRepository;

    @GetMapping("/viajes")
    private String listarViajes(Model modelo){
        List<Viaje> viajes = viajeRepository.findAll();
        modelo.addAttribute("viajes", viajes);
        return "viajes/index";
    }

    @GetMapping("/viajes/nuevo")
    private String nuevoViaje(Model modelo){
        modelo.addAttribute("viaje", new Viaje());
        return "viajes/create";
    }

    @PostMapping("/viajes/guardar")
    private String guardarViaje(@Valid Viaje viaje,BindingResult result, RedirectAttributes flash){
        if(result.hasErrors()){
            return "viajes";
        }
        viajeRepository.save(viaje);
        flash.addFlashAttribute("success", "Viaje registrado correctamente");
        return "redirect:/viajes";
    }

    @GetMapping("/viajes/editar/{id}")
    private  String editarViaje(@PathVariable("id") Integer id, Model modelo){
        Viaje viaje = viajeRepository.findById(id).orElse(null);
        if (viaje == null) {
            return "redirect:/viajes"; // Redirigir si el viaje no existe
        }
        modelo.addAttribute("viaje", viaje);
        return "viajes/edit";
    }

    @PostMapping("/viajes/eliminar/{id}")
    private String eliminarViaje(@PathVariable("id") Integer id, RedirectAttributes flash) {
        Viaje viaje = viajeRepository.findById(id).orElse(null);
        if (viaje != null) {
            viajeRepository.delete(viaje);
            flash.addFlashAttribute("success", "Viaje eliminado correctamente");
        } else {
            flash.addFlashAttribute("error", "El viaje no existe");
        }
        return "redirect:/viajes";
    }

}
