package med.voll.api.dto.medico;

import med.voll.api.model.Especialidade;
import med.voll.api.model.Medico;

public record MedicoListaDTO(String nome,
                             String email,
                             String crm,
                             Especialidade especialidade) {

    public MedicoListaDTO (Medico medico) {
        this(
                medico.getNome(),
                medico.getEmail(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}
