package com.torres.valmir.kotlinMvpDagger2.ui.main.detail.info

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.torres.valmir.kotlinMvpDagger2.R
import com.torres.valmir.kotlinMvpDagger2.TMDBApplication
import com.torres.valmir.kotlinMvpDagger2.model.ListMovies
import com.torres.valmir.kotlinMvpDagger2.model.ListTV
import com.torres.valmir.kotlinMvpDagger2.model.Movie
import com.torres.valmir.kotlinMvpDagger2.model.TvShow
import com.torres.valmir.kotlinMvpDagger2.remote.movie.MovieServiceApi
import com.torres.valmir.kotlinMvpDagger2.remote.tvShow.TvServiceApi
import com.torres.valmir.kotlinMvpDagger2.util.Constants
import com.torres.valmir.kotlinMvpDagger2.util.Constants.Companion.MOVIE_OBJECT
import javax.inject.Inject

class InfoPresenter: InfoContract.Presenter {
    private lateinit var view: InfoContract.View

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var apiMovie: MovieServiceApi

    init {
        TMDBApplication.graph.inject(this)
    }

    override fun attach(view: InfoContract.View) {
        this.view = view
    }

    override fun getSimilarMovies(id: Int, page: Int, language: String) {
        apiMovie.getSimilarMovies(object : MovieServiceApi.ServiceCallback<ListMovies>{
            override fun onLoaded(response: ListMovies) {
                when(response.code){
                    200 -> {
                        if (page == 1) view.successResponseMovie(response.movieList)
                        else view.successResponseMorePagesMovie(response.movieList)
                    }
                    404 -> view.errorResponse(context.getString(R.string.error_404))
                    500 -> view.errorResponse(context.getString(R.string.error_500))
                    503 -> view.errorResponse(context.getString(R.string.error_503))
                    504 -> view.errorResponse(context.getString(R.string.error_504))
                    else -> view.errorResponse(context.getString(R.string.error_connection))
                }
            }
        }, id, page, language)
    }

    override fun getDetails(id: Int, language: String) {
        apiMovie.getMovieId(object : MovieServiceApi.ServiceCallback<Movie>{
            override fun onLoaded(response: Movie) {
                when(response.code){
                    200 -> view.responseDetailMovie(response)
                    404 -> view.errorResponse(context.getString(R.string.error_404))
                    500 -> view.errorResponse(context.getString(R.string.error_500))
                    503 -> view.errorResponse(context.getString(R.string.error_503))
                    504 -> view.errorResponse(context.getString(R.string.error_504))
                    else -> view.errorResponse(context.getString(R.string.error_connection))
                }
            }
        }, id, language)
    }

    override fun swapActivity(activity: AppCompatActivity, movie: Movie?, tv: TvShow?) {
        val intent = Intent(context, activity::class.java)
        movie?.let { intent.putExtra(MOVIE_OBJECT, it) }
        tv?.let { intent.putExtra(Constants.TVSHOW_OBJECT, it) }
        context.startActivity(intent)
    }
}