package io.github.pcranaway.brixton.queue

import io.github.pcranaway.brixton.arena.ArenaRepository
import io.github.pcranaway.brixton.game.Game
import io.github.pcranaway.brixton.game.GameRepository
import io.github.pcranaway.brixton.user.State
import io.github.pcranaway.brixton.utils.colorize
import io.github.pcranaway.brixton.utils.toUser
import io.github.pcranaway.brixton.queue.Type as QueueType
import io.github.pcranaway.brixton.game.type.Type as GameType

import org.bukkit.entity.Player
import java.util.stream.Stream

class Queue(
        val gameType: GameType,
        val queueType: QueueType
) {
    private val players: MutableList<Player> = mutableListOf()

    fun addPlayer(player: Player) {
        val user = player.toUser()!!

        // add to queue
        this.players.add(player)
        user.state = State.InQueue
        user.giveAppropriateItems()

        player.sendMessage("&bYou've been added to the ${queueType.displayName} &b${gameType.displayName} &bqueue.".colorize())

        if (this.players.size != 2) {
            return
        }

        // create match
        val playerA = players[0]
        val playerB = players[1]

        val arena = ArenaRepository.random()

        val game = Game(
                queueType,
                arena,
                gameType,
                playerA,
                playerB
        )

        GameRepository.games.add(game)

        game.start()

        // clear queue
        this.players.clear()
    }

    fun removePlayer(player: Player, newState: State) {
        val user = player.toUser()!!

        // remove from queue
        this.players.remove(player)
        user.state = newState
        user.giveAppropriateItems()

        player.sendMessage("&cYou've been removed from the ${queueType.displayName} &b${gameType.displayName} &cqueue.".colorize())
    }

    fun containsPlayer(player: Player): Boolean {
        return this.players.contains(player)
    }

    fun allPlayers(): Stream<Player> {
        return this.players.stream()
    }
}