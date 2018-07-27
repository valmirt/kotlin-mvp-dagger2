package com.example.valmir.kotlinMvpDagger2

import android.app.Application
import com.example.valmir.kotlinMvpDagger2.di.component.ApplicationComponent
import com.example.valmir.kotlinMvpDagger2.di.component.DaggerApplicationComponent
import com.example.valmir.kotlinMvpDagger2.di.module.ActivityModule
import com.example.valmir.kotlinMvpDagger2.di.module.AndroidModule
import com.example.valmir.kotlinMvpDagger2.di.module.FragmentModule
import com.example.valmir.kotlinMvpDagger2.di.module.NetModule


class TMDBApplication: Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerApplicationComponent
                .builder()
                .androidModule(AndroidModule(this))
                .netModule(NetModule())
                .activityModule(ActivityModule())
                .fragmentModule(FragmentModule())
                .build()

        graph.inject(this)
    }
}