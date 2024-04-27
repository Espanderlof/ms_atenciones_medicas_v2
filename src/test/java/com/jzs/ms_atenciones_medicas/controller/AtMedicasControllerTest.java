package com.jzs.ms_atenciones_medicas.controller;

import com.jzs.ms_atenciones_medicas.model.AtencionMedica;
import com.jzs.ms_atenciones_medicas.model.Paciente;
import com.jzs.ms_atenciones_medicas.service.AtMedicasService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(AtMedicasController.class)
public class AtMedicasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtMedicasService atMedicasServiceMock;

    @Test
    public void getAllAtencionesMedicasTest() throws Exception {
        AtencionMedica atencionMedica1 = new AtencionMedica(LocalDateTime.now(), "Detalle 1", "Centro 1");
        atencionMedica1.setId(1L);
        AtencionMedica atencionMedica2 = new AtencionMedica(LocalDateTime.now(), "Detalle 2", "Centro 2");
        atencionMedica2.setId(2L);
        List<AtencionMedica> atencionesMedicas = Arrays.asList(atencionMedica1, atencionMedica2);
    
        when(atMedicasServiceMock.getAllAtencionesMedicas()).thenReturn(atencionesMedicas);
    
        mockMvc.perform(MockMvcRequestBuilders.get("/atenciones"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.atencionMedicaList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.atencionMedicaList[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.atencionMedicaList[0].detalleAtencion", Matchers.is("Detalle 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.atencionMedicaList[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.atencionMedicaList[1].detalleAtencion", Matchers.is("Detalle 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.atenciones.href", Matchers.endsWith("/atenciones")));
    }

    @Test
    public void getAtencionMedicaByIdTest() throws Exception {
        Long id = 1L;
        AtencionMedica atencionMedica = new AtencionMedica(LocalDateTime.now(), "Detalle", "Centro");
        atencionMedica.setId(id);

        when(atMedicasServiceMock.getAtencionMedicaById(id)).thenReturn(atencionMedica);

        mockMvc.perform(MockMvcRequestBuilders.get("/atenciones/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.detalleAtencion", Matchers.is("Detalle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.centroSalud", Matchers.is("Centro")));
    }

    @Test
    public void getPacienteByRutTest() throws Exception {
        String rut = "12345678-9";
        Paciente paciente = new Paciente();
        paciente.setRut(rut);
        paciente.setNombre("Juan");
        paciente.setDireccion("Calle Principal 123");
        paciente.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(atMedicasServiceMock.getPacienteByRut(rut)).thenReturn(paciente);

        mockMvc.perform(MockMvcRequestBuilders.get("/atenciones/paciente/{pacienteRut}", rut))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rut", Matchers.is(rut)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Juan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.direccion", Matchers.is("Calle Principal 123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fechaNacimiento", Matchers.is("1990-01-01")));
    }

}