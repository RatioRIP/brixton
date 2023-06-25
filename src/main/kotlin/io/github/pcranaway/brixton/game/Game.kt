package io.github.pcranaway.brixton.game

import io.github.pcranaway.brixton.arena.Arena
import io.github.pcranaway.brixton.user.State
import io.github.pcranaway.brixton.utils.bleach
import io.github.pcranaway.brixton.utils.toUser
import org.bukkit.entity.Player
import java.util.stream.Stream
import io.github.pcranaway.brixton.queue.Type as QueueType
import io.github.pcranaway.brixton.game.type.Type as GameType

class Game(
        val queueType: QueueType,
        val arena: Arena,
        val gameType: GameType,
        val playerA: Player,
        val playerB: Player
) {

    init {
        this.allPlayers().forEach {
            val user = it.toUser()!!

            user.state = State.Playing
        }

        this.start()
    }

    fun allPlayers(): Stream<Player> {
        return Stream.of(this.playerA, this.playerB)
    }

    fun containsPlayer(player: Player): Boolean {
        return allPlayers().anyMatch { it == player }
    }

    fun getOpposite(player: Player): Player? {
        return if (player == playerA) {
            playerB
        } else if (player == playerB)
            playerA
        else {
            null
        }
    }

    fun start() {
        this.allPlayers()
                .forEach {
                    val user = it.toUser()!!

                    // apply kit
                    it.bleach()
                    this.gameType.applyKit(it)
                }

        // manually teleport players :c
        this.playerA.teleport(arena.locationA)
        this.playerB.teleport(arena.locationB)
    }

    fun end() {
        this.allPlayers().forEach {
            it.spigot().respawn()

            val user = it.toUser()!!
            user.state = State.Lobby
            user.giveAppropriateItems()

            it.teleport(it.world.spawnLocation)
        }

        GameRepository.games.remove(this)
    }

}