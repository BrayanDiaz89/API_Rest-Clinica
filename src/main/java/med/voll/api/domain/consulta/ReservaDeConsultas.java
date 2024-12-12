package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.cancelamiento.ValidadorCancelamientoConsulta;
import med.voll.api.domain.consulta.validaciones.reserva.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.infra.errores.ValidacionException;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {

    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired //Llamo a TODAS las clases que tengan implementado la interfaz ValidadorDeConsultas
    private List<ValidadorDeConsultas> validadores;
    @Autowired
    private List<ValidadorCancelamientoConsulta> validadoresCancelamiento;


    public DatosDetalleConsulta reservar(DatosReservaConsulta datos){

        if(!pacienteRepository.existsById(datos.idPaciente())){
            throw new ValidacionException("No existe un paciente, con el id informado.");
        }
        //Validamos que el id médico sea diferente a campo vacío o null y que el idmedico si esté ingresado, pero no exista.
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionException("No existe un médico, con el id informado.");
        }
        //Validadores, estamos implementando cada uno de los validadores que implementan la interfaz (PATRON STRATEGY SE LLAMA)
        validadores.forEach(v-> v.validar(datos));

        var medico = seleccionarMedico(datos);
        //Al no encontrar medicos con la especialidad deseada, retornara un null, por lo que hay que
        //tratar el error, para que en caso de que sea null, nos informe que no hay medicos disponibles
        //para esa especialidad y en ese horario.
        if(medico == null){
            throw new ValidacionException("No existe un médico disponible en ese horario.");
        }
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datos.fecha(),null);

        repository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosReservaConsulta datos) {
        if(datos.idMedico() != null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionException("Es necesario elegir una especialidad, cuando no se elige el médico.");
        }
        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datos) {
        if(!repository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe.");
        }

        validadoresCancelamiento.forEach(v-> v.validarCancelamiento(datos));

        var consulta = repository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }

}
