package ru.verkhovin.cloudchats.api

import java.net.URI

fun locationHeader(path: String): URI = URI.create(path)
