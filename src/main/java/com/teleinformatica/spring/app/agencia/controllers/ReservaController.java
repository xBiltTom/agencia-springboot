package com.teleinformatica.spring.app.agencia.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
import com.teleinformatica.spring.app.agencia.entity.Reserva;
import com.teleinformatica.spring.app.agencia.entity.ReservaSeguro;
import com.teleinformatica.spring.app.agencia.entity.Seguro;
import com.teleinformatica.spring.app.agencia.repository.ClienteRepository;
import com.teleinformatica.spring.app.agencia.repository.EmpleadoRepository;
import com.teleinformatica.spring.app.agencia.repository.EstadoReservaRepository;
import com.teleinformatica.spring.app.agencia.repository.ReservaRepository;
import com.teleinformatica.spring.app.agencia.repository.ReservaSeguroRepository;
import com.teleinformatica.spring.app.agencia.repository.SeguroRepository;
import com.teleinformatica.spring.app.agencia.repository.ViajeRepository;

import jakarta.validation.Valid;

@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    EstadoReservaRepository estadoReservaRepository;

    @Autowired
    ViajeRepository viajeRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SeguroRepository seguroRepository;

    @Autowired
    ReservaSeguroRepository reservaSeguroRepository;

    @GetMapping("/reservas")
    public String listarReservas(Model modelo,  @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Reserva> reservasPage = reservaRepository.findAll(pageable);
        modelo.addAttribute("reservas", reservasPage.getContent());
        modelo.addAttribute("currentPage", reservasPage.getNumber());
        modelo.addAttribute("totalPages", reservasPage.getTotalPages());
        modelo.addAttribute("totalItems", reservasPage.getTotalElements());
        return "reservas/index"; 
    }

    @GetMapping("/reservas/nuevo")
    public String nuevoReserva(Model modelo) {
        modelo.addAttribute("reserva", new Reserva());
        modelo.addAttribute("viajes", viajeRepository.findAll());
        modelo.addAttribute("empleados", empleadoRepository.findAll());
        modelo.addAttribute("clientes", clienteRepository.findAll());
        modelo.addAttribute("estados", estadoReservaRepository.findAll());
        modelo.addAttribute("seguros", seguroRepository.findAll());
        return "reservas/create"; 
    }

    @PostMapping("/reservas/guardar")
    public String guardarReserva(@Valid Reserva reserva, 
                                @RequestParam(required = false) List<Integer> segurosIds,
                                BindingResult result, 
                                RedirectAttributes flash) {
        reservaRepository.save(reserva);
        
        if (segurosIds != null && !segurosIds.isEmpty()) {
            for (Integer seguroId : segurosIds) {
                Seguro seguro = seguroRepository.findById(seguroId).orElse(null);
                if (seguro != null) {
                    ReservaSeguro reservaSeguro = new ReservaSeguro();
                    reservaSeguro.setReserva(reserva);
                    reservaSeguro.setSeguro(seguro);
                    reservaSeguroRepository.save(reservaSeguro);
                }
            }
        }
        
        flash.addFlashAttribute("success", "Reserva registrada/actualizada correctamente");
        return "redirect:/reservas";
    }

    @GetMapping("/reservas/detalle/{id}")
    public String editarReserva(@PathVariable("id") Integer id, Model modelo) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva == null) {
            return "redirect:/reservas"; 
        }
        List<Seguro> segurosReserva = reservaSeguroRepository.findByReservaId(id)
            .stream()
            .map(ReservaSeguro::getSeguro)
            .collect(Collectors.toList());
        reserva.setSeguros(segurosReserva); 
        modelo.addAttribute("reserva", reserva);
        modelo.addAttribute("viajes", viajeRepository.findAll());
        modelo.addAttribute("empleados", empleadoRepository.findAll());
        modelo.addAttribute("clientes", clienteRepository.findAll());
        modelo.addAttribute("estados", estadoReservaRepository.findAll());
        modelo.addAttribute("seguros", seguroRepository.findAll());
        return "reservas/edit"; 
    }
}
