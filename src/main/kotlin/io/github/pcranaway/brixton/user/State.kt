package io.github.pcranaway.brixton.user

import io.github.pcranaway.brixton.user.State.*
import io.github.pcranaway.brixton.utils.bleach
import io.github.pcranaway.brixton.utils.lore
import io.github.pcranaway.brixton.utils.name
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Items {
    val unrankedSword = ItemStack(Material.STONE_SWORD)
            .name("&bUnranked Queue")
            .lore("&cRight-click to open the Unranked Queue menu")

    val rankedSword = ItemStack(Material.DIAMOND_SWORD)
            .name("&cRanked Queue")
            .lore("&cRight-click to open the Ranked Queue menu")

    val leaveQueue = ItemStack(Material.REDSTONE)
            .name("&cLeave Q")
            .lore("&cRight-click to leave the Queue")
}

fun Player.giveAppropriateItems(state: State) {
    this.bleach()

    val inventory = this.inventory

    when(state) {
        Lobby -> {
            inventory.setItem(0, Items.unrankedSword)
            inventory.setItem(1, Items.rankedSword)
        }
        InQueue -> {
            inventory.setItem(0, Items.leaveQueue)

            // todo: add item that shows the queue and the time the player has been queueing
        }
        Playing -> {}
    }
}