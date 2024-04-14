package com.jzs.ms_atenciones_medicas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;

import java.util.List;

public interface AtMedicasRepository extends JpaRepository<AtencionMedica, Long> {
    List<AtencionMedica> findByPaciente(Paciente paciente);

    @Query("SELECT p FROM Paciente p WHERE p.rut = :rut")
    Paciente findPacienteByRut(@Param("rut") String rut);
}
