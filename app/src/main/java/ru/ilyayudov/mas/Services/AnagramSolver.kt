package ru.ilyayudov.mas.Services

import ru.ilyayudov.mas.DataModels.Toponym
import ru.ilyayudov.mas.DataModels.ToponymType

class AnagramSolver {
    fun Solve(all: List<Toponym>, query: String, type: ToponymType, full: Boolean) : List<Toponym> {

        var sortedQuery = query.toSortedSet()
        var q = all

        if(full) {
            q = q.filter { it.sortedName == sortedQuery }
        } else {
            q = q.filter { it.sortedName.containsAll(sortedQuery) }
        }

        if(type != ToponymType.NotSet) {
            q = q.filter { it.type == type }
        }

        return q
    }
}