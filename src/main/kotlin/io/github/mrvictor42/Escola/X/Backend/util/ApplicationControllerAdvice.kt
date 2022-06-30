package io.github.mrvictor42.Escola.X.Backend.util

import io.github.mrvictor42.Escola.X.Backend.exception.ApiErrorsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import java.util.stream.Collectors

@RestControllerAdvice
class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationErros(exception: MethodArgumentNotValidException): ApiErrorsException? {
        val bindingResult = exception.bindingResult
        val messages = bindingResult
            .allErrors
            .stream()
            .map {
                objectError: ObjectError -> objectError.defaultMessage
            }
            .collect(Collectors.toList())
        return ApiErrorsException(messages)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(exception: ResponseStatusException): ResponseEntity<*>? {
        val messageError = exception.reason
        val codigoStatus = exception.status
        val apiErros = ApiErrorsException(messageError!!)
        return ResponseEntity<Any?>(apiErros, codigoStatus)
    }
}