package keown.cantrell.artgallery.main

import android.app.Application
import keown.cantrell.artgallery.models.PaintingJSONStore
import keown.cantrell.artgallery.models.PaintingStore

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var paintings: PaintingStore

    override fun onCreate() {
        super.onCreate()
        paintings = PaintingJSONStore(applicationContext)
        info("Painting started")
    }
}

