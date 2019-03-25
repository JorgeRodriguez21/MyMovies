package uio.androidbootcamp.moviesapp.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_movie_detail.*
import uio.androidbootcamp.moviesapp.R
import uio.androidbootcamp.moviesapp.model.repositories.MovieRepository
import uio.androidbootcamp.moviesapp.model.repositories.SharedPreferencesRepository
import uio.androidbootcamp.moviesapp.presenter.MovieDetailPresenter
import java.util.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailPresenter.MovieDetailView {

    private val sharedPreferencesRepository = SharedPreferencesRepository(this)
    private val movieRepository = MovieRepository(sharedPreferencesRepository)
    private val presenter: MovieDetailPresenter = MovieDetailPresenter(this, movieRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_movie_detail)
        super.onCreate(savedInstanceState)
        presenter.viewLoaded()
        presenter.getMovie(intent)
    }

    override fun loadImage(path: String) {
        image_view_movie_poster.setImageURI(path)
    }

    override fun showMovieTitle(title: String) {
        text_view_movie_title.text = title
    }

    override fun showMovieDate(date: Date) {
        text_view_movie_release_date.text = date.toString()
    }

    override fun showMovieDescription(description: String) {
        text_view_movie_description.text = description
    }

    override fun showMovieIsAlreadySavedMessage() {
        Toast.makeText(this, R.string.movie_not_saved, Toast.LENGTH_LONG).show()
    }

    override fun showSavedSuccessfullyMessage() {
        Toast.makeText(this, R.string.movie_saved, Toast.LENGTH_LONG).show()
    }

    override fun setActionsToScreenElements() {
        fab_save_movie.setOnClickListener { presenter.saveFavoriteMovie() }
    }
}
