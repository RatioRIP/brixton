package io.github.pcranaway.brixton.game.type

import io.github.pcranaway.brixton.game.type.impl.ClassicGameType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

// todo: add weird gamemode with fireballs and stuff that would be kinda coool idk think of something

val TYPES: List<Type> = listOf(
        ClassicGameType
)

interface Type {
    val displayName: String
    val item: ItemStack

    fun applyKit(player: Player)
}