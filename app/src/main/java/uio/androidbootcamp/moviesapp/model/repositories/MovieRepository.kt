package uio.androidbootcamp.moviesapp.model.repositories

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import uio.androidbootcamp.moviesapp.model.models.Movie
import uio.androidbootcamp.moviesapp.utils.Constants

//Manejo de repositorios de base de datos
class MovieRepository(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    private val gson = createGsonObject()
    private val key = Constants.MOVIE

    fun getMoviesFromSharedPreferences(retrieveMovieOutput: RetrieveMovieOutput) {
        val movies = retrieveMovies()
        retrieveMovieOutput.showMovies(movies)
    }

    fun saveMovieInSharedPreferences(value: Movie, saveMovieOutput: SaveMovieOutput) {
        val moviesToSave = mutableListOf<Movie>()
        val moviesFromSharedPreferences = retrieveMovies()
        val filteredMovies = moviesFromSharedPreferences?.filter { it.id == value.id }
        if (filteredMovies.isNullOrEmpty()) {
            if (!moviesFromSharedPreferences.isNullOrEmpty()) {
                moviesToSave.addAll(moviesFromSharedPreferences)
            }
            moviesToSave.add(value)
            saveMoviesInSharedPreferences(moviesToSave)
            saveMovieOutput.isSaved(true)
        }else {
            saveMovieOutput.isSaved(false)
        }
    }

    private fun saveMoviesInSharedPreferences(value: List<Movie>) {
        val jsonValue = gson.toJson(value)
        sharedPreferencesRepository.saveStringInSharedPreferences(key, jsonValue)
    }

    private fun retrieveMovies(): List<Movie>? {
        val listType = object : TypeToken<ArrayList<Movie>>() {
        }.type
        val gsonValue = sharedPreferencesRepository.getStringFromSharedPreferences(key)
        return Gson().fromJson<List<Movie>>(gsonValue, listType)
    }

    private fun createGsonObject(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()
    }


    interface SaveMovieOutput {
        fun isSaved(isSaved: Boolean)
    }

    interface RetrieveMovieOutput {
        fun showMovies(movies: List<Movie>?)
    }
}