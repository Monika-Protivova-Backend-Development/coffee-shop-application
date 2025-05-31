package com.motycka.edu.lesson02

fun main() {
    helloKotlin()
    hello("Moni")
    greet("Moni", "Hi")

    val greeting = getGreeting("Moni", "Welcome")
    println(greeting)
}

fun helloKotlin() {
    println("Hello, Kotlin!")
}

fun hello(name: String) {
    println("Hello, $name!")
}

fun greet(name: String, greeting: String) {
    println("$greeting, $name!")
}

fun getGreeting(name: String, greeting: String = "Hello"): String {
    return "$greeting, $name!"
}

