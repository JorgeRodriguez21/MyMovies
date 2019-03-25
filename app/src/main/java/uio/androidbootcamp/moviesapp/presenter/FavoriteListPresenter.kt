package uio.androidbootcamp.moviesapp.presenter

import android.view.View
import uio.androidbootcamp.moviesapp.model.models.Movie
import uio.androidbootcamp.moviesapp.model.repositories.MovieRepository

class FavoriteMovieListPresenter(val view: FavoriteMovieListView, private val movieRepository: MovieRepository) : MovieRepository.RetrieveMovieOutput {

    var movies = mutableListOf<Movie>()
        private set

    fun viewLoaded() {
        view.configureRecyclerView()
    }

    fun loadMovies() {
        movieRepository.getMoviesFromSharedPreferences(this)
    }

    fun onItemSelected(): View.OnClickListener {
        return View.OnClickListener { v ->
            val id = v?.tag as Long
            val movie = movies.firstOrNull { it.id == id }
            movie?.let { view.showMovieInformation(it) }
        }
    }

    override fun showMovies(movies: List<Movie>?) {
        when (movies) {
            null -> view.showMovieRetrieveError()
            else -> {
                this.movies.addAll(movies)
                view.updateRecyclerView()
            }
        }
    }

    interface FavoriteMovieListView {
        fun updateRecyclerView()
        fun showMovieRetrieveError()
        fun showMovieInformation(movie: Movie)
        fun configureRecyclerView()
    }
}