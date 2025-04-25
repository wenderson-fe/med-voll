package med.voll.api.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteDetalhamentoDTO;
import med.voll.api.dto.paciente.PacienteListaDTO;
import med.voll.api.infra.exception.TratadorDeErros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

public interface PacienteControllerDoc {
    @Operation(summary = "Cadastrar paciente", description = "Cria um novo paciente e retorna os detalhes do paciente criado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))

    })
    ResponseEntity<PacienteDetalhamentoDTO> cadastrar(@RequestBody @Valid PacienteCadastroDTO pacienteCadastroDTO, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Listar pacientes", description = "Retorna uma lista paginada de pacientes ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Page<PacienteListaDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao);

    @Operation(summary = "Atualizar paciente", description = "Atualiza as informações de um paciente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<PacienteDetalhamentoDTO> atualizar(@RequestBody @Valid PacienteAtualizacaoDTO pacienteAtualizacaoDTO);

    @Operation(summary = "Excluir paciente", description = "Realiza a exclusão lógica de um paciente ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameter(name = "id", description = "ID do paciente a ser excluído")
    ResponseEntity<Void> excluir(@PathVariable Long id);

    @Operation(summary = "Detalhar paciente", description = "Retorna os detalhes de um paciente ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações do paciente retornadas com secesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameter(name = "id", description = "ID do paciente a ser detalhado")
    ResponseEntity<PacienteDetalhamentoDTO> detalhar(@PathVariable Long id);
}
