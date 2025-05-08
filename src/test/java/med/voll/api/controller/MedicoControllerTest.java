package med.voll.api.controller;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoService;
import med.voll.api.dto.endereco.EnderecoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoDetalhamentoDTO;
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
class MedicoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<MedicoCadastroDTO> medicoCadastroTester;

    @Autowired
    private JacksonTester<MedicoDetalhamentoDTO> medicoDetalhamentoTester;

    @MockitoBean
    private MedicoService medicoService;

    @Test
    @DisplayName("Deve devolver status HTTP 400 quando as informações estiverem inválidas")
    void cadastrarCenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolve status HTTP 201 quando as informações estiverem corretas")
    void cadastrarCenario2() throws Exception{
        MedicoCadastroDTO cadastroDTO = new MedicoCadastroDTO("test", "test@gmail.com", "11986945749", "123456",
                Especialidade.ORTOPEDIA, dadosEndereco());

        MedicoDetalhamentoDTO detalhamentoDTO = new MedicoDetalhamentoDTO(null, "test", "test@gmail.com", "123456", "11986945749",
                Especialidade.ORTOPEDIA, new Endereco(dadosEndereco()));

        String jsonEsperado = medicoDetalhamentoTester.write(detalhamentoDTO).getJson();

        when(medicoService.cadastrar(any())).thenReturn(new Medico(cadastroDTO));

        MockHttpServletResponse response = mockMvc.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON).content(
                medicoCadastroTester.write(cadastroDTO).getJson()
        )).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private EnderecoDTO dadosEndereco() {
        return new EnderecoDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}