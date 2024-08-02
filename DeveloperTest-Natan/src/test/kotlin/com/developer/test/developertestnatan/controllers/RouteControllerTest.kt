package com.developer.test.developertestnatan.controllers

import com.developer.test.developertestnatan.dtos.Route
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouteControllerTest(
    @Autowired private val restTemplate: TestRestTemplate
) {

    @LocalServerPort
    private val port: Int = 0

    @Test
    fun `test for happy flow - when there is route`() {
        val startCountry = "ISR"
        val endCountry = "UZB"
        val responseEntity = restTemplate.getForEntity(
            "http://localhost:$port/routing/$startCountry/$endCountry",
            Route::class.java
        )
        assertThat(responseEntity.statusCode.value()).isEqualTo(200)
        assertThat(responseEntity.body).isNotNull
        assertThat(responseEntity.body!!.route.first()).isEqualTo(startCountry)
        assertThat(responseEntity.body!!.route.last()).isEqualTo(endCountry)
    }

    @Test
    fun `test for bad flow - when there is no route return 400`() {
        val startCountry = "GBR"
        val endCountry = "UZB"
        val responseEntity = restTemplate.getForEntity(
            "http://localhost:$port/routing/$startCountry/$endCountry",
            String::class.java
        )
        assertThat(responseEntity.statusCode.value()).isEqualTo(400)
    }

}