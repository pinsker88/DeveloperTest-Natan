package com.developer.test.developertestnatan.dtos

data class CountriesConfig(
    val cca3: String,
    val borders: List<String>?
)

data class Route(val route: List<String>)
