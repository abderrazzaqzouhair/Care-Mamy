package com.app.caremama.advice


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R

data class SavedPlaces(
    val title:String,
    val description:String,
    val city: String,
    val image:String,
    val saved:Boolean,
    val rating: Int
)


class PlacesAdapter(private val cardList: List<SavedPlaces>,val  context: Context) : RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {


        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardImage: ImageView = view.findViewById(R.id.imagePlace)
        val favoriteIcon: ImageView = view.findViewById(R.id.favoriteIcon)
        val cardTitle: TextView = view.findViewById(R.id.title)
        val cardDescription: TextView = view.findViewById(R.id.reviewText)
        val location: TextView = view.findViewById(R.id.location)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.places, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardItem = cardList[position]
        holder.cardTitle.text = cardItem.title
        holder.cardDescription.text = cardItem.description
        holder.ratingBar.rating = cardItem.rating.toFloat()
        holder.location.text = "${cardItem.city}, Morroco"
        holder.favoriteIcon.setOnClickListener {
            holder.favoriteIcon.setImageResource(R.drawable.girl)
            SavedPlacesPrefs(context).savePlace(cardItem.city)
        }
        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(cardItem.image, "drawable", context.packageName)
        if (resId != 0) {
            holder.cardImage.setImageResource(resId)
        } else {
            holder.cardImage.setImageResource(R.drawable.baseline_lock_24)
        }




//        holder.readMoreButton.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mayoclinichealthsystem.org/hometown-health/speaking-of-health/tips-for-drinking-more-water"))
//            it.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = cardList.size
}
