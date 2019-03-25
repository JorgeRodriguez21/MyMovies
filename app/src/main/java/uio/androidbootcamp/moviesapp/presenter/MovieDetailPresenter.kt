package uio.androidbootcamp.moviesapp.presenter

import android.content.Intent
import uio.androidbootcamp.moviesapp.model.models.Movie
import uio.androidbootcamp.moviesapp.model.repositories.MovieRepository
import uio.androidbootcamp.moviesapp.utils.Constants.MOVIE
import java.util.*

class MovieDetailPresenter(val view: MovieDetailView, val movieRepository: MovieRepository) : MovieRepository.SaveMovieOutput {

    lateinit var movie: Movie

    fun getMovie(intent: Intent) {
        movie = intent.getSerializableExtra(MOVIE) as Movie
        view.loadImage("https://image.tmdb.org/t/p/w200" + movie.posterPath)
        view.showMovieTitle(movie.title)
        view.showMovieDate(movie.releaseDate)
        view.showMovieDescription(movie.overview)
    }

    fun viewLoaded(){
        view.setActionsToScreenElements()
    }

    fun saveFavoriteMovie() {
        movieRepository.saveMovieInSharedPreferences(movie, this)
    }

    override fun isSaved(isSaved: Boolean) {
        if (isSaved) {
            view.showSavedSuccessfullyMessage()
        } else {
            view.showMovieIsAlreadySavedMessage()
        }
    }

    interface MovieDetailView {
        fun loadImage(path: String)
        fun showMovieTitle(title: String)
        fun showMovieDate(date: Date)
        fun showMovieDescription(description: String)
        fun showSavedSuccessfullyMessage()
        fun showMovieIsAlreadySavedMessage()
        fun setActionsToScreenElements()
    }
}