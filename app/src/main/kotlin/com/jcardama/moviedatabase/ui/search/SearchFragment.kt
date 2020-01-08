package com.jcardama.moviedatabase.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.domain.model.Movie
import com.jcardama.moviedatabase.ui.movies.MoviesViewModel
import com.jcardama.moviedatabase.util.adapter.RecyclerViewAdapterUtil
import com.jcardama.moviedatabase.util.extension.changeListener
import com.jcardama.moviedatabase.util.extension.fadeIn
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_search.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import javax.inject.Inject

class SearchFragment : DaggerDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private val moviesViewModel: MoviesViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(MoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_dialog_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.container_layout.setOnClickListener { dismiss() }

        view.card_view.fadeIn()

        view.close_image_button.setOnClickListener { dismiss() }

        view.recycler_view.layoutManager = LinearLayoutManager(context!!)

        RecyclerViewAdapterUtil.Builder<Movie>(context!!, R.layout.item_search).bindView { itemView, item, _ ->
            itemView.icon_image_view.setImageResource(when (item?.searched) {
                true -> R.drawable.ic_history
                else -> R.drawable.ic_search
            })
            itemView.title_text_view.text = item?.title
        }.setOnClickListener { _, item, _ ->
            viewModel.save(item?.apply {
                searched = true
                timestamp = System.currentTimeMillis()
            })
            findNavController().navigateUp()
            moviesViewModel.movie.value = item
        }.into(view.recycler_view)

        with(viewModel) {
            getSearchedMovies()

            view.search_edit_text.changeListener { _, s ->
                when {
                    s.isEmpty() -> getSearchedMovies()
                    else -> searchMoviesByTitle(s)
                }
            }

            movies.observe(this@SearchFragment, Observer { results ->
                val movies = results?.sortedWith(compareByDescending<Movie> {
                    it.timestamp
                }.thenByDescending {
                    it.searched
                })?.toMutableList()
                (view.recycler_view.adapter as RecyclerViewAdapterUtil<Movie>).setItems(movies ?: mutableListOf())
            })
        }
    }
}
