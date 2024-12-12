package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoTieneOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosReservaConsulta datos) {
     var medicoTieneOtraConsultaEnElMismoHorario = repository.existsByMedicoIdAndFechaAndMotivoCancelamientoIsNull(datos.idMedico(), datos.fecha());

     if(medicoTieneOtraConsultaEnElMismoHorario){
         throw new ValidacionException("El médico ya tiene otra consulta en esa misma hora y fecha.");
     }

    }
}
