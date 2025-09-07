package med.voll.api.domain.usuario.service;

import jakarta.transaction.Transactional;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.usuario.DadosUsuario;
import med.voll.api.domain.usuario.repository.DadosUsuarioRepository;
import med.voll.api.domain.usuario.LoginUsuario;
import med.voll.api.domain.usuario.repository.LoginUsuarioRepository;
import med.voll.api.dto.usuario.UsuarioCadastroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private DadosUsuarioRepository dadosUsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginUsuarioRepository loginUsuarioRepository;

    //Cadastra um usuário e cria o login associado
    @Transactional
    public DadosUsuario cadastrar(UsuarioCadastroDTO dadosUsuario) {
        if (dadosUsuarioRepository.existsByCpf(dadosUsuario.cpf()) ||
                loginUsuarioRepository.existsByLogin(dadosUsuario.login())) {
            throw new ValidacaoException("Dados já cadastrados para outro usuário");
        }

        DadosUsuario usuario = new DadosUsuario(
                dadosUsuario.nome(),
                dadosUsuario.cpf(),
                dadosUsuario.dataNascimento(),
                dadosUsuario.cargo()
        );

        dadosUsuarioRepository.save(usuario);

        cadastrarLogin(dadosUsuario.login(), dadosUsuario.senha(), usuario);

        return usuario;
    }

    private void cadastrarLogin(String login, String senha, DadosUsuario usuario) {
        LoginUsuario loginUsuario = new LoginUsuario(
                login,
                passwordEncoder.encode(senha),
                usuario
        );

        loginUsuarioRepository.save(loginUsuario);
    }
}
