package ru.solarmeister.tinkoffexam

import android.app.Application
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.solarmeister.tinkoffexam.model.api.RetrofitService
import ru.solarmeister.tinkoffexam.model.database.FavouriteFilmsDataBase
import ru.solarmeister.tinkoffexam.model.database.FavouritesFilmsDao

class TinkoffExamApp : Application() {

    lateinit var retrofitService: RetrofitService
    lateinit var filmsDao: FavouritesFilmsDao

    override fun onCreate() {
        super.onCreate()
        createRetrofit()
        createRoom()
    }

    private fun createRoom() {
        val room = Room.databaseBuilder(
            applicationContext,
            FavouriteFilmsDataBase::class.java,
            "favouritesFilms_table"
        ).build()
        filmsDao = room.filmDao()
    }

    private fun createRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java)
    }

}