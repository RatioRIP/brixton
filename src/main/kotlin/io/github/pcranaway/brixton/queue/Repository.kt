package io.github.pcranaway.brixton.queue

import io.github.pcranaway.brixton.queue.Type as QueueType
import io.github.pcranaway.brixton.game.type.Type as GameType

object QueueRepository {
    val queues: MutableList<Queue> = arrayListOf()

    fun findQueue(gameType: GameType, queueType: QueueType) {
        queues.filter { it.gameType == gameType && it.queueType == queueType }
    }
}