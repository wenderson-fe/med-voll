package med.voll.api.controller;

import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteService;
import med.voll.api.dto.endereco.EnderecoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteDetalhamentoDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class PacienteControllerTest {
    @Autowired
    private JacksonTester<PacienteCadastroDTO> pacienteCadastroTester;

    @Autowired
    private JacksonTester<PacienteDetalhamentoDTO> pacienteDetalhamentoTester;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PacienteService pacienteService;

    @Test
    @DisplayName("Deve devolver status HTTP 400 quando as informações estiverem inválidas")
    void cadastrarCenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON).content("{}")
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deve devolver status HTTP 201 quando as informações estiverem corretas")
    void cadastrarCenario2() throws Exception {
        PacienteCadastroDTO cadastroDTO = new PacienteCadastroDTO("Paciente", "paciente@gmail.com", "11000000000",
                "54676864676", dadosEndereco());

        PacienteDetalhamentoDTO detalhamentoDTO = new PacienteDetalhamentoDTO(new Paciente(cadastroDTO));

        String jsonEsperado = pacienteDetalhamentoTester.write(detalhamentoDTO).getJson();

        when(pacienteService.cadastrar(any())).thenReturn(new Paciente(cadastroDTO));

        MockHttpServletResponse response = mockMvc.perform(post("/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pacienteCadastroTester.write(cadastroDTO).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private EnderecoDTO dadosEndereco() {
        return new EnderecoDTO(
                "rua teste",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }







}