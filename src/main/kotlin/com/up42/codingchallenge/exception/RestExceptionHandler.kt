package com.up42.codingchallenge.exception

import com.up42.codingchallenge.domain.ErrorDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Created by santoshsharma on 28 May, 2022
 */

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(FeatureNotFoundException :: class)
    fun handleException(fnfEx: FeatureNotFoundException): ResponseEntity<ErrorDTO> {
        val errors = ErrorDTO(fnfEx.message)
        return ResponseEntity<ErrorDTO>(errors, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}