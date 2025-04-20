package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosConsultaDTO dados) {
        LocalDateTime dataConsulta = dados.data();
        LocalDateTime agora = LocalDateTime.now();

        //Calcula quantos minutos faltam entre o momento atual e o horário da consulta.
        long diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }

    }
}
