package com.jzs.ms_atenciones_medicas.service;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;

import java.util.List;

public interface AtMedicasService {
    List<AtencionMedica> getAllAtencionesMedicas();
    AtencionMedica getAtencionMedicaById(Long id);
    List<AtencionMedica> getAtencionesMedicasByPaciente(Paciente paciente);
    AtencionMedica createAtencionMedica(AtencionMedica atencionMedica);
    AtencionMedica updateAtencionMedica(Long id, AtencionMedica atencionMedica);
    void deleteAtencionMedica(Long id);
}
