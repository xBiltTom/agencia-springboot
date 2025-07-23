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
import com.teleinformatica.spring.app.agencia.entity.Empleado;
import com.teleinformatica.spring.app.agencia.repository.EmpleadoRepository;

import jakarta.validation.Valid;

@Controller
public class EmpleadoController {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @GetMapping("/empleados")
    public String listarEmpleados(Model modelo,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Empleado> empleadosPage = empleadoRepository.findAll(pageable);

        modelo.addAttribute("empleados", empleadosPage.getContent());
        modelo.addAttribute("currentPage", empleadosPage.getNumber());
        modelo.addAttribute("totalPages", empleadosPage.getTotalPages());
        modelo.addAttribute("totalItems", empleadosPage.getTotalElements());

        return "empleados/index"; 
    }

    @GetMapping("/empleados/nuevo")
    public String nuevoEmpleado(Model modelo){
        Empleado empleado = new Empleado();
        modelo.addAttribute("empleado", empleado); // Agrega un nuevo objeto Empleado al modelo
        return "empleados/create"; // Retorna el nombre de la vista para registrar un nuevo empleado
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "empleados/create";
        }
        empleadoRepository.save(empleado);
        flash.addFlashAttribute("success", "Empleado registrado correctamente");
        return "redirect:/empleados";
    }

    @GetMapping("/empleados/editar/{id}")
    public String editarEmpleado(@PathVariable("id") Integer id,Model modelo){
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado == null) {
            return "redirect:/empleados"; // Redirige si el empleado no existe
        }
        modelo.addAttribute("empleado", empleado);
        return "empleados/edit"; // Retorna el nombre de la vista para editar un empleado
    }

    @PostMapping("/empleados/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Integer id, RedirectAttributes flash){
        Empleado empleado = empleadoRepository.findById(id).orElse(null); 
        empleadoRepository.delete(empleado);
        flash.addFlashAttribute("success", "Empleado eliminado correctamente");
        return "redirect:/empleados";
    }
}
