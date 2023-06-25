package io.github.pcranaway.brixton.game

import io.github.pcranaway.brixton.BrixtonPlugin
import io.github.pcranaway.brixton.user.State
import io.github.pcranaway.brixton.utils.colorize
import io.github.pcranaway.brixton.utils.toUser
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.plugin.java.JavaPlugin

object GameListener : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val killed = event.entity!!
        val killedUser = killed.toUser()!!

        assert(killedUser.state == State.Playing)

        val game = killedUser.game ?: return

        val killer = game.getOpposite(killed) ?: return
        val killerUser = killer.toUser()

        game.allPlayers().forEach {
            it.sendMessage("&b${killed.name} &cwas killed by &b${killer.name}".colorize())
        }

        game.end()
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val user = player.toUser()!!

        event.respawnLocation = player.world.spawnLocation

        user.state = State.Lobby

        val plugin = JavaPlugin.getPlugin(BrixtonPlugin::class.java)

        plugin.server.scheduler.runTaskLater(plugin, fun () {
            user.giveAppropriateItems()
        }, 20L);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuit(event: PlayerQuitEvent) {
        val loser = event.player
        val loserUser = loser.toUser() ?: return

        val game = loserUser.game ?: return

        val winner = game.getOpposite(loser)!!
        val winnerUser = winner.toUser()!!

        game.allPlayers().forEach {
            it.sendMessage("&b${loser.name} &c disconnected".colorize())
        }

        game.end()
    }
}
