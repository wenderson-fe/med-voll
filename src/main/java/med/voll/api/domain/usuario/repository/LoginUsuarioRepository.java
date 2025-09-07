package med.voll.api.domain.usuario.repository;

import med.voll.api.domain.usuario.LoginUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginUsuarioRepository extends JpaRepository<LoginUsuario, Long> {
    UserDetails findByLogin(String login);

    Boolean existsByLogin(String login);
}
