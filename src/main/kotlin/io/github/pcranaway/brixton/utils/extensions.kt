package io.github.pcranaway.brixton.utils

import io.github.pcranaway.brixton.user.User
import io.github.pcranaway.brixton.user.UserRepository
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.entity.Player

fun Player.bleach() {
    this.inventory.contents = emptyArray()
    this.inventory.armorContents = emptyArray()

    this.health = 20.0
    this.foodLevel = 20

    this.player.activePotionEffects.forEach {
        this.removePotionEffect(it.type)
    }

    this.gameMode = GameMode.SURVIVAL
}

fun String.colorize(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun List<String>.colorize(): List<String> {
   return this.map(String::colorize).toList()
}

fun Player.toUser(): User? {
    return UserRepository.users.firstOrNull { it.player == this }
}
