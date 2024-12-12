package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key") //Anotacion swagger para bearer key, (visualizar en doc los datos)
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public Page<Paciente.DatosListadoPaciente> listarPacientes(@PageableDefault(size = 4, sort = {"nombre"}) Pageable paginacion) {
        return repository.findByActivoTrue(paginacion).map(Paciente.DatosListadoPaciente::new);
    }

    @PostMapping
    @Transactional
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente) {
        repository.save(new Paciente(datosRegistroPaciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datos) {
        Paciente paciente = repository.getReferenceById(datos.id());
        paciente.actualizarDatosPaciente(datos);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumento().toString(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void inactivarPaciente(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.desactivarPaciente();
    }

}

