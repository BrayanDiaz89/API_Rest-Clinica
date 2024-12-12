package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //@Component hace que tu clase sea visible para Spring, permitiendo que @Autowired
public class ValidadorPacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosReservaConsulta datos) {
        var pacienteEstaActivo = repository.findActivoById(datos.idPaciente());
        if(!pacienteEstaActivo) {
            throw new ValidacionException("La consulta no puede ser reservada, paciente no inactivo");
        }
    }
}
