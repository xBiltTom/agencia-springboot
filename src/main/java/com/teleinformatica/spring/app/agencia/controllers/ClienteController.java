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

import com.teleinformatica.spring.app.agencia.entity.Cliente;
import com.teleinformatica.spring.app.agencia.repository.ClienteRepository;

import jakarta.validation.Valid;

@Controller
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public String listarClientes(Model modelo,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Cliente> clientesPage = clienteRepository.findAll(pageable);
        
        modelo.addAttribute("clientes", clientesPage.getContent());
        modelo.addAttribute("currentPage", clientesPage.getNumber());
        modelo.addAttribute("totalPages", clientesPage.getTotalPages());
        modelo.addAttribute("totalItems", clientesPage.getTotalElements());
        return "clientes/index";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(Model modelo){
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente);
        return "clientes/create";
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(@Valid Cliente cliente,BindingResult result, RedirectAttributes flash){
        if(result.hasErrors()){
            return "clientes/create";
        }
        clienteRepository.save(cliente);
        flash.addFlashAttribute("success", "Cliente registrado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/editar/{dni}")
    public String editarCliente(@PathVariable("dni") String dni,Model modelo){
        Cliente cliente = clienteRepository.findById(dni).orElse(null);
        modelo.addAttribute("cliente",cliente);
        return "clientes/edit";
    }

    @PostMapping("/clientes/eliminar/{dni}")
    public String eliminarCliente(@PathVariable("dni") String dni, RedirectAttributes flash){
        Cliente cliente = clienteRepository.findById(dni).orElse(null); 
        clienteRepository.delete(cliente);
        flash.addFlashAttribute("success", "Cliente eliminado correctamente");
        return "redirect:/clientes";
    }

}
