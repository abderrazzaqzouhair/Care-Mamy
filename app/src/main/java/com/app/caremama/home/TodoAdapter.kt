package com.app.caremama.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R

data class TodoItem(
    val title: String,
    val description: String,
    val date: String
)
class TodoAdapter(private val items: List<TodoItem>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.taskTitle)
        val descriptionText: TextView = itemView.findViewById(R.id.taskDescription)
        val dateText: TextView = itemView.findViewById(R.id.taskDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.titleText.text = item.title
        holder.descriptionText.text = item.description
        holder.dateText.text = item.date
    }

    override fun getItemCount(): Int = items.size
}
