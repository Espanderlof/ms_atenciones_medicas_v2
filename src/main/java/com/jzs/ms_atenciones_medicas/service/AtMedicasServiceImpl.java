package com.jzs.ms_atenciones_medicas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;
import com.jzs.ms_atenciones_medicas.repository.AtMedicasRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AtMedicasServiceImpl implements AtMedicasService {

    @Autowired
    private AtMedicasRepository atMedicasRepository;

    @Override
    public List<AtencionMedica> getAllAtencionesMedicas() {
        return atMedicasRepository.findAll();
    }

    @Override
    public AtencionMedica getAtencionMedicaById(Long id) {
        return atMedicasRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atención médica no encontrada con ID: " + id));
    }

    @Override
    public List<AtencionMedica> getAtencionesMedicasByPaciente(Paciente paciente) {
        return atMedicasRepository.findByPaciente(paciente);
    }

    @Override
    public AtencionMedica createAtencionMedica(AtencionMedica atencionMedica) {
        return atMedicasRepository.save(atencionMedica);
    }

    @Override
    public AtencionMedica updateAtencionMedica(Long id, AtencionMedica atencionMedica) {
        AtencionMedica existingAtencionMedica = getAtencionMedicaById(id);
        existingAtencionMedica.setFecha(atencionMedica.getFecha());
        existingAtencionMedica.setDetalleAtencion(atencionMedica.getDetalleAtencion());
        existingAtencionMedica.setCentroSalud(atencionMedica.getCentroSalud());
        existingAtencionMedica.setPaciente(atencionMedica.getPaciente());
        return atMedicasRepository.save(existingAtencionMedica);
    }

    @Override
    public void deleteAtencionMedica(Long id) {
        AtencionMedica atencionMedica = getAtencionMedicaById(id);
        atMedicasRepository.delete(atencionMedica);
    }

    @Override
    public Paciente getPacienteByRut(String rut) {
        return atMedicasRepository.findPacienteByRut(rut);
    }
}
