package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoDetalhamentoDTO;
import med.voll.api.dto.medico.MedicoListaDTO;
import med.voll.api.model.Medico;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid MedicoCadastroDTO medicoCadastroDTO, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(medicoCadastroDTO);
        medicoService.cadastrar(medico);

        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoDetalhamentoDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoListaDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        Page<MedicoListaDTO> page = medicoService.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid MedicoAtualizacaoDTO medicoAtualizacaoDTO) {
        var medico = medicoService.atualizar(medicoAtualizacaoDTO);
        return ResponseEntity.ok(new MedicoDetalhamentoDTO(medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = medicoService.detalhar(id);
        return ResponseEntity.ok(new MedicoDetalhamentoDTO(medico));
    }
}
