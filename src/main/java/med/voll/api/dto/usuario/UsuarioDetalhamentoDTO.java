package med.voll.api.dto.usuario;

import med.voll.api.domain.usuario.DadosUsuario;

public record UsuarioDetalhamentoDTO(Long id, String nome) {

    public UsuarioDetalhamentoDTO(DadosUsuario usuario) {
        this (
                usuario.getId(),
                usuario.getNome()
        );
    }
}
