package ru.solarmeister.tinkoffexam.model.database

import androidx.room.*
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm
import ru.solarmeister.tinkoffexam.listfilms.listdata.VisibleOfFilm

@Dao
interface FavouritesFilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: ItemFilm)

    @Query("SELECT * FROM favouritesFilms_table")
    suspend fun getAllFavouritesFilms(): List<ItemFilm>

    @Query("SELECT filmId, visibilityOfFilm FROM favouritesFilms_table")
    suspend fun getVisibleOfFilms(): List<VisibleOfFilm>

    @Delete
    suspend fun deleteFavouriteFilm(film: ItemFilm)

}