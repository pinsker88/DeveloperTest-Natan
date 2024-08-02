package com.developer.test.developertestnatan

import com.developer.test.developertestnatan.exceptions.LandPathNotFoundException
import com.developer.test.developertestnatan.services.RouteResolver
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ConfigFileReadTest {

    @Autowired
    private lateinit var routeResolver: RouteResolver


    @Test
    fun `check happy flow - path is found`() {
        val startCountry = "ISR"
        val endCountry = "UZB"

        val path = routeResolver.resolveShortestPath(startCountry, endCountry)

        assertThat(path).isNotNull
        assertThat(path.route.size).isEqualTo(6)
        assertThat(path.route[0]).isEqualTo(startCountry)
        assertThat(path.route.last()).isEqualTo(endCountry)
    }

    @Test
    fun `check bad flow - can't find the path`() {
        val startCountry = "GBR"
        val endCountry = "UZB"

        assertThrows<LandPathNotFoundException> {
            routeResolver.resolveShortestPath(startCountry, endCountry)
        }
    }
}