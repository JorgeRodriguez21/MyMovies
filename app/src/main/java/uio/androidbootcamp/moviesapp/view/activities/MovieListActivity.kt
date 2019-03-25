package uio.androidbootcamp.moviesapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import uio.androidbootcamp.moviesapp.R
import uio.androidbootcamp.moviesapp.model.models.Movie
import uio.androidbootcamp.moviesapp.model.services.MovieListService
import uio.androidbootcamp.moviesapp.model.services.MovieRestServices
import uio.androidbootcamp.moviesapp.model.services.RetrofitInstance
import uio.androidbootcamp.moviesapp.presenter.MovieListPresenter
import uio.androidbootcamp.moviesapp.utils.Constants
import uio.androidbootcamp.moviesapp.view.adapters.MovieListAdapter
import android.view.Menu
import android.view.MenuItem


class MovieListActivity : AppCompatActivity(), MovieListPresenter.MovieListView {

    private val presenter = MovieListPresenter(this)

    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_movie_list)
        super.onCreate(savedInstanceState)
        presenter.viewLoaded()
    }

    override fun configureRecyclerView() {
        moviesAdapter = MovieListAdapter(presenter.movies, presenter.onItemSelected())
        recyclerViewMovies = findViewById(R.id.recycler_view_movies)
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

    override fun getMovieListService(): MovieListService {
        val movieRestService: MovieRestServices = RetrofitInstance.retrofit.create(MovieRestServices::class.java)
        return MovieListService(presenter, movieRestService)
    }

    override fun showMovieInformation(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(Constants.MOVIE, movie)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_navigate_to_find_movie -> {
                startActivity(Intent(applicationContext, FindMovieActivity::class.java))
                true
            }
            R.id.menu_navigate_to_favorite_movies_list -> {
                startActivity(Intent(applicationContext, FavoriteMovieListActivity::class.java))
                true
            }
            else -> {
                startActivity(Intent(applicationContext, MovieListActivity::class.java))
                finish()
                true
            }
        }
    }

}