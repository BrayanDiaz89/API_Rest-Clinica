package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    //Tratar error 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }
    //Tratar error 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        //Para obtener el mensaje de error únicamente y retornar a nuestro cliente
        //definimos el parámetro arriba de la Exception encapsulado en e, y obtenemos e con metodo getAllErrors();
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new).toList();

        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionException e){
        //Para obtener el mensaje de error únicamente y retornar a nuestro cliente
        //definimos el parámetro arriba de la Exception encapsulado en e, y obtenemos e con metodo getAllErrors();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
