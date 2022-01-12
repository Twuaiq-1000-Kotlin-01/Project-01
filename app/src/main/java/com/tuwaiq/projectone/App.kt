package com.tuwaiq.projectone

import android.app.Application
import com.tuwaiq.projectone.repo.Repo

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Repo.initialize(this)
    }
}