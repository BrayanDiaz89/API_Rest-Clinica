package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente (
                                        @NotNull
                                        Long id,
                                        String nombre,
                                        @Email
                                        String email,
                                        String telefono,
                                        String documento,
                                        boolean activo,
                                        @Valid
                                        DatosDireccion direccion) {
}
