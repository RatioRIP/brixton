package io.github.pcranaway.brixton.arena

import io.github.pcranaway.brixton.utils.colorize
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.sender
import revxrsal.commands.command.CommandActor

object ArenaCommands {

    @Command("arena new")
    @CommandPermission("brixton.admin")
    fun new(actor: CommandActor, name: String ) {
        val exists = ArenaRepository.arenas.any { it.name.equals(name, ignoreCase = true) }

        if(exists) {
            actor.sender.sendMessage("&cThis arena already exists.".colorize())
            return
        }

        val location = (actor.sender as Player).location

        val arena = Arena(name, location, location)
        ArenaRepository.arenas.add(arena)

        actor.sender.sendMessage("&bArena created.".colorize())
    }

    @Command("arena delete")
    @CommandPermission("brixton.admin")
    fun delete(actor: CommandActor, name: String ) {
        val exists = ArenaRepository.arenas.any { it.name.equals(name, ignoreCase = true) }

        if(!exists) {
            actor.sender.sendMessage("&cThis arena doesn't exist.".colorize())
            return
        }

        val arena = ArenaRepository.arenas.firstOrNull { it.name.equals(name, ignoreCase = true ) }!!
        ArenaRepository.arenas.remove(arena)

        actor.sender.sendMessage("&cArena deleted.".colorize())
    }

    @Command("arena seta")
    @CommandPermission("brixton.admin")
    fun setA(actor: CommandActor, name: String ) {
        val exists = ArenaRepository.arenas.any { it.name.equals(name, ignoreCase = true) }

        if(!exists) {
            actor.sender.sendMessage("&cThis arena doesn't exist.".colorize())
            return
        }

        val location = (actor.sender as Player).location
        val arena = ArenaRepository.arenas.firstOrNull { it.name.equals(name, ignoreCase = true ) }!!

        arena.locationA = location

        actor.sender.sendMessage("&cArena modified.".colorize())
    }

    @Command("arena setb")
    @CommandPermission("brixton.admin")
    fun setB(actor: CommandActor, name: String ) {
        val exists = ArenaRepository.arenas.any { it.name.equals(name, ignoreCase = true) }

        if(!exists) {
            actor.sender.sendMessage("&cThis arena doesn't exist.".colorize())
            return
        }

        val location = (actor.sender as Player).location
        val arena = ArenaRepository.arenas.firstOrNull { it.name.equals(name, ignoreCase = true ) }!!

        arena.locationB = location

        actor.sender.sendMessage("&cArena modified.".colorize())
    }

    @Command("arena list")
    @CommandPermission("brixton.admin")
    fun list(actor: CommandActor) {
        val exists = ArenaRepository.arenas.forEach {
            actor.sender.sendMessage("&c- &b${it.name}".colorize())
        }
    }

}