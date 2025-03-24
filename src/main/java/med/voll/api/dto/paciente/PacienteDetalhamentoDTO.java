package med.voll.api.dto.paciente;

import med.voll.api.model.Endereco;
import med.voll.api.model.Paciente;

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
