package io.github.pcranaway.brixton.queue

import io.github.pcranaway.brixton.utils.colorize

enum class Type(val displayName: String) {
    Unranked("&bUnranked".colorize()),
    Ranked("&cRanked".colorize());
}