package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{

    public void validar(DadosConsultaDTO dados) {
        LocalDateTime dataConsulta = dados.data();

        //Verifica se a data da consulta é no domingo.
        Boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        //Verifica se a hora da consulta é antes da abertura da clínica.
        Boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;

        //Verifica se a hora da consulta é depois das 18 (18h é o limite para o agendamento de consultas).
        Boolean depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica");
        }

    }
}
