package med.voll.api.service;

import jakarta.transaction.Transactional;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional
    public void cadastrar(PacienteCadastroDTO pacienteCadastroDTO){
        pacienteRepository.save(new Paciente(pacienteCadastroDTO));
    }
}
