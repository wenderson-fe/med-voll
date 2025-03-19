package med.voll.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.dto.medico.MedicoAtualizacaoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(MedicoCadastroDTO medicoDTO) {
        this.ativo = true;
        this.nome = medicoDTO.nome();
        this.email = medicoDTO.email();
        this.telefone = medicoDTO.telefone();
        this.crm = medicoDTO.crm();
        this.especialidade = medicoDTO.especialidade();
        this.endereco = new Endereco(medicoDTO.endereco());
    }

    public void atualizarInformacoes(MedicoAtualizacaoDTO medicoAtualizacaoDTO) {
        if(medicoAtualizacaoDTO.nome() != null) {
            this.nome = medicoAtualizacaoDTO.nome();
        }

        if(medicoAtualizacaoDTO.telefone() != null) {
            this.telefone = medicoAtualizacaoDTO.telefone();
        }

        if(medicoAtualizacaoDTO.endereco() != null) {
            this.endereco.atualizar(medicoAtualizacaoDTO.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
