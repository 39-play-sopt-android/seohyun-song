package org.sopt.play

import android.app.Application
import org.sopt.play.data.remote.ApiFactory

class PlaySoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiFactory.init(this)
    }
}
