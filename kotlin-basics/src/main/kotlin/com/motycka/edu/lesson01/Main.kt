package com.motycka.edu.lesson01

fun main(args: Array<String>) {
    println("Kotlin version: ${KotlinVersion.CURRENT}")
    println("JVM version: ${System.getProperty("java.version")}")
    println("OS name: ${System.getProperty("os.name")}")
    println("OS version: ${System.getProperty("os.version")}")
    println("OS architecture: ${System.getProperty("os.arch")}")
    println("Program arguments: ${args.joinToString(", ")}")
}
