package keown.cantrell.artgallery.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaintingModel(
        var id: Long = 0,
        var title: String = "",
        var description: String = "",
        var Paint: String = "",
        var Other: String = "",
        var image: String = ""
) : Parcelable

