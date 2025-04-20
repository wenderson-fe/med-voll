package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCancelamentoConsulta dados) {
        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());

        if(consulta.getData().isBefore(LocalDateTime.now().plusHours(24))) {
            throw new ValidacaoException("O cancelamento só é possível com pelo menos 24 horas de antecedência.");
        }
    }
}
