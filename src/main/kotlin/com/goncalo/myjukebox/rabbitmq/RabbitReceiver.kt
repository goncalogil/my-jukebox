package com.goncalo.myjukebox.rabbitmq

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class RabbitReceiver{
    val latch: CountDownLatch = CountDownLatch(1)

    fun receiveMessage(message: String){
        println("Received < $message >")
        latch.countDown()
    }
}
