package ru.solarmeister.tinkoffexam.filmdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.solarmeister.tinkoffexam.NetworkErrorFragment
import ru.solarmeister.tinkoffexam.R
import ru.solarmeister.tinkoffexam.TinkoffExamApp
import ru.solarmeister.tinkoffexam.databinding.FragmentFilmBinding
import ru.solarmeister.tinkoffexam.isNetworkConnected
import ru.solarmeister.tinkoffexam.listfilms.IProcessingError

class FilmFragment : Fragment(), IProcessingError {

    private lateinit var binding: FragmentFilmBinding
    private val filmViewModel: FilmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!requireContext().isNetworkConnected()) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<NetworkErrorFragment>(R.id.fragment_container)
                remove(this@FilmFragment)
            }
        }
        val id = arguments?.getInt("filmId")
        filmViewModel.getFilmDetails((activity?.application as TinkoffExamApp).retrofitService, id, this)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmBinding.inflate(inflater, container, false)
        filmViewModel.dataFilmDetails.observe(viewLifecycleOwner) { filmDetails ->
            with(binding) {
                Glide.with(requireContext())
                    .load(filmDetails.posterUrl)
                    .into(posterOfFragmentFilm)
                titleOfFragmentFilm.text = filmDetails.nameRu
                descriptionOfFragmentFilm.text = filmDetails.description
                genreOfFragmentFilm.text = getString(R.string.genres) + filmDetails.genres.joinToString(", ") {
                    it.genre
                }
                countriesOfFragmentFilm.text = getString(R.string.counties) + filmDetails.countries.joinToString(", ") {
                    it.country
                }
            }
        }
        return binding.root
    }

    override fun showErrorFragment() {
        if (!requireContext().isNetworkConnected()) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<NetworkErrorFragment>(R.id.fragment_container)
                remove(this@FilmFragment)
            }
        }
    }
}