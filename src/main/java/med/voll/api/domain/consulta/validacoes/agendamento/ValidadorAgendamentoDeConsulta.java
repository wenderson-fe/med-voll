package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.dto.consulta.DadosConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosConsultaDTO dados);
}
