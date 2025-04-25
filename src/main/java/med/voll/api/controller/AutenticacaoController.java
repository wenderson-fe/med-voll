package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.doc.AutenticacaoControllerDoc;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.dto.usuario.TokenJWTDTO;
import med.voll.api.dto.usuario.DadosAutenticacaoDTO;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController implements AutenticacaoControllerDoc {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenJWTDTO> efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO Dadosautenticacao) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(Dadosautenticacao.login(), Dadosautenticacao.senha());
        var authentication = manager.authenticate(authenticationToken);

        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

}
