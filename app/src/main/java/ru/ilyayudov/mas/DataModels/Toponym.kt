package ru.ilyayudov.mas.DataModels

import java.util.SortedSet

data class Toponym(
        val name: String,
        val type: String,
        val sortedName: String = name
                .replace(" ", "")
                .replace("-", "")
                .toSortedSet()
                .toString()
) {
    override fun toString(): String {
        return name
    }
}