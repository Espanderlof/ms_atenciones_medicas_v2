package com.jzs.ms_atenciones_medicas.service;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;
import com.jzs.ms_atenciones_medicas.repository.AtMedicasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AtMedicasServicelmplTest {

    @Mock
    private AtMedicasRepository atMedicasRepository;

    @InjectMocks
    private AtMedicasServiceImpl atMedicasService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllAtencionesMedicasTest() {
        List<AtencionMedica> atencionesMedicas = Arrays.asList(
                new AtencionMedica(LocalDateTime.now(), "Detalle 1", "Centro 1"),
                new AtencionMedica(LocalDateTime.now(), "Detalle 2", "Centro 2")
        );
        when(atMedicasRepository.findAll()).thenReturn(atencionesMedicas);

        List<AtencionMedica> result = atMedicasService.getAllAtencionesMedicas();

        assertEquals(2, result.size());
        verify(atMedicasRepository, times(1)).findAll();
    }

    @Test
    public void getAtencionMedicaByIdTest() {
        Long id = 1L;
        AtencionMedica atencionMedica = new AtencionMedica(LocalDateTime.now(), "Detalle", "Centro");
        when(atMedicasRepository.findById(id)).thenReturn(Optional.of(atencionMedica));

        AtencionMedica result = atMedicasService.getAtencionMedicaById(id);

        assertNotNull(result);
        assertEquals("Detalle", result.getDetalleAtencion());
        verify(atMedicasRepository, times(1)).findById(id);
    }

    @Test
    public void getAtencionMedicaByIdNotFoundTest() {
        Long id = 1L;
        when(atMedicasRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> atMedicasService.getAtencionMedicaById(id));
        verify(atMedicasRepository, times(1)).findById(id);
    }

    @Test
    public void createAtencionMedicaTest() {
        AtencionMedica atencionMedica = new AtencionMedica(LocalDateTime.now(), "Detalle", "Centro");
        Paciente paciente = new Paciente("12345678-9", "Juan", "Dirección", LocalDate.now());
        atencionMedica.setPaciente(paciente);
        when(atMedicasRepository.save(atencionMedica)).thenReturn(atencionMedica);

        AtencionMedica result = atMedicasService.createAtencionMedica(atencionMedica);

        assertNotNull(result);
        assertEquals("Detalle", result.getDetalleAtencion());
        assertEquals("12345678-9", result.getPaciente().getRut());
        verify(atMedicasRepository, times(1)).save(atencionMedica);
    }

}
