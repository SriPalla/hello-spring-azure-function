package com.example

import com.example.model.Greeting
import com.example.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class GreetFunction(val customMessageFormatter: CustomMessageFormatter) : Function<User, Greeting> {
    override fun apply(user: User): Greeting {
        val message = customMessageFormatter.getCustomizedMessage(" Hello, " + user.name + "! ")
        return Greeting(message)
    }
}
