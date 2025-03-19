package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteListaDTO;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public void cadastrar(@RequestBody @Valid PacienteCadastroDTO pacienteCadastroDTO){
        pacienteService.cadastrar(pacienteCadastroDTO);
    }

    @GetMapping
    public Page<PacienteListaDTO> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return pacienteService.listar(paginacao);
    }

    @PutMapping()
    public void atualizar(@RequestBody @Valid PacienteAtualizacaoDTO pacienteAtualizacaoDTO) {
        pacienteService.atualizar(pacienteAtualizacaoDTO);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
    }
}
