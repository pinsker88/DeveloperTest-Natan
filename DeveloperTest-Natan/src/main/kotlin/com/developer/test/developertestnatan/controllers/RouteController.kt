package com.developer.test.developertestnatan.controllers

import com.developer.test.developertestnatan.dtos.Route
import com.developer.test.developertestnatan.services.RouteResolver
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RouteController(
    private val routeResolver: RouteResolver
) {

    @GetMapping("/routing/{origin}/{destination}")
    fun findRoute(@PathVariable(name = "origin", required = true) originState: String,
                  @PathVariable(name = "destination", required = true) destinationState: String): Route {
        return routeResolver.resolveShortestPath(originState, destinationState)
    }
}
