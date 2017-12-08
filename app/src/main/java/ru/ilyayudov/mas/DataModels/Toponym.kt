package ru.ilyayudov.mas.DataModels

import java.util.SortedSet

data class Toponym(val name: String, val type: ToponymType, val sortedName: SortedSet<Char>, val uri: String) {

    override fun toString(): String {
        return name
    }
}