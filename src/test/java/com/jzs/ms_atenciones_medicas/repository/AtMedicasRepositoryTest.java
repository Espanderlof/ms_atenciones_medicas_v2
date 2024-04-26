package com.jzs.ms_atenciones_medicas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.HistorialMedico;
import com.jzs.ms_atenciones_medicas.model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AtMedicasRepositoryTest {

    @Autowired
    private AtMedicasRepository atMedicasRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void guardarAtencionMedicaTest() {
        Paciente paciente = new Paciente();
        paciente.setRut("12345678-9");
        paciente.setNombre("Juan");
        paciente.setDireccion("Calle Principal 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setDetalleHistorial("Detalle del historial");
        historialMedico.setFechaIngresoHistorial(LocalDateTime.now());
        historialMedico.setPaciente(paciente);

        paciente.setHistorialMedico(Arrays.asList(historialMedico));

        entityManager.persist(paciente);

        AtencionMedica atencionMedica = new AtencionMedica();
        atencionMedica.setFecha(LocalDateTime.now());
        atencionMedica.setDetalleAtencion("Detalle de prueba");
        atencionMedica.setCentroSalud("Centro de prueba");
        atencionMedica.setPaciente(paciente);

        AtencionMedica atencionMedicaGuardada = atMedicasRepository.save(atencionMedica);

        assertNotNull(atencionMedicaGuardada.getId());
        assertEquals(atencionMedica.getFecha(), atencionMedicaGuardada.getFecha());
        assertEquals("Detalle de prueba", atencionMedicaGuardada.getDetalleAtencion());
        assertEquals("Centro de prueba", atencionMedicaGuardada.getCentroSalud());
        assertEquals(paciente.getRut(), atencionMedicaGuardada.getPaciente().getRut());
    }

    @Test
    public void buscarPacientePorRutTest() {
        String rut = "12345678-9";

        Paciente paciente = new Paciente();
        paciente.setRut(rut);
        paciente.setNombre("Juan");
        paciente.setDireccion("Calle Principal 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        HistorialMedico historialMedico = new HistorialMedico();
        historialMedico.setDetalleHistorial("Detalle del historial");
        historialMedico.setFechaIngresoHistorial(LocalDateTime.now());
        historialMedico.setPaciente(paciente);

        paciente.setHistorialMedico(Arrays.asList(historialMedico));

        entityManager.persist(paciente);

        AtencionMedica atencionMedica = new AtencionMedica();
        atencionMedica.setFecha(LocalDateTime.now());
        atencionMedica.setDetalleAtencion("Detalle de prueba");
        atencionMedica.setCentroSalud("Centro de prueba");
        atencionMedica.setPaciente(paciente);

        atMedicasRepository.save(atencionMedica);

        Paciente pacienteEncontrado = atMedicasRepository.findPacienteByRut(rut);

        assertNotNull(pacienteEncontrado);
        assertEquals(rut, pacienteEncontrado.getRut());
        assertEquals("Juan", pacienteEncontrado.getNombre());
        assertEquals("Calle Principal 123", pacienteEncontrado.getDireccion());
        assertEquals(LocalDate.of(1990, 1, 1), pacienteEncontrado.getFechaNacimiento());
    }
}