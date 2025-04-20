package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosConsultaDTO dados) {
        Boolean pacienteEstaAtivo = pacienteRepository.estaAtivo(dados.IdPaciente());

        if(!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com um paciente excluído do sistema.");
        }
    }

}
