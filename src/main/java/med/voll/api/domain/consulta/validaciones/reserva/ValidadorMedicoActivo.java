package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository repository;

    public void validar(DatosReservaConsulta datos) {
        //elección del medico opcional
        if(datos.idMedico() == null){
            return;
        }
        var medicoEstaActivo = repository.findActivoById(datos.idMedico());
        if(!medicoEstaActivo){
            throw new ValidacionException("No es posible agendar la consulta, médico inactivo.");
        }
    }
}
