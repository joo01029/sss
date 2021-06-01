package com.sss.domain.response

import org.springframework.http.HttpStatus

class ResponseData<T>(status: HttpStatus, message: String, var data: T) : Response(status, message) {
}