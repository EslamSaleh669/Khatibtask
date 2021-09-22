package com.example.arcgis.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.arcgis.di.*


class MyApplication() : Application() {
    var appComponent: AppComponent? = null

    private var instance: MyApplication? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .viewModelFactoryModule(ViewModelFactoryModule()).build()

    }

}