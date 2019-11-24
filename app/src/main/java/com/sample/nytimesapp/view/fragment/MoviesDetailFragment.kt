package com.sample.nytimesapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sample.nytimesapp.R
import com.squareup.picasso.Picasso


class MoviesDetailFragment : Fragment() {

    lateinit var imageView: ImageView
    lateinit var titleText: TextView
    lateinit var summaryShortView: TextView
    lateinit var publicationDateView: TextView
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_detail_fragment, container, false)
        imageView = view.findViewById(R.id.image)
        titleText = view.findViewById(R.id.title)
        summaryShortView = view.findViewById(R.id.summary)
        publicationDateView = view.findViewById(R.id.publication_date)

        val title = arguments?.getString("title") //TODO Make these constants.
        val url = arguments?.getString("url")
        val summaryShort = arguments?.getString("summary_short")
        val publicationDate = arguments?.getString("publication_date")

        titleText.text = title
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder_big)
            .into(imageView)

        summaryShortView.setText(summaryShort)
        publicationDateView.setText(publicationDate)

        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        with((activity as AppCompatActivity).supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.title = getString(R.string.movie_detail_title)
        }
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
