package ru.solarmeister.tinkoffexam.filmdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.solarmeister.tinkoffexam.model.api.RetrofitService
import ru.solarmeister.tinkoffexam.filmdetails.filmdata.FilmDetails
import ru.solarmeister.tinkoffexam.listfilms.IProcessingError

class FilmViewModel : ViewModel() {

    private val _dataFilmDetails: MutableLiveData<FilmDetails> = MutableLiveData()
    val dataFilmDetails: LiveData<FilmDetails> = _dataFilmDetails

    fun getFilmDetails(retrofitService: RetrofitService, id: Int?, iProcessingError: IProcessingError) {
        if (id != null) {
            viewModelScope.launch {
                val response = retrofitService.getFilmDetails(id)
                response.enqueue(object : Callback<FilmDetails> {
                    override fun onResponse(
                        call: Call<FilmDetails>,
                        response: Response<FilmDetails>
                    ) {
                        if (response.isSuccessful) {
                            _dataFilmDetails.value = response.body()!!
                        }
                    }

                    override fun onFailure(call: Call<FilmDetails>, t: Throwable) {
                        iProcessingError.showErrorFragment()
                    }

                })
            }

        }
    }
}
