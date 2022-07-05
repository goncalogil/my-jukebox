package com.goncalo.myjukebox.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.newSerializationContext


@Configuration
class RedisConfiguration(
    val objectMapper: ObjectMapper
) {

    @Bean
    @Primary
    fun <K, V> reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<K, V> {

        val redisObjectMapper = objectMapper.copy()
        redisObjectMapper.activateDefaultTypingAsProperty(
            LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.EVERYTHING,
            null,
        )
        val serializer = GenericJackson2JsonRedisSerializer(redisObjectMapper)
        val builder: RedisSerializationContext.RedisSerializationContextBuilder<K, V> = newSerializationContext(serializer)
        return ReactiveRedisTemplate<K, V>(factory, builder.build())
    }
}