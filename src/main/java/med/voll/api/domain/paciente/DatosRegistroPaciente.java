package med.voll.api.domain.paciente;


import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;

public record DatosRegistroPaciente(
                                    @NotBlank(message = "El campo nombre no puede estar vacío.")
                                    String nombre,
                                    @NotBlank(message = "El campo email no puede estar vacío.")
                                    @Email
                                    String email,
                                    @NotBlank(message = "El telefono no debe estar vacío.")
                                    @Pattern(regexp = "\\d{5,15}")
                                    String telefono,
                                    @NotBlank(message = "El documento no debe estar vacío.")
                                    @Pattern(regexp = "\\d{4,10}")
                                    String documento,
                                    @NotNull(message = "La dirección no puede ser null.")
                                    @Valid
                                    DatosDireccion direccion) {
}
