package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoListaDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public void cadastrar(MedicoCadastroDTO medicoCadastroDTO) {
        medicoRepository.save(new Medico(medicoCadastroDTO));
    }

    public Page<MedicoListaDTO> listar(Pageable paginacao) {
        return medicoRepository.findAll(paginacao)
                .map(MedicoListaDTO::new);
    }


}
