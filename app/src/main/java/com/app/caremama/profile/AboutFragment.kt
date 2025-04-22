package com.app.caremama.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.caremama.R
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AboutFragment : Fragment() {

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val feedbackButton = view.findViewById<Button>(R.id.btnFeedback)
        val back: ImageView = view.findViewById(R.id.sign_back)
        back.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, ProfileFragment())
                ?.commit()
        }
        feedbackButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:hydratracker.support@hydratracker.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "HydraTracker Feedback")
            startActivity(intent)
        }

        return view
    }
}
