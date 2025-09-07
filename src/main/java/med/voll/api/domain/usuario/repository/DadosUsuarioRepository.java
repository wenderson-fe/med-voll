package med.voll.api.domain.usuario.repository;

import med.voll.api.domain.usuario.DadosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosUsuarioRepository extends JpaRepository<DadosUsuario, Long> {
    Boolean existsByCpf(String cpf);
}
