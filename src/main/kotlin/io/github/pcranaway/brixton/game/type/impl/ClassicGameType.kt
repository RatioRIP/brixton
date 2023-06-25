package io.github.pcranaway.brixton.game.type.impl

import io.github.pcranaway.brixton.game.type.Type
import io.github.pcranaway.brixton.utils.bleach
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ClassicGameType : Type {
    override val displayName: String
        get() = "Classic"

    override val item: ItemStack
        get() = ItemStack(Material.DIAMOND_SWORD)

    override fun applyKit(player: Player) {
        val inventory = player.inventory

        player.bleach()

        inventory.armorContents = arrayOf(
                ItemStack(Material.DIAMOND_HELMET),
                ItemStack(Material.DIAMOND_CHESTPLATE),
                ItemStack(Material.DIAMOND_LEGGINGS),
                ItemStack(Material.DIAMOND_BOOTS),
        ).reversedArray()

        inventory.setItem(0, ItemStack(Material.DIAMOND_SWORD))
        inventory.setItem(1, ItemStack(Material.BOW))
        inventory.setItem(2, ItemStack(Material.FISHING_ROD))
        inventory.setItem(3, ItemStack(Material.GOLDEN_APPLE, 16))
        inventory.setItem(4, ItemStack(Material.GOLDEN_CARROT, 64))
        inventory.setItem(8, ItemStack(Material.ARROW, 32))
    }
}
