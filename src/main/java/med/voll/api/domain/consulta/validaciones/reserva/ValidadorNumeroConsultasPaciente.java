package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNumeroConsultasPaciente implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos){

        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);
        var pacienteTieneOtraConsultaEnElDia = repository.existsByPacienteIdAndFechaBetweenAndMotivoCancelamientoIsNull(datos.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteTieneOtraConsultaEnElDia){
            throw new ValidacionException("El paciente ya tiene una consulta para el mismo día, no es posible más de una consulta diaria.");
        }
    }
}
