package ru.verkhovin.cloudchats.exception

import org.springframework.http.HttpStatus

abstract class WebMappedException(val statusCode: HttpStatus, message: String) : RuntimeException(message)