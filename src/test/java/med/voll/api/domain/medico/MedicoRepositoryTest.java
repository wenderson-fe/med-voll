package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaService;
import med.voll.api.domain.consulta.MotivoCancelamento;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.dto.consulta.DadosCancelamentoConsulta;
import med.voll.api.dto.endereco.EnderecoDTO;
import med.voll.api.dto.medico.MedicoCadastroDTO;
import med.voll.api.dto.paciente.PacienteCadastroDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    @ActiveProfiles("test")
    class MedicoRepositoryTest {

        @Autowired
        private MedicoRepository medicoRepository;

        @Autowired
        private EntityManager entityManager;

        @Test
        @DisplayName("Deve devolver null quando o único médico cadastrado da mesma especialidade não está disponível na data")
        void escolherMedicoAleatorioLivreNaDataCenario1() {
            //given ou arrange
            LocalDateTime proximaSegundaAs10 = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
            Paciente paciente = cadastrarPaciente("Paciente", "paciente@emial.com", "00000000000");
            cadastrarConsulta(medico, paciente, proximaSegundaAs10);

            //when ou act
            Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

            //then ou assert
            assertThat(medicoLivre).isNull();
        }

        @Test
        @DisplayName("Deve devolver médico da mesma especialidade quando ele estiver disponível na data")
        void escolherMedicoAleatorioLivreNaDataCenario2() {
            LocalDateTime proximaSegundaAs10 = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

            Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
            assertThat(medicoLivre).isEqualTo(medico);
        }

        @Test
        @DisplayName("Deve devolver null quando existir médico disponível na data, mas de outra especialidade")
        void escolherMedicoAleatorioLivreNaDataCenario3() {
            LocalDateTime proximaSegundaAs10 = LocalDate.now()
                    .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                    .atTime(10, 0);

            cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

            Medico medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.DERMATOLOGIA, proximaSegundaAs10);
            assertThat(medicoLivre).isNull();
        }

        // Métodos para criar DTOs e popular o banco de dados de teste
        private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
            entityManager.persist(new Consulta(paciente, medico, data));
        }

        private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
            Medico medico = new Medico(dadosMedico(nome, email, crm, especialidade));
            entityManager.persist(medico);
            return medico;
        }

        private Paciente cadastrarPaciente(String nome, String email, String cpf) {
            Paciente paciente = new Paciente(dadosPaciente(nome, email, cpf));
            entityManager.persist(paciente);
            return paciente;
        }

        private MedicoCadastroDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
            return new MedicoCadastroDTO(
                    nome,
                    email,
                    "61999999999",
                    crm,
                    especialidade,
                    dadosEndereco()
            );
        }

        private PacienteCadastroDTO dadosPaciente(String nome, String email, String cpf) {
            return new PacienteCadastroDTO(
                    nome,
                    email,
                    "61999999999",
                    cpf,
                    dadosEndereco()
            );
        }

        private EnderecoDTO dadosEndereco() {
            return new EnderecoDTO(
                    "rua xpto",
                    "bairro",
                    "00000000",
                    "Brasilia",
                    "DF",
                    null,
                    null
            );
        }

        private DadosCancelamentoConsulta dadosCancelamentoConsulta() {
            return new DadosCancelamentoConsulta(
                    1L,
                    MotivoCancelamento.PACIENTE_DESISTIU
            );
        }


}