package com.app.caremama.advice


import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


data class Tip(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val rating: Int,
    val category: String,
    val trimester: String,
    val source: String,
    val isEmergency: Boolean
)


class TipAdapter(private val tips: List<Tip>) : RecyclerView.Adapter<TipAdapter.TipViewHolder>() {

    class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageTip: ImageView = itemView.findViewById(R.id.imageTip)
        val imageProgress: ProgressBar = itemView.findViewById(R.id.imageProgress)
        val titleTip: TextView = itemView.findViewById(R.id.titleTip)
        val descTip: TextView = itemView.findViewById(R.id.descTip)
        val btnReadMore: Button = itemView.findViewById(R.id.btnReadMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.places, parent, false)
        return TipViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val tip = tips[position]
        holder.titleTip.text = tip.title
        holder.descTip.text = tip.description

        holder.imageProgress.visibility = View.VISIBLE
        holder.btnReadMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whattoexpect.com/pregnancy/"))
            it.context.startActivity(intent)
        }

        Glide.with(holder.itemView.context)
            .load(tip.image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgress.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    holder.imageProgress.visibility = View.GONE
                    return false
                }
            })
            .into(holder.imageTip)
    }

    override fun getItemCount(): Int = tips.size
}
































