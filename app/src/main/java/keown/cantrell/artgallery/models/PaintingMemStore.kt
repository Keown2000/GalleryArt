package keown.cantrell.artgallery.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class PaintingMemStore : PaintingStore, AnkoLogger {

    val paintings = ArrayList<PaintingModel>()

    override fun findAll(): List<PaintingModel> {
        return paintings
    }

    override fun create(painting: PaintingModel) {
        paintings.add(painting)
        logAll();
    }
    override fun update(painting: PaintingModel) {
        var foundPainting: PaintingModel? = paintings.find { p -> p.id == painting.id }
        if (foundPainting != null) {
            foundPainting.title = painting.title
            foundPainting.description = painting.description
            foundPainting.Paint = painting.Paint
            foundPainting.Other = painting.Other
            foundPainting.image = painting.image
            logAll()
        }
    }
    override fun delete(painting: PaintingModel) {
        paintings.remove(painting)
    }
    fun logAll() {
        paintings.forEach{ info("${it}") }
    }
}

