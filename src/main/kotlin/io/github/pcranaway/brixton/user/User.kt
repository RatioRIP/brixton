package io.github.pcranaway.brixton.user

import io.github.pcranaway.brixton.game.Game
import io.github.pcranaway.brixton.game.GameRepository
import io.github.pcranaway.brixton.queue.Queue
import io.github.pcranaway.brixton.queue.QueueRepository
import io.github.pcranaway.brixton.user.State.*
import org.bukkit.entity.Player

enum class State {
    Lobby,
    InQueue,
    Playing
}

data class User(val player: Player) {

    val queue: Queue?
        get() =
            QueueRepository.queues.firstOrNull { it.containsPlayer(this.player) }

    val game: Game?
        get() =
            GameRepository.games.firstOrNull { it.containsPlayer(this.player) }

    var state: State = Lobby

    fun giveAppropriateItems() =
            this.player.giveAppropriateItems(this.state)

}