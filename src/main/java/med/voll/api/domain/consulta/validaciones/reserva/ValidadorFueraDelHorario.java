package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorFueraDelHorario implements ValidadorDeConsultas {

    public void validar(DatosReservaConsulta datos) {
        var fechaConsulta = datos.fecha();
        var domingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesAperturaClinica = fechaConsulta.getHour() < 7;
        var horarioDespuesDeCierreClinica = fechaConsulta.getHour() > 18;

        if(domingo || horarioAntesAperturaClinica || horarioDespuesDeCierreClinica){
            throw new ValidacionException("No es posible agendar una consulta en estos horarios.");
        }
    }
}
