package com.jzs.ms_atenciones_medicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/atenciones")
public class AtMedicasController {
    @Autowired
    private AtMedicasService atMedicasService;

    @GetMapping
    public ResponseEntity<List<AtencionMedica>> getAllAtencionesMedicas() {
        List<AtencionMedica> atencionesMedicas = atMedicasService.getAllAtencionesMedicas();
        return ResponseEntity.ok(atencionesMedicas);
    }

    @RequestMapping(value = {"/", "/paciente/"}, method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> handleEmptyRequest() {
        return ResponseEntity.badRequest().body(new ErrorResponse(false, "Debe proporcionar el ID."));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAtencionMedicaById(@Validated @PathVariable(required = false) Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, "Debe proporcionar el ID de la atención médica."));
        }
    
        try {
            AtencionMedica atencionMedica = atMedicasService.getAtencionMedicaById(id);
            return ResponseEntity.ok(atencionMedica);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(false, "No se encontró la atención médica con ID " + id + "."));
        }
    }

    @GetMapping("/paciente/{pacienteRut}")
    public ResponseEntity<?> getPacienteByRut(@PathVariable String pacienteRut) {
        Paciente paciente = atMedicasService.getPacienteByRut(pacienteRut);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(false, "No se encontró el paciente con RUT " + pacienteRut + "."));
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    public ResponseEntity<?> createAtencionMedica(@Valid @RequestBody AtencionMedica atencionMedica) {
        AtencionMedica nuevaAtencionMedica = atMedicasService.createAtencionMedica(atencionMedica);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAtencionMedica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAtencionMedica(@PathVariable Long id, @Valid @RequestBody AtencionMedica atencionMedica) {
        try {
            AtencionMedica atencionMedicaActualizada = atMedicasService.updateAtencionMedica(id, atencionMedica);
            return ResponseEntity.ok(atencionMedicaActualizada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(false, "No se encontró la atención médica con ID " + id + "."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAtencionMedica(@PathVariable Long id) {
        try {
            atMedicasService.deleteAtencionMedica(id);
            return ResponseEntity.ok(new ErrorResponse(true, "Atención médica eliminada correctamente."));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(false, "No se encontró la atención médica con ID " + id + "."));
        }
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
}
