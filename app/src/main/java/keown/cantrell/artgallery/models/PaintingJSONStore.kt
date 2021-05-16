package keown.cantrell.artgallery.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import keown.cantrell.artgallery.helpers.exists
import keown.cantrell.artgallery.helpers.read
import keown.cantrell.artgallery.helpers.write


import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "paintings.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<PaintingModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PaintingJSONStore : PaintingStore, AnkoLogger {

    val context: Context
    var paintings = mutableListOf<PaintingModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PaintingModel> {
        return paintings
    }

    override fun create(painting: PaintingModel) {
        painting.id = generateRandomId()
        paintings.add(painting)
        serialize()
    }


    override fun delete(painting: PaintingModel) {
        paintings.remove(painting)
        serialize()
    }
    override fun update(painting: PaintingModel) {
        val paintingsList = findAll() as ArrayList<PaintingModel>
        var foundPainting: PaintingModel? = paintingsList.find { p -> p.id == painting.id }
        if (foundPainting != null) {
            foundPainting.title = painting.title
            foundPainting.description = painting.description
            foundPainting.Other = painting.Other
            foundPainting.Paint = painting.Paint
            foundPainting.image = painting.image

        }
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(paintings, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        paintings = Gson().fromJson(jsonString, listType)
    }
}

