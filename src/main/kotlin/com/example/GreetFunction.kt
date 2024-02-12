package com.example

import com.example.model.Greeting
import com.example.model.User
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class GreetFunction: Function<User, Greeting> {
    override fun apply(user: User): Greeting {
        return Greeting(" Hello, " + user.name + "!!!!!")
    }
}
