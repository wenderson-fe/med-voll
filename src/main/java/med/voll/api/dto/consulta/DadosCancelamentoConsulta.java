package med.voll.api.dto.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.MotivoCancelamento;

public record DadosCancelamentoConsulta(
        @NotNull
        @JsonAlias("id")
        Long idConsulta,

        @NotNull
        @JsonAlias("motivo_cancelamento")
        MotivoCancelamento motivoCancelamento
        ) {
}
