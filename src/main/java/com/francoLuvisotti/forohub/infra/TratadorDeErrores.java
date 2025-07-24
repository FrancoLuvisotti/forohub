package com.francoLuvisotti.forohub.infra;

import com.francoLuvisotti.forohub.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> error404(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(
            new ApiResponse<>(false, e.getMessage(), null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> error500(Exception e) {
        return ResponseEntity.status(500).body(
            new ApiResponse<>(false, "Error interno: " + e.getMessage(), null)
        );
    }
}
