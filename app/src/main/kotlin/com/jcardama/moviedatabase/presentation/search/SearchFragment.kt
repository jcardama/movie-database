package com.jcardama.moviedatabase.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcardama.moviedatabase.R
import com.jcardama.moviedatabase.core.usecase.State
import com.jcardama.moviedatabase.domain.entity.MovieEntity
import com.jcardama.moviedatabase.presentation.movies.MoviesViewModel
import com.jcardama.moviedatabase.core.extensions.changeListener
import com.jcardama.moviedatabase.core.extensions.fadeIn
import com.jcardama.moviedatabase.core.extensions.getTypedAdapter
import com.jcardama.moviedatabase.core.extensions.initAdapter
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
        ViewModelProvider(requireActivity(), viewModelFactory).get(MoviesViewModel::class.java)
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

        view.recycler_view.layoutManager = LinearLayoutManager(requireContext())

        view.recycler_view.initAdapter<MovieEntity>(R.layout.item_movie).onBindView { itemView, item, _ ->
            itemView.icon_image_view.setImageResource(when (item?.searched) {
                true -> R.drawable.ic_history
                else -> R.drawable.ic_search
            })
            itemView.title_text_view.text = item?.title
        }.onClick { _, item, _ ->
            viewModel.setMovieAsSearched(item?.id ?: 0L)
        }

        with(viewModel) {
            getSearchedMovies()

            view.search_edit_text.changeListener { _, s ->
                when {
                    s.isEmpty() -> getSearchedMovies()
                    else -> searchMoviesByTitle(s)
                }
            }

            setMovieAsSearched.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (it.state) {
                    State.SUCCESS -> {
                        findNavController().navigateUp()
                        moviesViewModel.movieId.value = setMovieAsSearchedParams.value?.id
                    }
                }
            }

            movies.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (it.state) {
                    State.SUCCESS -> view.recycler_view.getTypedAdapter<MovieEntity>().setItems(it.data?.toMutableList() ?: mutableListOf())
                }
            }

            searchedMovies.observe(viewLifecycleOwner) {
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (it.state) {
                    State.SUCCESS -> view.recycler_view.getTypedAdapter<MovieEntity>().setItems(it.data?.toMutableList() ?: mutableListOf())
                }
            }
        }
    }
}
