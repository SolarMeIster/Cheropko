package ru.solarmeister.tinkoffexam.filmdetails.filmdata

import ru.solarmeister.tinkoffexam.listfilms.listdata.ListGenre

data class FilmDetails(val nameRu: String, val posterUrl: String, val description: String, val countries: List<ListCountries>, val genres: List<ListGenre>)
