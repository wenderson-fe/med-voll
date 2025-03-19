package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public void cadastrar(@RequestBody @Valid PacienteCadastroDTO pacienteCadastroDTO){
        pacienteService.cadastrar(pacienteCadastroDTO);
    }
}
