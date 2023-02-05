package ru.solarmeister.tinkoffexam.listfilms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilmResponse
import ru.solarmeister.tinkoffexam.model.api.RetrofitService
import ru.solarmeister.tinkoffexam.model.database.FavouritesFilmsDao
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm
import ru.solarmeister.tinkoffexam.listfilms.listdata.VisibleOfFilm

class ListFilmsViewModel : ViewModel() {

    private val _dataOfTopFilms: MutableLiveData<ItemFilmResponse> = MutableLiveData()
    val dataOfTopFilm: LiveData<ItemFilmResponse> = _dataOfTopFilms

    private val _dataOfFavouritesFilms = MutableLiveData<List<ItemFilm>>()
    val dataOfFavouritesFilms: LiveData<List<ItemFilm>> = _dataOfFavouritesFilms

    private val _visibleOfFilm: MutableLiveData<List<VisibleOfFilm>> = MutableLiveData()
    val visibleOfFilm: LiveData<List<VisibleOfFilm>> = _visibleOfFilm

    fun getTopFilms(retrofitService: RetrofitService, iProcessingError: IProcessingError) {
        viewModelScope.launch {
            val response = retrofitService.getTopFilms()
            response.enqueue(object : Callback<ItemFilmResponse> {
                override fun onResponse(
                    call: Call<ItemFilmResponse>,
                    response: Response<ItemFilmResponse>
                ) {
                    if (response.isSuccessful) {
                        _dataOfTopFilms.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<ItemFilmResponse>, t: Throwable) {
                    iProcessingError.showErrorFragment()
                }

            })
        }
    }

    fun getFavoritesFilms(filmsDao: FavouritesFilmsDao) {
        viewModelScope.launch {
            _dataOfFavouritesFilms.value = filmsDao.getAllFavouritesFilms()
        }
    }

    fun getVisibleOfFilms(filmsDao: FavouritesFilmsDao) {
        viewModelScope.launch {
            _visibleOfFilm.value = filmsDao.getVisibleOfFilms()
        }
    }


    fun addFavoriteFilm(filmsDao: FavouritesFilmsDao, itemFilm: ItemFilm) {
       viewModelScope.launch {
           filmsDao.insertFilm(itemFilm)
       }
    }

    fun deleteFavouriteFilm(filmsDao: FavouritesFilmsDao, itemFilm: ItemFilm) {
        viewModelScope.launch {
            filmsDao.deleteFavouriteFilm(itemFilm)
        }
    }
}