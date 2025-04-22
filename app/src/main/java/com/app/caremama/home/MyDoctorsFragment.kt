package com.app.caremama.home

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R

class MyDoctorsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var fabAddDoctor: ImageButton
    private val doctorsList = mutableListOf<Doctor>()
    private lateinit var adapter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_doctors, container, false)

        recyclerView = view.findViewById(R.id.rvDoctors)
        emptyStateText = view.findViewById(R.id.tvEmptyState)
        fabAddDoctor = view.findViewById(R.id.fabAddDoctor)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadDoctors()

        fabAddDoctor.setOnClickListener {
            showAddDoctorDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = DoctorAdapter(doctorsList) { doctor, anchorView ->
            showDoctorOptionsMenu(doctor, anchorView)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun loadDoctors() {
        // Replace this with your actual data loading logic
        val sampleDoctors = listOf(
            Doctor("Dr. Sarah Johnson", "Cardiologist", "1234567890"),
            Doctor("Dr. Michael Smith", "Neurologist", "0987654321")
        )

        doctorsList.clear()
        doctorsList.addAll(sampleDoctors)
        adapter.notifyDataSetChanged()

        if (doctorsList.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showDoctorOptionsMenu(doctor: Doctor, anchorView: View) {
        val popup = PopupMenu(requireContext(), anchorView)
        popup.menuInflater.inflate(R.menu.menu_doctor_options, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_call -> {
                    callDoctor(doctor.phone)
                    true
                }
                R.id.menu_message -> {
                    messageDoctor(doctor.phone)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun callDoctor(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun messageDoctor(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("sms:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun showAddDoctorDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_doctor, null)
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Add New Doctor")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val name = dialogView.findViewById<EditText>(R.id.etDoctorName).text.toString()
                val specialty = dialogView.findViewById<EditText>(R.id.etSpecialty).text.toString()
                val phone = dialogView.findViewById<EditText>(R.id.etPhone).text.toString()

                if (name.isNotEmpty() && specialty.isNotEmpty() && phone.isNotEmpty()) {
                    val newDoctor = Doctor(name, specialty, phone)
                    doctorsList.add(newDoctor)
                    adapter.notifyDataSetChanged()
                    updateEmptyState()
                    Toast.makeText(requireContext(), "Doctor added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun updateEmptyState() {
        if (doctorsList.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    data class Doctor(
        val name: String,
        val specialty: String,
        val phone: String
    )

    class DoctorAdapter(
        private val doctors: List<Doctor>,
        private val onOptionClick: (Doctor, View) -> Unit
    ) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

        inner class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.tvDoctorName)
            val specialty: TextView = itemView.findViewById(R.id.tvSpecialty)
            val btnOptions: ImageButton = itemView.findViewById(R.id.btnOptions)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_doctor, parent, false)
            return DoctorViewHolder(view)
        }

        override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
            val doctor = doctors[position]
            holder.name.text = doctor.name
            holder.specialty.text = doctor.specialty

            holder.btnOptions.setOnClickListener {
                onOptionClick(doctor, it)
            }
        }

        override fun getItemCount() = doctors.size
    }
}