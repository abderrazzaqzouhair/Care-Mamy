package com.app.caremama.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.app.caremama.MainActivity
import com.app.caremama.R
import com.app.caremama.auth.AuthActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val indicatorLayout: TabLayout = findViewById(R.id.indicatorLayout)
        val button = findViewById<TextView>(R.id.btnNext)
        val skip = findViewById<TextView>(R.id.skip)
        skip.setOnClickListener {
            Log.d("hisr", "skip")
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == adapter.itemCount - 1) {
                    button.text = getString(R.string.get_started)
                } else {
                    button.text = getString(R.string.next)
                }
            }
        })

        button.setOnClickListener {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < adapter.itemCount) {
                viewPager.currentItem = nextItem
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }


        TabLayoutMediator(indicatorLayout, viewPager) { _, _ -> }.attach()
    }
}