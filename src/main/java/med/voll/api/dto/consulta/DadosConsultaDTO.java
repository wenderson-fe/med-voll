package med.voll.api.dto.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosConsultaDTO(
        Long IdMedico,

        @NotNull
        Long IdPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
