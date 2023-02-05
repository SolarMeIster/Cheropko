package ru.solarmeister.tinkoffexam.listfilms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.solarmeister.tinkoffexam.databinding.ItemFilmBinding
import ru.solarmeister.tinkoffexam.listfilms.listdata.ItemFilm
import ru.solarmeister.tinkoffexam.listfilms.listdata.VisibleOfFilm

class ListFilmsRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener,
    private val context: Context
) : RecyclerView.Adapter<ListFilmsRecyclerViewAdapter.ViewHolder>() {

    private lateinit var binding: ItemFilmBinding
    var data = emptyList<ItemFilm>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var visibilitiesFavouriteFilms = emptyList<VisibleOfFilm>()

    class ViewHolder(private val binding: ItemFilmBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(info: ItemFilm, onClickListener: OnItemClickListener, visible: Boolean) {

            with(binding) {
                if (visible) {
                    star.visibility = View.VISIBLE
                } else {
                    star.visibility = View.INVISIBLE
                }
                txNameOfFilm.text = info.nameRu
                txGenreOfFilm.text = info.genres.joinToString(", ") {
                    it.genre
                }
                Glide.with(context)
                    .load(info.posterUrl)
                    .into(imageOfFilm)
                cardView.setOnClickListener { onClickListener.onClick(info.filmId) }
                cardView.setOnLongClickListener {
                    info.visibilityOfFilm = !info.visibilityOfFilm
                    if (info.visibilityOfFilm) {
                        star.visibility = View.VISIBLE
                        onClickListener.onLongClickForAdd(info)
                    } else {
                        star.visibility = View.INVISIBLE
                        onClickListener.onLongClickForDelete(info)
                    }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemFilmBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var flag = false
        val info = data[position]
        if (visibilitiesFavouriteFilms.isNotEmpty()) {
            for (element in visibilitiesFavouriteFilms) {
                if (element.filmId == info.filmId) {
                    flag = true
                    break
                }
            }
            if (flag) {
                val index = visibilitiesFavouriteFilms.indexOf(VisibleOfFilm(info.filmId, true))
                val visibility = visibilitiesFavouriteFilms[index].visibilityOfFilm
                if (visibility != null) {
                    holder.bind(info, itemClickListener, visibility)
                }
            }
            holder.bind(info, itemClickListener, info.visibilityOfFilm)
        } else {
            holder.bind(info, itemClickListener, info.visibilityOfFilm)
        }
    }


    override fun getItemCount(): Int = data.size

    interface OnItemClickListener {
        fun onClick(id: Int)
        fun onLongClickForAdd(itemFilm: ItemFilm): Boolean
        fun onLongClickForDelete(itemFilm: ItemFilm): Boolean
    }
}