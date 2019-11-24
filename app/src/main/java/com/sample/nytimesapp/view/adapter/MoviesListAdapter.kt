package com.sample.nytimesapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sample.nytimesapp.R
import com.sample.nytimesapp.databinding.ListItemMovieBinding
import com.sample.nytimesapp.model.Result
import com.squareup.picasso.Picasso

class MoviesListAdapter :
    RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Result)
    }

    var moviesList: List<Result?> = ArrayList()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val listItemMovieBinding: ListItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.getContext()),
            R.layout.list_item_movie, viewGroup, false
        )
        return MovieViewHolder(listItemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Result? = moviesList[position]
        holder.listItemMovieBinding.movie = movie
        holder.listItemMovieBinding.onClickListener = listener
    }

    fun setItemsAndListener(
        moviesList: List<Result?>,
        listener: OnItemClickListener
    ) {
        this.moviesList = moviesList
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner class MovieViewHolder(val listItemMovieBinding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(listItemMovieBinding.getRoot())

    object ImageBindingAdapter {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(view)
            }
        }
    }


}
