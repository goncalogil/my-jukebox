package com.goncalo.myjukebox.core

import com.goncalo.myjukebox.rabbitmq.RabbitReceiver
import com.goncalo.myjukebox.rabbitmq.RabbitmqConfiguration
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RabbitmqLoader(
    private val rabbitTemplate : RabbitTemplate,
    private val receiver: RabbitReceiver
): CommandLineRunner {
    override fun run(vararg args: String?) {
        println("Sending message...")
        rabbitTemplate.convertAndSend(RabbitmqConfiguration.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ")
        receiver.latch.await(RABBIT_LATCH_TIMEOUT_MILLISECONDS.toLong(), TimeUnit.MILLISECONDS)
    }

    companion object {
        const val RABBIT_LATCH_TIMEOUT_MILLISECONDS = 1000;
    }

}
