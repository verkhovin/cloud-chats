package ru.verkhovin.cloudchats.common

import java.util.*

fun <T> Optional<T>.nullable(): T? = this.orElse(null)
