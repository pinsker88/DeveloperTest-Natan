package com.developer.test.developertestnatan

import com.developer.test.developertestnatan.dtos.CountriesConfig
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.Resource
import java.nio.file.Files

@SpringBootApplication
class DeveloperTestNatanApplication {

    @Value("classpath:config.json")
    private lateinit var resource: Resource

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Bean(name = ["Countries"])
    fun getStates(): List<CountriesConfig> {
        val json = String(Files.readAllBytes(resource.file.toPath()))
        val typeRef = object : TypeReference<List<CountriesConfig>>() {}
        return mapper.readValue(json, typeRef)
    }

    @Bean(name = ["CountryMatrix"])
    fun getCountryMatrix(@Qualifier("Countries") countries: List<CountriesConfig>): Array<IntArray> {
        val numberOfCountries = countries.size
        val countryList = countries.map { it.cca3 }
        val mapOfCounties = countryList.withIndex().associate { it.value to it.index }

        val matrix = Array(numberOfCountries) {IntArray(numberOfCountries) {0} }
        for (country in countries) {
            val fromCountry = mapOfCounties[country.cca3] ?: continue
            val borders = country.borders ?: continue
            for (border in borders) {
                val toCountry = mapOfCounties[border] ?: continue
                matrix[fromCountry][toCountry] = 1
                matrix[toCountry][fromCountry] = 1
            }
        }
        return matrix
    }
}


fun main(args: Array<String>) {
    runApplication<DeveloperTestNatanApplication>(*args)
}
