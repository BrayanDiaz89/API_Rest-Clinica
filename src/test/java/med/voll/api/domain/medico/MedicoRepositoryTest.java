package med.voll.api.domain.medico;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
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


@DataJpaTest//Anotación siempre utilizada cuando necesitamos testear de la capa de persistencia JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Debería devolver null, cuando el médico buscado existe, pero no está disponible en esa fecha")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario1() {
        //given or arrange (Dado un cierto contexto)
        var fechaLunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1", "medico@gmail.com", "512151", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("paciente1", "paciente@gmail.com", "652614");
        registrarConsulta(medico, paciente, fechaLunesSiguienteALas10);
        //when or act (Cuando ejecuto)
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, fechaLunesSiguienteALas10);
        //then o assert (Entonces me debe retornar...)
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería devolver medico, cuando el medico buscado está disponible en esa fecha.")
    void elegirMedicoAleatorioDisponibleEnLaFechaEscenario2() {
        //given or arrange (Dado un cierto contexto)
        var fechaLunesSiguienteALas10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        var medico = registrarMedico("Medico1", "medico@gmail.com", "512151", Especialidad.CARDIOLOGIA);
        //when or act (Cuando ejecuto)
        var medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(Especialidad.CARDIOLOGIA, fechaLunesSiguienteALas10);
        //then o assert (Entonces me debe retornar)
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null,medico,paciente,fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "52515121",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "151518451",
                documento,
                datosDireccion()
        );
    }
    private DatosReservaConsulta datosConsulta(Long idMedico, Long idPaciente, LocalDateTime fecha, Especialidad especialidad) {
        return new DatosReservaConsulta(
                idMedico,
                idPaciente,
                fecha,
                especialidad
        );
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "calle 2",
                "distrito 2",
                "Ibagué",
                "3",
                "e"
        );
    }
}