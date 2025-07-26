//package com.francoLuvisotti.forohub.infra.errores;
//
//import com.francoLuvisotti.forohub.domain.ValidacionException;
//import com.francoLuvisotti.forohub.dto.ApiResponse;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class TratadorDeErrores {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity tratarError404(){
//        return ResponseEntity.notFound().build();
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
//        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
//        return ResponseEntity.badRequest().body(errores);
//    }
//
//    @ExceptionHandler(ValidacionException.class)
//    public ResponseEntity tratarErrorDeValidacion(ValidacionException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    private record DatosErrorValidacion(String campo, String error){
//        public DatosErrorValidacion(FieldError error) {
//            this(error.getField(), error.getDefaultMessage());
//        }
//    }
//
//}
//
package com.francoLuvisotti.forohub.infra.errores;

import com.francoLuvisotti.forohub.domain.ValidacionException;
import com.francoLuvisotti.forohub.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErrores {

    private static final Logger logger = LoggerFactory.getLogger(TratadorDeErrores.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> manejarEntidadNoEncontrada(EntityNotFoundException ex){
        logger.warn("Entidad no encontrada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<DatosErrorValidacion>>> manejarErroresValidacion(MethodArgumentNotValidException e){
        logger.warn("Error de validación: {}", e.getMessage());
        List<DatosErrorValidacion> errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Errores de validación", errores));
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<ApiResponse<Void>> manejarValidacionPersonalizada(ValidacionException e){
        logger.warn("Validación personalizada fallida: {}", e.getMessage());
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, e.getMessage(), null));
    }

    // Clase interna para representar errores de campo
    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

