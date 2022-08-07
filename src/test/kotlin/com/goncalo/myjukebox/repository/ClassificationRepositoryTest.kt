package com.goncalo.myjukebox.repository

import com.goncalo.myjukebox.domain.Song
import com.goncalo.myjukebox.repository.helper.CatalogFaker
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveRedisOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(MockKExtension::class)
class ClassificationRepositoryTest {
    private val faker = CatalogFaker()
    private val songOps = mockk<ReactiveRedisOperations<String, Song>>()
    private val subject = ClassificationRepository(songOps)

    @Test
    fun `When I call incrementVotes, it should return a Unit` () {
        // Given
        val poolId = "fakePoolId"
        val song = faker.song()

        every {
            songOps.opsForZSet().incrementScore("$poolId:classification", song, (1).toDouble())
        } returns Mono.just((1).toDouble())

        // When
        val result = subject.incrementSongVotes(poolId, song)

        // Then
        StepVerifier.create(result)
            .assertNext {
                assert(it.javaClass == Unit.javaClass)
            }
            .verifyComplete()
    }

    @Test
    fun `When I call getSongWithHighestScore, it should return a Song` () {
        // Given
        val poolId = "fakePoolId"
        val song = faker.song()

        every {
            songOps.opsForZSet().reverseRange("$poolId:classification", Range.closed(0,0))
        } returns Flux.just(song)

        // When
        val result = subject.getSongWithHighestScore(poolId)

        // Then
        StepVerifier.create(result)
            .assertNext {
                assert( it.id == song.id)
                assert( it.name == song.name)
            }
            .verifyComplete()
    }
}
