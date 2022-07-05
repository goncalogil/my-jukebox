package com.goncalo.myjukebox.domain

data class Song(
    private val id : String,
    private val name : String
    ){

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }
}