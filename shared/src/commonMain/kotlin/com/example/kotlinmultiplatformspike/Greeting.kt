package com.example.kotlinmultiplatformspike

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}