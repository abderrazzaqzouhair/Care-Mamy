package com.app.caremama.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R
import com.google.android.material.textfield.TextInputEditText

class WeightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weight, container, false)

        // Sample data - replace with your actual data
        val weightEntries = mutableListOf(
            WeightEntry("date", "weight", "gain", "week"),
            WeightEntry("22-04-2024", "64 kg", "+ 3 kg", "8"),
            WeightEntry("21-04-2024", "61 kg", "+ 1 Kg", "5")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.weightRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = WeightAdapter(weightEntries)

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val weightInput = view.findViewById<TextInputEditText>(R.id.weightEditText).text.toString()
            if (weightInput.isNotEmpty()) {
                val lastEntry = weightEntries.last()
                val newEntry = WeightEntry(
                    date = lastEntry.date,
                    weight = weightInput,
                    gain = lastEntry.gain,
                    week = lastEntry.week)
                weightEntries.add(newEntry)
                recyclerView.adapter?.notifyItemInserted(weightEntries.size - 1)
            }
        }

        return view
    }
}

class WeightAdapter(private val weightEntries: List<WeightEntry>) : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    class WeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val weightTextView: TextView = itemView.findViewById(R.id.weightTextView)
        val gainTextView: TextView = itemView.findViewById(R.id.gainTextView)
        val weekTextView: TextView = itemView.findViewById(R.id.weekTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weight, parent, false)
        return WeightViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val entry = weightEntries[position]
        holder.dateTextView.text = entry.date
        holder.weightTextView.text = entry.weight
        holder.gainTextView.text = entry.gain
        holder.weekTextView.text = entry.week
    }

    override fun getItemCount() = weightEntries.size
}

data class WeightEntry(
    val date: String,
    val weight: String,
    val gain: String,
    val week: String
)