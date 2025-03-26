package med.voll.api.dto.paciente;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.paciente.Paciente;

public record PacienteDetalhamentoDTO(Long id,
                                      String nome,
                                      String email,
                                      String telefone,
                                      String cpf,
                                      Endereco endereco) {

    public PacienteDetalhamentoDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                paciente.getEndereco()
        );
    }
}
