package com.app.caremama.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import com.app.caremama.R
import com.app.caremama.profile.ProfileFragment


class HomeFragment : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val myDoctors = view.findViewById<CardView>(R.id.doctor)
        val trackingTools = view.findViewById<CardView>(R.id.trakingtools)
        val todolist = view.findViewById<CardView>(R.id.todolist)
        val more = view.findViewById<Button>(R.id.more)
        more.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, PregnancyTrackerFragment())
                ?.commit()
        }
        todolist.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, TodoListFragment())
                ?.commit()

        }
        trackingTools.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, TrackingToolsFragment())
                ?.commit()
        }
        myDoctors.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, MyDoctorsFragment())
                ?.commit()

        }
        return view
    }

}