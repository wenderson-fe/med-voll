package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosConsultaDTO dados) {
        //Escolha do médico é opcional
        if (dados.IdMedico() == null) {
            return;
        }

        Boolean medicoEstaAtivo = medicoRepository.estaAtivo(dados.IdMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um médico excluído do sistema.");
        }
    }
}
