package com.torres.valmir.kotlin_mvp_dagger2.remote.tv_show

import com.torres.valmir.kotlin_mvp_dagger2.model.ListCastCrew
import com.torres.valmir.kotlin_mvp_dagger2.model.ListTV
import com.torres.valmir.kotlin_mvp_dagger2.model.ListTrailers
import com.torres.valmir.kotlin_mvp_dagger2.model.TvShow

interface TvServiceApi {
    interface ServiceCallback <T> {
        fun onLoaded (response: T)
    }

    fun getPopularTV(callback: ServiceCallback<ListTV>, page: Int, language: String)

    fun getTopRatedTV(callback: ServiceCallback<ListTV>, page: Int, language: String)

    fun getTodaysTV(callback: ServiceCallback<ListTV>, page: Int, language: String)

    fun getTvShowId(callback: ServiceCallback<TvShow>, id: Int, language: String)

    fun getCastCrewTV(callback: ServiceCallback<ListCastCrew>, id: Int)

    fun getTvShow(callback: ServiceCallback<ListTV>, query: String, page: Int, language: String)

    fun getTrailer(callback: ServiceCallback<ListTrailers>, id: Int, language: String)
}