package ru.solarmeister.tinkoffexam.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm

@Database(entities = [ItemFilm::class], version = 1)
@TypeConverters(GenresConverter::class)
abstract class FavouriteFilmsDataBase : RoomDatabase()  {
    abstract fun filmDao(): FavouritesFilmsDao
}