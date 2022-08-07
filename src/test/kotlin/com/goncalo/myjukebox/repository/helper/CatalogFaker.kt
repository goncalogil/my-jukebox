package com.goncalo.myjukebox.repository.helper

import com.goncalo.myjukebox.domain.Song
import net.datafaker.Faker


class CatalogFaker {
    private val javaFaker = Faker()

    fun song() = Song(
        id = javaFaker.internet().uuid().toString(),
        name = javaFaker.rockBand().name().toString()
    )
}