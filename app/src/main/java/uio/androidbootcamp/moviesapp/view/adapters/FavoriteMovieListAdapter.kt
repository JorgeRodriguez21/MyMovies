package uio.androidbootcamp.moviesapp.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import uio.androidbootcamp.moviesapp.R
import uio.androidbootcamp.moviesapp.model.models.Movie

class FavoriteMovieListAdapter(private val moviesList: List<Movie>, private val onItemSelected: View.OnClickListener) :
        RecyclerView.Adapter<FavoriteMovieListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_movie_list_item, parent, false)
        val movieContainer = view.findViewById(R.id.rl_favorite_movie_container) as RelativeLayout
        movieContainer.setOnClickListener(onItemSelected)
        return ViewHolder(view)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById(R.id.text_view_movie_name) as TextView
        val movieContainer = view.findViewById(R.id.rl_favorite_movie_container) as RelativeLayout

    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = moviesList[position].title
        holder.movieContainer.tag = moviesList[position].id
    }
}