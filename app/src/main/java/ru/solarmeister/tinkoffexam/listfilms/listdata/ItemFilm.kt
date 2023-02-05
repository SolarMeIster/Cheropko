package ru.solarmeister.tinkoffexam.listfilms.listdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favouritesFilms_table")
data class ItemFilm (

    @PrimaryKey
    val filmId: Int,
    val nameRu: String,
    val genres: List<ListGenre>,
    val posterUrl: String,
    var visibilityOfFilm: Boolean = false
)


data class VisibleOfFilm(
    @ColumnInfo(name = "filmId") val filmId: Int?,
    @ColumnInfo(name = "visibilityOfFilm") val visibilityOfFilm: Boolean?
)