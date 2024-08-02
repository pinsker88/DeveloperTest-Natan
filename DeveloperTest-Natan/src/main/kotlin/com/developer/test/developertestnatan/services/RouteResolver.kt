package com.developer.test.developertestnatan.services

import com.developer.test.developertestnatan.dtos.CountriesConfig
import com.developer.test.developertestnatan.dtos.Route
import com.developer.test.developertestnatan.exceptions.LandPathNotFoundException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.*

@Service
class RouteResolver(
    @Qualifier("Countries") private val countries: List<CountriesConfig>,
    @Qualifier("CountryMatrix") private val adjacentMatrixVariable: Array<IntArray>
) {
    private lateinit var countryIndeces: Map<String, Int>
    private lateinit var countryCodes: List<String>

    @PostConstruct
    private fun initialization() {
        countryCodes = countries.map { it.cca3 }
        countryIndeces = countryCodes.withIndex().associate { it.value to it.index }
    }

    fun resolveShortestPath(startPoint: String,
                            endPoint: String): Route {
        val path = findShortestPath(startPoint, endPoint)
            ?: throw LandPathNotFoundException("There is no land route from $startPoint to $endPoint")
        return Route(path)
    }


    private fun findShortestPath(startPoint: String,
                                 endPoint: String): List<String>? {
        val matrixSize = adjacentMatrixVariable.size
        val startIdx = countryIndeces[startPoint] ?: return null
        val endIdx = countryIndeces[endPoint] ?: return null

        val queue: Queue<Int> = LinkedList()
        val distances = IntArray(matrixSize) { Int.MAX_VALUE }
        val previous = IntArray(matrixSize) { -1 }
        val visited = BooleanArray(matrixSize)

        distances[startIdx] = 0
        queue.add(startIdx)

        while (queue.isNotEmpty()) {
            val u = queue.poll()
            if (visited[u]) continue
            visited[u] = true

            for (v in adjacentMatrixVariable[u].indices) {
                if (adjacentMatrixVariable[u][v] == 1 && !visited[v]) {
                    if (distances[u] + 1 < distances[v]) {
                        distances[v] = distances[u] + 1
                        previous[v] = u
                        queue.add(v)
                    }
                }
            }
        }

        if (distances[endIdx] == Int.MAX_VALUE) return null

        val path = mutableListOf<String>()
        var endIndexIterator = endIdx
        while (endIndexIterator != -1) {
            path.add(countryCodes[endIndexIterator])
            endIndexIterator = previous[endIndexIterator]
        }
        path.reverse()
        return path
    }
}