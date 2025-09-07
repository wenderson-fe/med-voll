package med.voll.api.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import med.voll.api.domain.usuario.Cargo;

import java.time.LocalDate;

public record UsuarioCadastroDTO(
        @NotBlank
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos")
        String cpf,

        @NotNull
        @Past
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @NotNull
        Cargo cargo,

        @NotBlank
        @Email
        String login,

        @NotBlank
        String senha) {
}
