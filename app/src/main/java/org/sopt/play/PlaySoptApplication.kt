package org.sopt.play

import android.app.Application
import org.sopt.play.core.di.AppContainer

class PlaySoptApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}
