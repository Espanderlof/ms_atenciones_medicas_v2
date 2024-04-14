package com.jzs.ms_atenciones_medicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;

import java.util.List;

public interface AtMedicasRepository extends JpaRepository<AtencionMedica, Long> {
    List<AtencionMedica> findByPaciente(Paciente paciente);
}
