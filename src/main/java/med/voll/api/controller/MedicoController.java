package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.medico.MedicoListaDTO;
import med.voll.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public void cadastrar(@RequestBody @Valid MedicoCadastroDTO medicoCadastroDTO) {
        medicoService.cadastrar(medicoCadastroDTO);

    }

    @GetMapping
    public Page<MedicoListaDTO> medicoListaDTO(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return medicoService.listar(paginacao);
    }
}
