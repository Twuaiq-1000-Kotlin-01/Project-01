package com.tuwaiq.projectone

import android.app.Application
import com.tuwaiq.projectone.Repo.Repo

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Repo.initialize(this)
    }
}