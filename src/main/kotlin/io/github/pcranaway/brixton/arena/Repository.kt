package io.github.pcranaway.brixton.arena

object ArenaRepository {
    val arenas: MutableList<Arena> = arrayListOf()

    fun random(): Arena {
        return this.arenas.random()
    }
}
