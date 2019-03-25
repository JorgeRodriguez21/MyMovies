package uio.androidbootcamp.moviesapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import uio.androidbootcamp.moviesapp.R
import uio.androidbootcamp.moviesapp.model.models.Movie
import uio.androidbootcamp.moviesapp.model.repositories.MovieRepository
import uio.androidbootcamp.moviesapp.model.repositories.SharedPreferencesRepository
import uio.androidbootcamp.moviesapp.presenter.FavoriteMovieListPresenter
import uio.androidbootcamp.moviesapp.utils.Constants
import uio.androidbootcamp.moviesapp.view.adapters.FavoriteMovieListAdapter

class FavoriteMovieListActivity : AppCompatActivity(), FavoriteMovieListPresenter.FavoriteMovieListView {

    private val sharedPreferencesRepository = SharedPreferencesRepository(this)
    private val movieRepository = MovieRepository(sharedPreferencesRepository)
    private val presenter = FavoriteMovieListPresenter(this, movieRepository)

    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: FavoriteMovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_favorite_movie_list)
        super.onCreate(savedInstanceState)
        presenter.viewLoaded()
    }

    override fun configureRecyclerView() {
        moviesAdapter = FavoriteMovieListAdapter(presenter.movies, presenter.onItemSelected())
        recyclerViewMovies = findViewById(R.id.recycler_view_favorite_movies)
        recyclerViewMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = moviesAdapter
        }
        presenter.loadMovies()
    }

    override fun updateRecyclerView() {
        moviesAdapter.notifyDataSetChanged()
    }

    override fun showMovieRetrieveError() {
        Toast.makeText(this, getString(R.string.movies_cannot_be_retrieved), Toast.LENGTH_LONG).show()
    }

    override fun showMovieInformation(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        startActivity(intent)
    }
}