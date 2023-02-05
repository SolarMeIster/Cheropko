package ru.solarmeister.tinkoffexam.model.database

import androidx.room.TypeConverter
import ru.solarmeister.tinkoffexam.listfilms.listdata.ListGenre

class GenresConverter {

    @TypeConverter
    fun fromGenres(genres: List<ListGenre>): String {
        return genres.joinToString(", ") { it.genre }
    }

    @TypeConverter
    fun toGenres(data: String): List<ListGenre> {
        val result = mutableListOf<ListGenre>()
        val list = data.split(", ")
        list.forEach {
            result.add(ListGenre(it))
        }
        return result
    }

}