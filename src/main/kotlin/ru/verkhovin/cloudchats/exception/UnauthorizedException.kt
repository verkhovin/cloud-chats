package ru.verkhovin.cloudchats.exception

import org.springframework.http.HttpStatus

class UnauthorizedException(reason: String) : WebMappedException(HttpStatus.UNAUTHORIZED, String.format("The action needs an authorization: %s", reason))