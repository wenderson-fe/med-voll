package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.dto.MedicoDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public void cadastrar(MedicoDTO medicoDTO) {
        medicoRepository.save(new Medico(medicoDTO));
    }

}
