package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.dto.paciente.PacienteAtualizacaoDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;

@Entity
@Table(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;
    private Boolean ativo;

    public Paciente(PacienteCadastroDTO pacienteDTO) {
        this.ativo = true;
        this.nome = pacienteDTO.nome();
        this.email = pacienteDTO.email();
        this.telefone = pacienteDTO.telefone();
        this.cpf = pacienteDTO.cpf();
        this.endereco = new Endereco(pacienteDTO.endereco());

    }

    public void atualizarInformacoes(PacienteAtualizacaoDTO pacienteAtualizacaoDTO) {
        if(pacienteAtualizacaoDTO.nome() != null) {
            this.nome = pacienteAtualizacaoDTO.nome();
        }

        if(pacienteAtualizacaoDTO.telefone() != null) {
            this.telefone = pacienteAtualizacaoDTO.telefone();
        }

        if(pacienteAtualizacaoDTO.endereco() != null) {
            this.endereco.atualizar(pacienteAtualizacaoDTO.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
