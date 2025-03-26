package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import med.voll.api.dto.paciente.PacienteDetalhamentoDTO;
import med.voll.api.dto.paciente.PacienteListaDTO;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteDetalhamentoDTO> cadastrar(@RequestBody @Valid PacienteCadastroDTO pacienteCadastroDTO, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(pacienteCadastroDTO);
        pacienteService.cadastrar(paciente);

        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDetalhamentoDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteListaDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<PacienteListaDTO> page = pacienteService.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping()
    public ResponseEntity<PacienteDetalhamentoDTO> atualizar(@RequestBody @Valid PacienteAtualizacaoDTO pacienteAtualizacaoDTO) {
        var paciente = pacienteService.atualizar(pacienteAtualizacaoDTO);
        return ResponseEntity.ok(new PacienteDetalhamentoDTO(paciente));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDetalhamentoDTO> detalhar(@PathVariable Long id) {
        var paciente = pacienteService.detalhar(id);
        return ResponseEntity.ok(new PacienteDetalhamentoDTO(paciente));
    }
}
