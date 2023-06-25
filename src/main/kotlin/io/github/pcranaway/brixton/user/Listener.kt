package io.github.pcranaway.brixton.user

import io.github.pcranaway.brixton.queue.RANKED_QUEUE_INVENTORY
import io.github.pcranaway.brixton.queue.UNRANKED_QUEUE_INVENTORY
import io.github.pcranaway.brixton.user.State.*
import io.github.pcranaway.brixton.utils.toUser
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object UserListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        // create new user for player and add it to the user repository
        val user = User(player)
        UserRepository.users.add(user)

        user.giveAppropriateItems()
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        // find user and remove it
        val user = UserRepository.users.firstOrNull() { it.player == player }

        UserRepository.users.remove(user)
    }
}

object ItemListener : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val action = event.action

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            return
        }

        if(event.item == null) {
            return
        }

        val item = event.item
        val user = UserRepository.users.firstOrNull { it.player == player } ?: return

        when(user.state) {
            Lobby -> {
                if(item.isSimilar(Items.unrankedSword)) {
                    UNRANKED_QUEUE_INVENTORY.open(player)
                }

                if(item.isSimilar(Items.rankedSword)) {
                    RANKED_QUEUE_INVENTORY.open(player)
                }
            }
            InQueue -> {
                if(item.isSimilar(Items.leaveQueue)) {
                    user.queue?.removePlayer(player, Lobby)
                }
            }
            Playing -> { }
        }

    }
}

object LobbyListener : Listener {
    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent) {
        val player = event.player

        when(player.toUser()?.state) {
            Playing -> { return }

            else -> {
                event.isCancelled = true
            }
        }

    }

    @EventHandler
    fun onItemMove(event: InventoryMoveItemEvent) {
        val player: Player = event.initiator.holder as Player

        when(player.toUser()?.state) {
            Playing -> { return }

            else -> {
                event.isCancelled = true
            }
        }
    }
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player

        when(player.toUser()?.state) {
            Playing -> { return }

            else -> {
                event.isCancelled = true
            }
        }

    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player

        when(player.toUser()?.state) {
            Playing -> { return }

            else -> {
                event.isCancelled = true
            }
        }

    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if(event.entity !is Player) {
            return
        }

        val player = event.entity as Player

        when(player.toUser()?.state) {
            Playing -> { return }

            else -> {
                event.isCancelled = true
            }
        }
    }

}