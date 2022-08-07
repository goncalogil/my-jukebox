package com.goncalo.myjukebox.repository

import com.goncalo.myjukebox.domain.Classification
import com.goncalo.myjukebox.domain.Song
import com.goncalo.myjukebox.domain.SongClassification
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class ClassificationRepository(
    private val songOps: ReactiveRedisOperations<String, Song>
) {
    fun incrementSongVotes(poolId: String, song: Song): Mono<Unit> =
        songOps.opsForZSet().incrementScore(getClassificationKey(poolId), song, (1).toDouble())
            .flatMap { Mono.just(Unit) }

    fun getSongWithHighestScore(poolId: String): Mono<Song> =
        songOps.opsForZSet().reverseRange(getClassificationKey(poolId), Range.closed(0,0))
            .single()

    fun getAllSortedSongs(poolId: String) : Mono<Classification> {
        var totalVotes = 0
        return songOps.opsForZSet().scan(getClassificationKey(poolId))
            .map {
                val songVotes = it.score?.toInt() ?: 0
                totalVotes += songVotes
                it.value?.let{ song -> SongClassification(Song( song.id, song.name ), songVotes) }
            }
            .collectList()
            .single()
            .map {
                Classification(totalVotes, it.sortedByDescending { song -> song.votes })
            }
    }

    private fun getClassificationKey(poolId: String): String = "$poolId:$classificationSuffix"
    private val classificationSuffix = "classification"
}
