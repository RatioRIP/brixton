package io.github.pcranaway.brixton

import io.github.pcranaway.brixton.queue.Type as QueueType
import io.github.pcranaway.brixton.game.type.Type as GameType

import fr.minuskube.inv.InventoryManager
import io.github.pcranaway.brixton.arena.ArenaCommands
import io.github.pcranaway.brixton.game.GameListener
import io.github.pcranaway.brixton.game.type.TYPES
import io.github.pcranaway.brixton.queue.Queue
import io.github.pcranaway.brixton.queue.QueueRepository
import io.github.pcranaway.brixton.user.ItemListener
import io.github.pcranaway.brixton.user.LobbyListener
import io.github.pcranaway.brixton.user.UserListener
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler

class BrixtonPlugin : JavaPlugin() {

    companion object {
        lateinit var inventoryManager: InventoryManager
    }

    override fun onEnable() {
        // setup queues
        TYPES.forEach {
            QueueRepository.queues.add(Queue(it, QueueType.Ranked))
            QueueRepository.queues.add(Queue(it, QueueType.Unranked))
        }

        // setup listeners
        this.server.pluginManager.registerEvents(UserListener, this)
        this.server.pluginManager.registerEvents(ItemListener, this)
        this.server.pluginManager.registerEvents(LobbyListener, this)
        this.server.pluginManager.registerEvents(GameListener, this)

        // setup inventory manager
        inventoryManager = InventoryManager(this)
        inventoryManager.init()

        // setup commands
        val commandHandler = BukkitCommandHandler.create(this)
        commandHandler.register(ArenaCommands)
    }

}