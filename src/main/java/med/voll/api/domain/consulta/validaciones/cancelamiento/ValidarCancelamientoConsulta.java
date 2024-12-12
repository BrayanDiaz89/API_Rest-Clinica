package med.voll.api.domain.consulta.validaciones.cancelamiento;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarCancelamientoConsulta implements ValidadorCancelamientoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validarCancelamiento(DatosCancelamientoConsulta datos) {
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidacionException("La consulta solo puede ser cancelada con más de 24 horas de anticipación.");
        }

    }

}
