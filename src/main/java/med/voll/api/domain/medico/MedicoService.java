package med.voll.api.domain.medico;

import jakarta.transaction.Transactional;
import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoListaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public Medico cadastrar(MedicoCadastroDTO dadosCadastro) {
        Medico medico = new Medico(dadosCadastro);
        medicoRepository.save(medico);
        return medico;
    }

    public Page<MedicoListaDTO> listar(Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao)
                .map(MedicoListaDTO::new);
    }

    @Transactional
    public Medico atualizar(MedicoAtualizacaoDTO medicoAtualizacaoDTO) {
        var medico = medicoRepository.getReferenceById(medicoAtualizacaoDTO.id());
        medico.atualizarInformacoes(medicoAtualizacaoDTO);
        return medico;
    }

    @Transactional
    public void excluir(Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();
    }

    public Medico detalhar(Long id) {
        return medicoRepository.getReferenceById(id);
    }
}
