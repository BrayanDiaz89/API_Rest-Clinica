package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroMedico(
                @NotBlank(message = "El nombre no debe estar vacío.")
                String nombre,
                @NotBlank(message = "El email no debe estar vacío.")
                @Email
                String email,
                @NotBlank(message = "El telefono no debe estar vacío.")
                @Pattern(regexp = "\\d{5,15}")
                String telefono,
                @NotBlank(message = "El documento no debe estar vacío.")
                @Pattern(regexp = "\\d{4,10}")
                String documento,
                @NotNull(message = "La especialidad debe ser una de las existentes.")
                Especialidad especialidad,
                @NotNull(message = "La dirección no debe tener ningún campo vacío.") //Es un objeto contiene más atributos dentro
                @Valid
                DatosDireccion direccion//Por lo que retornario nulo, y no blanco.
                ) {
}
