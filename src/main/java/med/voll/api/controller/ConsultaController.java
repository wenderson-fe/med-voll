package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.dto.consulta.ConsultaDetalhamentoDTO;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.consulta.DadosConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ConsultaDetalhamentoDTO> agendar(@RequestBody @Valid DadosConsultaDTO dados, UriComponentsBuilder uriBuilder) {
        //System.out.println(dados);
        ConsultaDetalhamentoDTO consulta = consultaService.agendar(dados);
        URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.id()).toUri();
        return ResponseEntity.created(uri).body(consulta);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamento) {
        consultaService.cancelar(dadosCancelamento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public  ResponseEntity<ConsultaDetalhamentoDTO> detalhar(@PathVariable Long id){
        Consulta consulta = consultaService.detalhar(id);
        return ResponseEntity.ok().body(new ConsultaDetalhamentoDTO(consulta));
    }

}
