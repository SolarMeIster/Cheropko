package ru.solarmeister.tinkoffexam.listfilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import ru.solarmeister.tinkoffexam.NetworkErrorFragment
import ru.solarmeister.tinkoffexam.R
import ru.solarmeister.tinkoffexam.TinkoffExamApp
import ru.solarmeister.tinkoffexam.databinding.FragmentListFilmsBinding
import ru.solarmeister.tinkoffexam.filmdetails.FilmFragment
import ru.solarmeister.tinkoffexam.isNetworkConnected
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm
import ru.solarmeister.tinkoffexam.listfilms.listdata.VisibleOfFilm

class ListFilmsFragment : Fragment(), ListFilmsRecyclerViewAdapter.OnItemClickListener, IProcessingError {

    private lateinit var binding: FragmentListFilmsBinding

    private val listFilmsRecyclerViewAdapter: ListFilmsRecyclerViewAdapter by lazy {
        ListFilmsRecyclerViewAdapter(this, requireContext())
    }

    private val listFilmsViewModel: ListFilmsViewModel by viewModels()
    private var dataFromNet = emptyList<ItemFilm>()
    private var visibilities = mutableListOf<VisibleOfFilm>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listFilmsViewModel.getTopFilms((activity?.application as TinkoffExamApp).retrofitService, this)
        listFilmsViewModel.getVisibleOfFilms((activity?.application as TinkoffExamApp).filmsDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        listFilmsViewModel.dataOfFavouritesFilms.observe(viewLifecycleOwner) {
            listFilmsRecyclerViewAdapter.data = it
        }
        listFilmsViewModel.visibleOfFilm.observe(viewLifecycleOwner) {
            visibilities = it as MutableList<VisibleOfFilm>
        }
        listFilmsViewModel.dataOfTopFilm.observe(viewLifecycleOwner) {
            listFilmsRecyclerViewAdapter.data = it.films
            listFilmsRecyclerViewAdapter.visibilitiesFavouriteFilms = visibilities
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnFavourites.setOnClickListener {
            binding.txToolbar.text = getString(R.string.favorites)
            listFilmsViewModel.getFavoritesFilms((activity?.application as TinkoffExamApp).filmsDao)
        }
        binding.btnPopular.setOnClickListener {
            binding.txToolbar.text = getString(R.string.popular)
            listFilmsViewModel.getTopFilms((activity?.application as TinkoffExamApp).retrofitService, this)
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerViewPopularFilmsFragment.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewPopularFilmsFragment.adapter = listFilmsRecyclerViewAdapter
        }
    }

    override fun onClick(id: Int) {
        val filmFragment = FilmFragment()
        filmFragment.arguments = bundleOf("filmId" to id)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            replace(R.id.fragment_container, filmFragment)
        }
    }

    override fun onLongClickForAdd(itemFilm: ItemFilm): Boolean {
        listFilmsViewModel.addFavoriteFilm((activity?.application as TinkoffExamApp).filmsDao, itemFilm)
        return false
    }

    override fun onLongClickForDelete(itemFilm: ItemFilm): Boolean {
        listFilmsViewModel.deleteFavouriteFilm((activity?.application as TinkoffExamApp).filmsDao, itemFilm)
        visibilities.remove(VisibleOfFilm(itemFilm.filmId, true))
        return false
    }

    override fun showErrorFragment() {
        if (!requireContext().isNetworkConnected()) {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<NetworkErrorFragment>(R.id.fragment_container)
                remove(this@ListFilmsFragment)
            }
        }
    }
}