package com.jzs.ms_atenciones_medicas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;
import com.jzs.ms_atenciones_medicas.repository.AtMedicasRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AtMedicasServiceTest {

    @InjectMocks
    private AtMedicasServiceImpl atMedicasService;

    @Mock
    private AtMedicasRepository atMedicasRepositoryMock;

    @Test
    public void getAllAtencionesMedicasTest() {
        AtencionMedica atencionMedica1 = new AtencionMedica();
        atencionMedica1.setId(1L);
        atencionMedica1.setFecha(LocalDateTime.now());
        atencionMedica1.setDetalleAtencion("Detalle de prueba 1");
        atencionMedica1.setCentroSalud("Centro de prueba 1");

        AtencionMedica atencionMedica2 = new AtencionMedica();
        atencionMedica2.setId(2L);
        atencionMedica2.setFecha(LocalDateTime.now());
        atencionMedica2.setDetalleAtencion("Detalle de prueba 2");
        atencionMedica2.setCentroSalud("Centro de prueba 2");

        List<AtencionMedica> atencionesMedicas = Arrays.asList(atencionMedica1, atencionMedica2);

        when(atMedicasRepositoryMock.findAll()).thenReturn(atencionesMedicas);

        List<AtencionMedica> atencionesMedicasObtenidas = atMedicasService.getAllAtencionesMedicas();

        assertEquals(2, atencionesMedicasObtenidas.size());
        assertEquals("Detalle de prueba 1", atencionesMedicasObtenidas.get(0).getDetalleAtencion());
        assertEquals("Detalle de prueba 2", atencionesMedicasObtenidas.get(1).getDetalleAtencion());
    }

    @Test
    public void getAtencionMedicaByIdTest() {
        Long id = 1L;

        AtencionMedica atencionMedica = new AtencionMedica();
        atencionMedica.setId(id);
        atencionMedica.setFecha(LocalDateTime.now());
        atencionMedica.setDetalleAtencion("Detalle de prueba");
        atencionMedica.setCentroSalud("Centro de prueba");

        when(atMedicasRepositoryMock.findById(id)).thenReturn(java.util.Optional.of(atencionMedica));

        AtencionMedica atencionMedicaObtenida = atMedicasService.getAtencionMedicaById(id);

        assertEquals(id, atencionMedicaObtenida.getId());
        assertEquals("Detalle de prueba", atencionMedicaObtenida.getDetalleAtencion());
        assertEquals("Centro de prueba", atencionMedicaObtenida.getCentroSalud());
    }

    @Test
    public void createAtencionMedicaTest() {
        AtencionMedica atencionMedica = new AtencionMedica();
        atencionMedica.setFecha(LocalDateTime.now());
        atencionMedica.setDetalleAtencion("Detalle de prueba");
        atencionMedica.setCentroSalud("Centro de prueba");

        when(atMedicasRepositoryMock.save(any(AtencionMedica.class))).thenReturn(atencionMedica);

        AtencionMedica atencionMedicaCreada = atMedicasService.createAtencionMedica(atencionMedica);

        assertEquals("Detalle de prueba", atencionMedicaCreada.getDetalleAtencion());
        assertEquals("Centro de prueba", atencionMedicaCreada.getCentroSalud());
    }

    @Test
    public void getPacienteByRutTest() {
        String rut = "12345678-9";

        Paciente paciente = new Paciente();
        paciente.setRut(rut);
        paciente.setNombre("Juan");
        paciente.setDireccion("Calle Principal 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(atMedicasRepositoryMock.findPacienteByRut(rut)).thenReturn(paciente);

        Paciente pacienteObtenido = atMedicasService.getPacienteByRut(rut);

        assertEquals(rut, pacienteObtenido.getRut());
        assertEquals("Juan", pacienteObtenido.getNombre());
        assertEquals("Calle Principal 123", pacienteObtenido.getDireccion());
        assertEquals(LocalDate.of(1990, 1, 1), pacienteObtenido.getFechaNacimiento());
    }
}