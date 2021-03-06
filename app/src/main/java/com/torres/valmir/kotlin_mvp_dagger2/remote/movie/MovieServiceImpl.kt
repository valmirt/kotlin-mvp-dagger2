package com.torres.valmir.kotlin_mvp_dagger2.remote.movie

import com.torres.valmir.kotlin_mvp_dagger2.TMDBApplication
import com.torres.valmir.kotlin_mvp_dagger2.model.ListCastCrew
import com.torres.valmir.kotlin_mvp_dagger2.model.ListMovies
import com.torres.valmir.kotlin_mvp_dagger2.model.ListTrailers
import com.torres.valmir.kotlin_mvp_dagger2.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MovieServiceImpl: MovieServiceApi {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        TMDBApplication.graph.inject(this)
    }

    override fun getMovie(callback: MovieServiceApi.ServiceCallback<ListMovies>, query: String, page: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .searchMovie(query = query,page = page, language = language)

        returningCall(callMovie, callback)
    }

    override fun getPopular(callback: MovieServiceApi.ServiceCallback<ListMovies>, page: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .popular(page = page, language = language)

        returningCall(callMovie, callback)
    }

    override fun getTopRated(callback: MovieServiceApi.ServiceCallback<ListMovies>, page: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .topRated(page = page, language = language)

        returningCall(callMovie, callback)
    }

    override fun getNowPlaying(callback: MovieServiceApi.ServiceCallback<ListMovies>, page: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .nowPlaying(page = page, language = language)

        returningCall(callMovie, callback)
    }

    override fun getMovieId(callback: MovieServiceApi.ServiceCallback<Movie>, id: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .searchMovieId(id = id, language = language)

        callMovie.enqueue(object: Callback<Movie>{
            override fun onFailure(call: Call<Movie>?, t: Throwable?) {
                callback.onLoaded(Movie())
            }

            override fun onResponse(call: Call<Movie>?, response: Response<Movie>?) {
                var result = Movie()
                response?.body()?.let {
                    result = it
                }
                response?.code()?.let {
                    result.code = it
                }
                callback.onLoaded(result)
            }
        })
    }

    override fun getSimilarMovies(callback: MovieServiceApi.ServiceCallback<ListMovies>, id: Int, page: Int, language: String) {
        val callMovie = retrofit
                .create(MovieEndpoint::class.java)
                .getSimilarMovies(id = id, page = page, language = language)

        returningCall(callMovie, callback)
    }

    override fun getCastCrew(callback: MovieServiceApi.ServiceCallback<ListCastCrew>, id: Int) {
        val call = retrofit
                .create(MovieEndpoint::class.java)
                .getCastandCrewMovie(id = id)

        call.enqueue(object : Callback<ListCastCrew>{
            override fun onFailure(call: Call<ListCastCrew>?, t: Throwable?) {
                callback.onLoaded(ListCastCrew())
            }

            override fun onResponse(call: Call<ListCastCrew>?, response: Response<ListCastCrew>?) {
                var result = ListCastCrew()
                response?.body()?.let {
                    result = it
                }
                response?.code()?.let {
                    result.code = it
                }
                callback.onLoaded(result)
            }
        })
    }

    override fun getTrailer(callback: MovieServiceApi.ServiceCallback<ListTrailers>, id: Int, language: String) {
        val call = retrofit
                .create(MovieEndpoint::class.java)
                .getTrailerMovie(id = id, language = language)

        call.enqueue(object : Callback<ListTrailers>{
            override fun onFailure(call: Call<ListTrailers>, t: Throwable) {
                callback.onLoaded(ListTrailers())
            }

            override fun onResponse(call: Call<ListTrailers>, response: Response<ListTrailers>) {
                var result = ListTrailers()

                response.body()?.let {
                    result = it
                }
                result.code = response.code()

                callback.onLoaded(result)
            }
        })
    }

    private fun returningCall(call: Call<ListMovies>, callback: MovieServiceApi.ServiceCallback<ListMovies>){
        call.enqueue(object: Callback<ListMovies>{
            override fun onFailure(call: Call<ListMovies>?, t: Throwable?) {
                callback.onLoaded(ListMovies())
            }

            override fun onResponse(call: Call<ListMovies>?, response: Response<ListMovies>?) {
                var result = ListMovies()
                response?.body()?.let {
                    result = it
                }
                response?.code()?.let {
                    result.code = it
                }
                callback.onLoaded(result)
            }
        })
    }
}