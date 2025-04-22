package com.app.caremama.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.app.caremama.R


class TrackingToolsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_tracking_tools, container, false)
        val pregnancyTracker: CardView = view.findViewById(R.id.prognancyTracker)
        val counter: CardView = view.findViewById(R.id.counter)
        val weight: CardView = view.findViewById(R.id.weight)
        val mood: CardView = view.findViewById(R.id.mood)
        pregnancyTracker.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, PregnancyTrackerFragment())
                ?.commit()

        }
        counter.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, CounterFragment())
                ?.commit()
        }
        weight.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, WeightFragment())
                ?.commit()
        }
        mood.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, MoodFragment())
                ?.commit()
        }
        return view
    }
}