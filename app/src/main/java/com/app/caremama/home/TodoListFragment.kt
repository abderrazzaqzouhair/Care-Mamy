package com.app.caremama.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R

class TodoListFragment : Fragment() {

    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_list, container, false)

        val titleInput = view.findViewById<EditText>(R.id.titleInput)
        val descriptionInput = view.findViewById<EditText>(R.id.descriptionInput)
        val dateInput = view.findViewById<EditText>(R.id.dateInput)
        val addButton = view.findViewById<Button>(R.id.addButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)

        todoAdapter = TodoAdapter(todoList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = todoAdapter

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val date = dateInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                val todoItem = TodoItem(title, description, date)
                todoList.add(todoItem)
                todoAdapter.notifyItemInserted(todoList.size - 1)
                titleInput.text.clear()
                descriptionInput.text.clear()
                dateInput.text.clear()
            }
        }

        return view
    }
}
