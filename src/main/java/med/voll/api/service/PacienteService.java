package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteListaDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public void cadastrar(PacienteCadastroDTO pacienteCadastroDTO){
        pacienteRepository.save(new Paciente(pacienteCadastroDTO));
    }

    public Page<PacienteListaDTO> listar(Pageable paginacao) {
        return pacienteRepository.findAllByAtivoTrue(paginacao)
                .map(PacienteListaDTO::new);
    }

    @Transactional
    public void atualizar(PacienteAtualizacaoDTO pacienteAtualizacaoDTO) {
        var paciente = pacienteRepository.getReferenceById(pacienteAtualizacaoDTO.id());
        paciente.atualizarInformacoes(pacienteAtualizacaoDTO);
    }

    @Transactional
    public void excluir(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }

}
