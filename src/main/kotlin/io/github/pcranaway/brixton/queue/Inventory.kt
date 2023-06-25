package io.github.pcranaway.brixton.queue

import fr.minuskube.inv.ClickableItem
import io.github.pcranaway.brixton.queue.Type as QueueType
import io.github.pcranaway.brixton.game.type.Type as GameType

import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import io.github.pcranaway.brixton.BrixtonPlugin
import io.github.pcranaway.brixton.utils.colorize
import io.github.pcranaway.brixton.utils.lore
import io.github.pcranaway.brixton.utils.name
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

val UNRANKED_QUEUE_INVENTORY: SmartInventory = SmartInventory.builder()
        .id("unrankedQueue")
        .provider(QueueInventoryProvider(QueueType.Unranked))
        .title("&bUnranked Queue".colorize())
        .size(3, 9)
        .manager(BrixtonPlugin.inventoryManager)
        .build()

val RANKED_QUEUE_INVENTORY: SmartInventory = SmartInventory.builder()
        .id("rankedQueue")
        .provider(QueueInventoryProvider(QueueType.Ranked))
        .title("&cRanked Queue".colorize())
        .size(3, 9)
        .manager(BrixtonPlugin.inventoryManager)
        .build()

class QueueInventoryProvider(val queueType: QueueType) : InventoryProvider {
    override fun init(player: Player?, contents: InventoryContents?) {
        QueueRepository.queues
                .filter { it.queueType == queueType }
                .forEach {
                    val gameType = it.gameType

                    val item = gameType.item
                            .name("&b" + gameType.displayName)
                            .lore("&7Click to get in the queue")

                    val clickableItem = ClickableItem.of(item, fun(_) {
                        it.addPlayer(player!!)

                        player.closeInventory()
                    })

                    contents!!.add(clickableItem)
                }
    }

    override fun update(player: Player?, contents: InventoryContents?) {
    }

}