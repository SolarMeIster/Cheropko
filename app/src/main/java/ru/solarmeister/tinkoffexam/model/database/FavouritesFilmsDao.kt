package ru.solarmeister.tinkoffexam.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm
import ru.solarmeister.tinkoffexam.listfilms.listdata.VisibleOfFilm

@Dao
interface FavouritesFilmsDao {

    @Insert
    suspend fun insertFilm(film: ItemFilm)

    @Query("SELECT * FROM favouritesFilms_table")
    suspend fun getAllFavouritesFilms(): List<ItemFilm>

    @Query("SELECT filmId, visibilityOfFilm FROM favouritesFilms_table")
    suspend fun getVisibleOfFilms(): List<VisibleOfFilm>

    @Delete
    suspend fun deleteFavouriteFilm(film: ItemFilm)

}