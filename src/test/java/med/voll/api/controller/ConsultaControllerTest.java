package med.voll.api.controller;

import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosConsultaDTO> dadosConsultaDTOJson;

    @Autowired
    private JacksonTester<ConsultaDetalhamentoDTO> consultaDetalhamentoDTOJson;

    @MockitoBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deve devolver código HTTP 400 quando informações estão inválidas")
    void agendarCenario1() throws Exception {
        // Simula uma requisição, post, passando um Json vazio.
        MockHttpServletResponse response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver código HTTP 200 quando informações estão corretas")
    void agendarCenario2() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.GINECOLOGIA;

        ConsultaDetalhamentoDTO dadosDetalhamento = new ConsultaDetalhamentoDTO(null, 8L, 11L, data);

        //Define um comportamento para quando o método agendar da classe consultaService for chamado.
        when(consultaService.agendar(any())).thenReturn(dadosDetalhamento);

        String jsonEsperado = consultaDetalhamentoDTOJson.write(
                dadosDetalhamento
        ).getJson();

        MockHttpServletResponse response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON).content(dadosConsultaDTOJson.write(
                                new DadosConsultaDTO(8L, 11L, data, especialidade)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}