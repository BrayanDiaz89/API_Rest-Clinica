package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component //@Component hace que tu clase sea visible para Spring, permitiendo que @Autowired
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {
        var ahora = LocalDateTime.now();
        var fechaConsulta = datos.fecha();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();
        if(diferenciaEnMinutos < 30) {
            throw new ValidacionException("No es posible agendar una consulta, con menos de 30 min de anticipación.");
        }
    }
}
