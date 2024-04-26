package com.jzs.ms_atenciones_medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;
import com.jzs.ms_atenciones_medicas.service.AtMedicasService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atenciones")
public class AtMedicasController {
    @Autowired
    private AtMedicasService atMedicasService;

    @GetMapping
    public CollectionModel<EntityModel<AtencionMedica>> getAllAtencionesMedicas() {
        List<AtencionMedica> atencionesMedicas = atMedicasService.getAllAtencionesMedicas();
        List<EntityModel<AtencionMedica>> atencionesResources = atencionesMedicas.stream()
            .map(atencion -> EntityModel.of(atencion,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(atencion.getId())).withSelfRel()
            ))
            .collect(Collectors.toList());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas());
        CollectionModel<EntityModel<AtencionMedica>> resources = CollectionModel.of(atencionesResources, linkTo.withRel("atenciones"));

        return resources;
    }

    @RequestMapping(value = {"/", "/paciente/"}, method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> handleEmptyRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse(false, "Debe proporcionar el ID."));
    }

    @GetMapping("/{id}")
    public EntityModel<AtencionMedica> getAtencionMedicaById(@Validated @PathVariable(required = false) Long id) {
        if (id == null) {
            throw new AtMedicasNotFoundException("Debe proporcionar el ID de la atención médica.");
        }

        AtencionMedica atencionMedica = atMedicasService.getAtencionMedicaById(id);
        return EntityModel.of(atencionMedica,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas()).withRel("all-atenciones"));
    }

    @GetMapping("/paciente/{pacienteRut}")
    public EntityModel<Paciente> getPacienteByRut(@PathVariable String pacienteRut) {
        Paciente paciente = atMedicasService.getPacienteByRut(pacienteRut);
        if (paciente == null) {
            throw new AtMedicasNotFoundException("No se encontró el paciente con RUT " + pacienteRut);
        }
        return EntityModel.of(paciente,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPacienteByRut(pacienteRut)).withSelfRel());
    }

    @PostMapping
    public EntityModel<AtencionMedica> createAtencionMedica(@Valid @RequestBody AtencionMedica atencionMedica) {
        AtencionMedica nuevaAtencionMedica = atMedicasService.createAtencionMedica(atencionMedica);
        return EntityModel.of(nuevaAtencionMedica,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(nuevaAtencionMedica.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas()).withRel("all-atenciones"));
    }

    @PutMapping("/{id}")
    public EntityModel<AtencionMedica> updateAtencionMedica(@PathVariable Long id, @Valid @RequestBody AtencionMedica atencionMedica) {
        AtencionMedica atencionMedicaActualizada = atMedicasService.updateAtencionMedica(id, atencionMedica);
        return EntityModel.of(atencionMedicaActualizada,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAtencionMedicaById(id)).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllAtencionesMedicas()).withRel("all-atenciones"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAtencionMedica(@PathVariable Long id) {
        atMedicasService.deleteAtencionMedica(id);
        return ResponseEntity.ok(new ErrorResponse(true, "Atención médica eliminada correctamente."));
    }

    static class ErrorResponse {
        private final boolean respuesta;
        private final String message;

        public ErrorResponse(boolean respuesta, String message) {
            this.respuesta = respuesta;
            this.message = message;
        }

        public boolean isRespuesta() {
            return respuesta;
        }

        public String getMessage() {
            return message;
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    static class AtMedicasNotFoundException extends RuntimeException {
        public AtMedicasNotFoundException(String message) {
            super(message);
        }
    }
}
