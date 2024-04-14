package com.jzs.ms_atenciones_medicas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "at_pacientes")
public class Paciente {
    @Id
    @Column(name = "rut")
    @NotBlank(message = "El RUT no puede estar vacío")
    private String rut;

    @Column(name = "nombre")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Column(name = "direccion")
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @Column(name = "fecha_nacimiento")
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HistorialMedico> historialMedico;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AtencionMedica> atencionesMedicas;
    
    public Paciente() {
    }

    public Paciente(String rut, String nombre, String direccion, LocalDate fechaNacimiento) {
        this.rut = rut;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<HistorialMedico> getHistorialMedico() {
        return historialMedico;
    }

    public void setHistorialMedico(List<HistorialMedico> historialMedico) {
        this.historialMedico = historialMedico;
    }

    public List<AtencionMedica> getAtencionesMedicas() {
        return atencionesMedicas;
    }

    public void setAtencionesMedicas(List<AtencionMedica> atencionesMedicas) {
        this.atencionesMedicas = atencionesMedicas;
    }
}
