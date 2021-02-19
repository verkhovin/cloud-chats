package ru.verkhovin.cloudchats

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CloudChatsApplication

fun main(args: Array<String>) {
  runApplication<CloudChatsApplication>(*args)
}
