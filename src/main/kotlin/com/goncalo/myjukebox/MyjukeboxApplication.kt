package com.goncalo.myjukebox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyjukeboxApplication

fun main(args: Array<String>) {
	@Suppress("SpreadOperator")
	runApplication<MyjukeboxApplication>(*args)
}
