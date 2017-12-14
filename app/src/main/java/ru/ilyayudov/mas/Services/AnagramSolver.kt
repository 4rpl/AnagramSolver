package ru.ilyayudov.mas.Services

import ru.ilyayudov.mas.DataModels.Toponym

class AnagramSolver {
    fun Solve(all: List<Toponym>, query: String, full: Boolean) : List<Toponym> {

        var sortedQuery = formatName(query)
        var q = all
        val sortedName =

        if(full) {
            q = q.filter { sortedQuery == formatName(it.name) }
        } else {
            q = q.filter { contains(sortedQuery, formatName(it.name)) }
        }

        return q
    }

    private fun contains(sortedQuery: String, sortedInput: String) : Boolean {

        if(sortedInput.length < sortedQuery.length) {
            return false
        }

        var i = 0
        var j = 0
        while(i < sortedQuery.length && j < sortedInput.length) {
            if(sortedQuery[i] > sortedInput[j]) {
                ++j
            } else if(sortedQuery[i] < sortedInput[j]) {
                return@contains false
            } else {
                ++i
                ++j
            }
        }

        return true
    }

    private fun formatName(name: String) : String {
        return String(name
                .replace(" ", "")
                .replace("-", "")
                .replace(".", "")
                .toUpperCase()
                .toCharArray()
                .sortedArray())
    }
}