package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosUsuario;
import med.voll.api.domain.usuario.service.UsuarioService;
import med.voll.api.dto.usuario.UsuarioCadastroDTO;
import med.voll.api.dto.usuario.UsuarioDetalhamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioDetalhamentoDTO> cadastrar(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO, UriComponentsBuilder uriBuilder) {
        DadosUsuario usuario = service.cadastrar(usuarioCadastroDTO);

        URI uri = uriBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDetalhamentoDTO(usuario));
    }
}
