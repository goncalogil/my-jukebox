package com.goncalo.myjukebox.domain

data class SongClassification(
    val song: Song,
    val votes: Int
)