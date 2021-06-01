package com.sss.handler

import com.sss.domain.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException::class)
    fun handleClientException(e : HttpClientErrorException) : ResponseEntity<Response>{
        val response : Response = Response(e.statusCode, e.message!!)
        return ResponseEntity<Response>(response, e.statusCode)
    }

    @ExceptionHandler(HttpServerErrorException::class)
    fun handleServerException(e : HttpServerErrorException) : ResponseEntity<Response>{
        val response : Response = Response(e.statusCode, e.message!!)
        return ResponseEntity<Response>(response, e.statusCode)
    }
}