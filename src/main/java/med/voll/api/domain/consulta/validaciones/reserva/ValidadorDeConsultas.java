package med.voll.api.domain.consulta.validaciones.reserva;

import med.voll.api.domain.consulta.DatosReservaConsulta;

//Creación de interfaz para permitir el llamado de todas las clases Validador (Se debe implementar la interfaz en cada clase), aplicando los principios SOLID
//LA "S" Responsabilidad única, cada uno de los validadores hace una cosa específica,
//LA "O" Abierto y cerrado, porque dentro de reserva de consultas, hemos cerrado la clase, no se modificará, pero estará disponible a cambios a través de la interfaz,
//LA "D" Principio de inversión de dependencias, porque utilizamos la interfaz y no cada clase concreta una por una
public interface ValidadorDeConsultas {
    void validar(DatosReservaConsulta datos);
}
