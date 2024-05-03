package com.jzs.ms_atenciones_medicas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "at_atenciones_medicas")
public class AtencionMedica extends RepresentationModel<AtencionMedica> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime fecha;

    @Column(name = "detalle_atencion")
    @NotBlank(message = "El detalle de la atención no puede estar vacío")
    private String detalleAtencion;

    @Column(name = "centro_salud")
    @NotBlank(message = "El centro de salud no puede estar vacío")
    private String centroSalud;

    @ManyToOne
    @JoinColumn(name = "paciente_rut")
    @NotNull(message = "El paciente no puede ser nulo")
    private Paciente paciente;
    
    public AtencionMedica() {
    }

    public AtencionMedica(LocalDateTime fecha, String detalleAtencion, String centroSalud) {
        this.fecha = fecha;
        this.detalleAtencion = detalleAtencion;
        this.centroSalud = centroSalud;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDetalleAtencion() {
        return detalleAtencion;
    }

    public void setDetalleAtencion(String detalleAtencion) {
        this.detalleAtencion = detalleAtencion;
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
