package med.voll.api.dto.paciente;

import med.voll.api.domain.paciente.Paciente;

public record PacienteListaDTO(Long id,
                               String nome,
                               String email,
                               String cpf) {

    public PacienteListaDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}
