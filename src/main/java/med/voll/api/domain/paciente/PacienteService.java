package med.voll.api.domain.paciente;

import jakarta.transaction.Transactional;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteListaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public Paciente cadastrar(PacienteCadastroDTO dadosPaciente){
        Paciente paciente = new Paciente(dadosPaciente);
        pacienteRepository.save(paciente);
        return paciente;
    }

    public Page<PacienteListaDTO> listar(Pageable paginacao) {
        return pacienteRepository.findAllByAtivoTrue(paginacao)
                .map(PacienteListaDTO::new);
    }

    @Transactional
    public Paciente atualizar(PacienteAtualizacaoDTO pacienteAtualizacaoDTO) {
        var paciente = pacienteRepository.getReferenceById(pacienteAtualizacaoDTO.id());
        paciente.atualizarInformacoes(pacienteAtualizacaoDTO);
        return paciente;
    }

    @Transactional
    public void excluir(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
    }

    public Paciente detalhar(Long id) {
        return pacienteRepository.getReferenceById(id);
    }
}
