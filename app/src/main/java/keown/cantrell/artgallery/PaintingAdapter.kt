package keown.cantrell.artgallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import keown.cantrell.artgallery.helpers.readImageFromPath
import keown.cantrell.artgallery.models.PaintingModel
import kotlinx.android.synthetic.main.card_painting.view.*
import kotlinx.android.synthetic.main.card_painting.view.paintingTitle
import kotlinx.android.synthetic.main.card_painting.view.description
import kotlinx.android.synthetic.main.card_painting.view.Other
import kotlinx.android.synthetic.main.card_painting.view.Paint



interface PaintingListener {
    fun onPaintingClick(painting: PaintingModel)
}

class PaintingAdapter constructor(
        private var paintings: List<PaintingModel>,
        private val listener: PaintingListener
) : RecyclerView.Adapter<PaintingAdapter.MainHolder>() {

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val painting = paintings[holder.adapterPosition]
        holder.bind(painting, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.card_painting,
                        parent,
                        false
                )
        )
    }
    override fun getItemCount(): Int = paintings.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(painting: PaintingModel, listener: PaintingListener) {
            itemView.paintingTitle.text = painting.title
            itemView.description.text = painting.description
            itemView.Other.text = painting.Other
            itemView.Paint.text = painting.Paint
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, painting.image))
            itemView.setOnClickListener { listener.onPaintingClick(painting) }

        }
    }
    fun updateList(list: MutableList<PaintingModel>){
        paintings = list
        notifyDataSetChanged()

    }
}


