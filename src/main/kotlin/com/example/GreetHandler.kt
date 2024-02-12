package com.example

import com.example.model.User
import com.microsoft.azure.functions.*
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import org.springframework.stereotype.Component
import java.util.*

@Component
class GreetHandler(val greetFunction: GreetFunction) {
    @FunctionName("greet")
    fun execute(@HttpTrigger(name = "request", methods = [HttpMethod.GET, HttpMethod.POST], authLevel = AuthorizationLevel.ANONYMOUS) request: HttpRequestMessage<Optional<User>>, context: ExecutionContext): HttpResponseMessage {
        val user = request.body.filter { it.name.isNotBlank()}.orElseGet {
                    User(request.queryParameters.getOrDefault("name", "world"))
                }
        context.logger.info("Greeting user name: " + user.name)
        return request.createResponseBuilder(HttpStatus.OK).body(greetFunction.apply(user)).header("Content-Type", "application/json").build()
    }
}
