package com.goncalo.myjukebox.rabbitmq

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableAutoConfiguration
class RabbitmqConfiguration{
    @Bean
    fun queue(): Queue = Queue(queueName,false)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(topicExchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange) =
        BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: RabbitReceiver): MessageListenerAdapter =
        MessageListenerAdapter(receiver, "receiveMessage")

    companion object {
        const val topicExchangeName : String = "spring-boot-exchange"
        const val queueName : String = "spring-boot"
    }
}
