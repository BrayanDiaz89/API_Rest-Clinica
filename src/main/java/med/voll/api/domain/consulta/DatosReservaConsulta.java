package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
                                   Long idMedico,
                                   @NotNull
                                   Long idPaciente,
                                   @NotNull
                                   @Future//No puede ser una fecha anterior a la fecha actual
                                   @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
                                   LocalDateTime fecha,
                                   Especialidad especialidad) {
}
