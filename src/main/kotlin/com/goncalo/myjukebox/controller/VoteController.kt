package com.goncalo.myjukebox.controller

import com.goncalo.myjukebox.domain.Classification
import com.goncalo.myjukebox.domain.Song
import com.goncalo.myjukebox.domain.SongClassification
import com.goncalo.myjukebox.repository.ClassificationRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class VoteController(
    val classificationRepository: ClassificationRepository
) {
    @GetMapping("/vote/{poolId}")
    fun getAllVotes(@PathVariable poolId : String): Mono<Classification> =
        classificationRepository.getAllSortedSongs(poolId)

    @PostMapping("/vote/{poolId}")
    fun voteInSong(
        @PathVariable poolId: String,
        @RequestBody song: Song
    ) : Mono<Unit> =
        classificationRepository.incrementSongVotes(poolId, song)
}