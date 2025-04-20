package med.voll.api.dto.consulta;

import med.voll.api.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record ConsultaDetalhamentoDTO(
        Long id,
        Long IdMedico,
        Long IdPaciente,
        LocalDateTime data) {
    public ConsultaDetalhamentoDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
