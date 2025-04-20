package med.voll.api.domain.consulta;

import jakarta.transaction.Transactional;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoDeConsultas> validadoresCancelamento;

    @Transactional
    public ConsultaDetalhamentoDTO agendar (DadosConsultaDTO dados) {
        if (!pacienteRepository.existsById(dados.IdPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe.");
        }

        // A escolha do médico é opcional, logo o Id do médico pode ser nulo.
        if (dados.IdMedico() != null && !medicoRepository.existsById(dados.IdMedico())) {
            throw new ValidacaoException("Id do médico informado não existe.");
        }

        //Aplica todas as validações nos dados da consulta.
        validadores.forEach(v -> v.validar(dados));

        Paciente paciente = pacienteRepository.getReferenceById(dados.IdPaciente());
        Medico medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data.");
        }

        Consulta consulta = new Consulta(paciente, medico, dados.data());
        consultaRepository.save(consulta);

        return new ConsultaDetalhamentoDTO(consulta);
    }

    private Medico escolherMedico(DadosConsultaDTO dados) {
        if (dados.IdMedico() != null) {
            return medicoRepository.getReferenceById(dados.IdMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for informado.");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    @Transactional
    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.validar(dados));

        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());

        consulta.cancelar(dados);
    }

    public Consulta detalhar(Long id) {
        return consultaRepository.getReferenceById(id);
    }
}
