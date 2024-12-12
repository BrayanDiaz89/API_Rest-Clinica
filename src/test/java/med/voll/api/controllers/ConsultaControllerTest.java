package med.voll.api.controllers;

import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJson;

    @Autowired
    private JacksonTester<DatosCancelamientoConsulta> datosCancelamientoConsultaJson;

    //Mockar nuestra ReservaDeConsulta
    @MockitoBean //No fue posible usar @MockBean
    private ReservaDeConsultas reservaDeConsultas;

    @Test
    @DisplayName("Debería devolver método http 400 cuando la request no tenga datos.")
    @WithMockUser
    void reservar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); //obtenemos el valor 400
    }

    @Test
    @DisplayName("Debería devolver método http 400 cuando la request no tenga datos.")
    @WithMockUser
    void cancelar_escenario1() throws Exception {
        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); //obtenemos el valor 400
    }

    @Test
    @DisplayName("Debería devolver método http 200 cuando la request reciba un json valido.")
    @WithMockUser
    void reservar_escenario2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.CARDIOLOGIA;
        var datosDetalle = new DatosDetalleConsulta(null, 2l, 5l,fecha);
        //Importar metodos de mockito para condicionar que cada vez que se quiera simular una reserva
        //retorne los datos detalle (Json).
        when(reservaDeConsultas.reservar(any())).thenReturn(datosDetalle);

        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosReservaConsultaJson.write(
                                new DatosReservaConsulta(2l,5l, fecha, especialidad)
                        ).getJson()
                        )
                )
                .andReturn().getResponse();
        var jsonEsperado = datosDetalleConsultaJson.write(
                datosDetalle
        ).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); //obtenemos el valor 200
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Debería devolver método http 204 cuando la request reciba un json valido, ya que su retorno sería sin contenido, pero realizado con exito.")
    @WithMockUser
    void cancelar_escenario2() throws Exception {

        var motivo = MotivoCancelamiento.PACIENTE_DESISTIO;
        var datosCancelamiento = new DatosCancelamientoConsulta(1L, motivo);
        //Importar metodos de mockito para condicionar que cada vez que se quiera simular una reserva
        //retorne los datos detalle (Json).
        doNothing().when(reservaDeConsultas).cancelar(any());

        var response = mockMvc.perform(delete("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosCancelamientoConsultaJson.write(
                                datosCancelamiento).getJson()
                        )
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value()); //obtenemos el valor 204
        assertThat(response.getContentAsString()).isEmpty();
    }
}