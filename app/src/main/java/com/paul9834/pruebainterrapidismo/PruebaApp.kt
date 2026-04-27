package com.paul9834.pruebainterrapidismo

import android.app.Application
import com.paul9834.pruebainterrapidismo.di.AppContainer
import com.paul9834.pruebainterrapidismo.di.DefaultAppContainer

class PruebaApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}