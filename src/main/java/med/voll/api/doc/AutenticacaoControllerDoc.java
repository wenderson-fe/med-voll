package med.voll.api.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import med.voll.api.dto.usuario.DadosAutenticacaoDTO;
import med.voll.api.infra.exception.TratadorDeErros;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AutenticacaoControllerDoc {
    @Operation(summary = "Realizar login", description = "Realiza login e retorna um token JWT para autenticação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(implementation = TratadorDeErros.Excecao.class)))

    })
    ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO Dadosautenticacao);
}
