package com.app.caremama.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

class MoodFragment : Fragment() {

    private var selectedMood: String? = null
    private val journalEntries = mutableListOf(
        JournalEntry("01 Oct 12:42", "Boredom") // Initial example entry
    )
    private lateinit var journalAdapter: JournalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mood, container, false)

        // Setup mood options
        val moodOptions = listOf(
            "Happiness", "Joy", "Excitement",
            "Satisfaction", "Love", "Gratitude",
            "Affection", "Enthusiasm",
            "Cheerful", "Contentment",
            "Frustration", "Fear", "Surprise",
            "Sadness", "Anger", "Pain",
            "Boredom", "Indifference"
        )

        val moodRecyclerView = view.findViewById<RecyclerView>(R.id.moodOptionsRecyclerView)
        moodRecyclerView.layoutManager = GridLayoutManager(context, 3)
        moodRecyclerView.adapter = MoodAdapter(moodOptions) { mood ->
            selectedMood = mood
        }

        // Setup journal entries
        val journalRecyclerView = view.findViewById<RecyclerView>(R.id.journalRecyclerView)
        journalRecyclerView.layoutManager = LinearLayoutManager(context)
        journalAdapter = JournalAdapter(journalEntries)
        journalRecyclerView.adapter = journalAdapter

        // Save button click handler
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            selectedMood?.let { mood ->
                val currentTime = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
                    .format(Date())
                val newEntry = JournalEntry("Today ${currentTime.split(" ")[2]}", mood)

                journalEntries.add(0, newEntry) // Add to top of list
                journalAdapter.notifyItemInserted(1) // Position 1 because of header
                selectedMood = null

                // Reset selection
                (moodRecyclerView.adapter as? MoodAdapter)?.resetSelection()
            } ?: run {
                Toast.makeText(context, "Please select a mood first", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    class MoodAdapter(
        private val moods: List<String>,
        private val onMoodSelected: (String) -> Unit
    ) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

        private var selectedPosition = -1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
            val chip = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mood_option, parent, false) as Chip
            return MoodViewHolder(chip)
        }

        override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
            holder.bind(moods[position], position == selectedPosition)
        }

        override fun getItemCount() = moods.size

        fun resetSelection() {
            val previousSelected = selectedPosition
            selectedPosition = -1
            notifyItemChanged(previousSelected)
        }

        inner class MoodViewHolder(private val chip: Chip) : RecyclerView.ViewHolder(chip) {
            fun bind(mood: String, isSelected: Boolean) {
                chip.text = mood
                chip.isChecked = isSelected

                // Set initial colors
                if (isSelected) {
                    chip.setChipBackgroundColorResource(R.color.secondary)
                } else {
                    chip.setChipBackgroundColorResource(R.color.white)
                }

                chip.setOnClickListener {
                    val previousSelected = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousSelected)
                    notifyItemChanged(selectedPosition)
                    onMoodSelected(mood)
                }
            }
        }
    }

    class JournalAdapter(
        private val entries: List<JournalEntry>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            private const val TYPE_HEADER = 0
            private const val TYPE_ITEM = 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType == TYPE_HEADER) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_journal_header, parent, false)
                HeaderViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_journal_entry, parent, false)
                JournalViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is JournalViewHolder) {
                holder.bind(entries[position - 1])
            }
        }

        override fun getItemCount() = entries.size + 1 // +1 for header

        override fun getItemViewType(position: Int): Int {
            return if (position == 0) TYPE_HEADER else TYPE_ITEM
        }

        inner class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(entry: JournalEntry) {
                itemView.findViewById<TextView>(R.id.dateText).text = entry.date
                itemView.findViewById<TextView>(R.id.moodText).text = entry.mood
            }
        }

        inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

    data class JournalEntry(val date: String, val mood: String)
}