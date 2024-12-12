package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
//Anotaciones de lombok
@Getter //Generar getters para todos
@NoArgsConstructor //Generar constructores sin argumentos
@AllArgsConstructor //Generar todos los constructores
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Column
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
    }

    public void cancelar(MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }
}
