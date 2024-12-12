package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Table(name = "pacientes")
@Entity(name = "Paciente")
//Anotaciones de lombok
@Getter //Generar getters para todos
@NoArgsConstructor //Generar constructores sin argumentos
@AllArgsConstructor //Generar todos los constructores
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.documento = datosRegistroPaciente.documento();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public void actualizarDatosPaciente(DatosActualizarPaciente datos){
        if(datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if(datos.telefono() != null) {
            this.telefono = datos.telefono();
        }
        if(datos.activo() != false) {
            this.activo = datos.activo();
        }
        if(datos.direccion() != null) {
            direccion.actualizarDatos(datos.direccion());
        }
    }

    public record DatosListadoPaciente(Long id, String nombre, String email, String documento){
        public DatosListadoPaciente(Paciente paciente) {
            this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
        }
    }

    //Metodo y clase Record DatosListaPaciente, creado para fines didáctico y aprendizaje.
    public void listadoPacientes(DatosListaPaciente datos){
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.documento = datos.documento();
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
