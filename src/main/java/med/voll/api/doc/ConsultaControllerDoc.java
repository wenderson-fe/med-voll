package med.voll.api.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import med.voll.api.infra.exception.TratadorDeErros;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

public interface ConsultaControllerDoc {

    @Operation(summary = "Agenda uma consulta", description = "Agenda uma consulta e retorna os detalhes da consulta criada")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))

    })
    ResponseEntity<ConsultaDetalhamentoDTO> agendar(@RequestBody @Valid DadosConsultaDTO dados, UriComponentsBuilder uriBuilder);

    @Operation(summary = "Cancela uma consulta", description = "Realiza a exclusão lógica de uma consulta ativa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Consulta excluída com sucesso", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Requisição falhou ",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.Excecao.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamento);

    @Operation(summary = "Detalhar consulta", description = "Retorna os detalhes de uma consulta ativa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informações da consulta retornadas com secesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrado", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameter(name = "id", description = "ID da consulta a ser detalhada")
    ResponseEntity<ConsultaDetalhamentoDTO> detalhar(@PathVariable Long id);
}
