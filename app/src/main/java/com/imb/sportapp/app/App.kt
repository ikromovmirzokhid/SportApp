package com.imb.sportapp.app

import androidx.multidex.MultiDexApplication
import com.imb.sportapp.DI
import com.imb.sportapp.di.components.DaggerNetworkComponent
import com.orhanobut.hawk.Hawk

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        initDI()
    }

    private fun initDI(){
        DI.networkComponent = DaggerNetworkComponent.factory().buildNetworkComponent(this)
    }
}