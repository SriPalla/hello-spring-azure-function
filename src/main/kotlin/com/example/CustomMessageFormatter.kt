package com.example

import org.springframework.stereotype.Component

@Component
class CustomMessageFormatter {
    fun getCustomizedMessage(input:String):String {
        return "<<<<<<<<<<<<<<<<<<<<<$input>>>>>>>>>>>>>>>>>>>>>>"
    }
}