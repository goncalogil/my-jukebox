package com.goncalo.myjukebox.domain

import reactor.core.publisher.Flux

data class Classification (
    val totalVotes : Int,
    val songs: List<SongClassification>
)
