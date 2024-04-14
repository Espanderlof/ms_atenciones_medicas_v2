package com.jzs.ms_atenciones_medicas.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "at_historial_medico")
@JsonIdentityReference(alwaysAsId = true)
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @Column(name = "detalle_historial")
    @NotBlank(message = "El detalle del historial no puede estar vacío")
    private String detalleHistorial;

    @Column(name = "fecha_ingreso_historial")
    @NotNull(message = "La fecha de ingreso del historial no puede ser nula")
    private LocalDateTime fechaIngresoHistorial;

    @ManyToOne
    @JoinColumn(name = "paciente_rut")
    @NotNull(message = "El paciente no puede ser nulo")
    private Paciente paciente;

    public HistorialMedico(String detalleHistorial, LocalDateTime fechaIngresoHistorial) {
        this.detalleHistorial = detalleHistorial;
        this.fechaIngresoHistorial = fechaIngresoHistorial;
    }

    public Long getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(Long idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getDetalleHistorial() {
        return detalleHistorial;
    }

    public void setDetalleHistorial(String detalleHistorial) {
        this.detalleHistorial = detalleHistorial;
    }

    public LocalDateTime getFechaIngresoHistorial() {
        return fechaIngresoHistorial;
    }

    public void setFechaIngresoHistorial(LocalDateTime fechaIngresoHistorial) {
        this.fechaIngresoHistorial = fechaIngresoHistorial;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
