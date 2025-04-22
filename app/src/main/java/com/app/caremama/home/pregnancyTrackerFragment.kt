package com.app.caremama.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.app.caremama.R
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PregnancyTrackerFragment : Fragment() {

    private lateinit var tvWeight: TextView
    private lateinit var tvLength: TextView
    private lateinit var tvAdvice: TextView
    private lateinit var tvFollowUp: TextView
    private lateinit var ivBabyImage: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pregnancy_tracker, container, false)

        tvWeight = view.findViewById(R.id.tvWeight)
        tvLength = view.findViewById(R.id.tvLength)
        tvAdvice = view.findViewById(R.id.tvAdvice)
        tvFollowUp = view.findViewById(R.id.tvFollowUp)
        ivBabyImage = view.findViewById(R.id.fetusImage)

        fetchPregnancyData()

        return view
    }

    private fun fetchPregnancyData() {
        RetrofitInstance.api.getPregnancyWeeks().enqueue(object : Callback<List<PregnancyInfo>> {
            override fun onResponse(call: Call<List<PregnancyInfo>>, response: Response<List<PregnancyInfo>>) {
                if (response.isSuccessful) {
                    val weeksList = response.body()

                    // Example: show week 8 info
                    val week8 = weeksList?.find { it.week == 8 }
                    week8?.let { displayWeekInfo(it) }
                } else {
                    Log.e("API_RESULT", "Failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<PregnancyInfo>>, t: Throwable) {
                Log.e("API_RESULT", "Error: ${t.message}")
            }
        })
    }

    private fun displayWeekInfo(info: PregnancyInfo) {
        tvWeight.text = "Weight: ${info.weight}g"
        tvLength.text = "Length: ${info.length}cm"
        tvAdvice.text = info.advice
        tvFollowUp.text = info.follow_up

        Glide.with(this)
            .load(info.image_url)
            .into(ivBabyImage)
    }
}
