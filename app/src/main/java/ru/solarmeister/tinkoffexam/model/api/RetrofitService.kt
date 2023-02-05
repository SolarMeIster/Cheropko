package ru.solarmeister.tinkoffexam.model.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.solarmeister.tinkoffexam.filmdetails.filmdata.FilmDetails
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilmResponse

interface RetrofitService {
    @Headers(
        "X-API-KEY: d648f3d7-5a92-4468-877d-131fe14cb08b",
        "Content-Type: application/json")
    @GET("./api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    fun getTopFilms(): Call<ItemFilmResponse>

    @Headers(
        "X-API-KEY: d648f3d7-5a92-4468-877d-131fe14cb08b",
        "Content-Type: application/json")
    @GET("/api/v2.2/films/{id}")
    fun getFilmDetails(@Path("id") id: Int): Call<FilmDetails>

}