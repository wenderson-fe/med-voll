package med.voll.api.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoDetalhamentoDTO;
import med.voll.api.dto.medico.MedicoListaDTO;
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

public interface MedicoControllerDoc {
    @Operation(summary = "Cadastrar médico", description = "Cria um novo médico e retorna os detalhes do médico criado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))

    })
    ResponseEntity<MedicoDetalhamentoDTO> cadastrar(@RequestBody @Valid MedicoCadastroDTO medicoCadastroDTO, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Listar Médicos", description = "Retorna uma lista paginada de médicos ativos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Page<MedicoListaDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao);

    @Operation(summary = "Atualizar médico", description = "Atualiza as informações de um médico existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "médico atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<MedicoDetalhamentoDTO> atualizar(@RequestBody @Valid MedicoAtualizacaoDTO medicoAtualizacaoDTO);

    @Operation(summary = "Excluir médico", description = "Realiza a exclusão lógica de um médico ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Médico excluído com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameter(name = "id", description = "ID do médico a ser excluído")
    ResponseEntity<Void> excluir(@PathVariable Long id);

    @Operation(summary = "Detalhar médico", description = "Retorna os detalhes de um médico ativo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações do médico retornadas com secesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameter(name = "id", description = "ID do médico a ser detalhado")
    ResponseEntity<MedicoDetalhamentoDTO> detalhar(@PathVariable Long id);
}
