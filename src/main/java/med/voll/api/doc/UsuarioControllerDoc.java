package med.voll.api.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import med.voll.api.dto.usuario.UsuarioCadastroDTO;
import med.voll.api.dto.usuario.UsuarioDetalhamentoDTO;
import med.voll.api.infra.exception.TratadorDeErros;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

public interface UsuarioControllerDoc {
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário e retorna id e nome do usuário criado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.DadosErroValidacao.class)))

    })
    public ResponseEntity<UsuarioDetalhamentoDTO> cadastrar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO, UriComponentsBuilder uriBuilder);
}
