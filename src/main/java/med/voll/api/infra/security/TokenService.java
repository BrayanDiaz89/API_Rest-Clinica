package med.voll.api.infra.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //Asignar valor de mi properties (Variable de entorno) con la anotación @Value
    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try { //Código obtenido en la documentación de auth0, una de las librerías de java,
            //publicadas oficialmente en jwt.io, vamos a su git y encontramos dependencia (instalación) y uso.
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med") //Emisor declarado
                    .withSubject(usuario.getLogin()) //Obtener para qué usuario estoy generando el token
                    .withClaim("id", usuario.getId()) //Obtener id del usuario que le estoy generando token
                    .withExpiresAt(generarFechaExpiracion()) //Dando fecha de expiración a mi token
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {

        if (token == null){
            throw new RuntimeException();
        }
        //Definimos la variable verifier afuera del try
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); //Validando la firma
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med") //el emisor de este token sea Voll med
                    .build()//Build del objeto verifier
                    .verify(token);//Verificar nuestro token
            verifier.getSubject(); //Obtenermos el solicitante o subject de este token
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        //Validar si este subject es válido o no
        if (verifier.getSubject() == null){
            throw new RuntimeException("Verifier inválido.");
        }
        //Posteriormente la podemos retornar
        return verifier.getSubject();
    }

    //Creación de metodo para dar fecha de expiracion a token (2 horas)
    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

}
