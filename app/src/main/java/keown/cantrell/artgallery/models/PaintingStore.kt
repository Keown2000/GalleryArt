package keown.cantrell.artgallery.models

interface PaintingStore {
    fun findAll(): List<PaintingModel>
    fun create(painting: PaintingModel)
    fun update(painting: PaintingModel)
    fun delete(painting: PaintingModel)
}

