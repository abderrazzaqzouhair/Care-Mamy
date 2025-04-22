package com.app.caremama.home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.app.caremama.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class CounterFragment : Fragment(R.layout.fragment_counter) {
    private lateinit var circleButton: FrameLayout
    private lateinit var timerArea: LinearLayout
    private lateinit var buttonStop: Button
    private lateinit var textStartTime: TextView
    private lateinit var textTimeRemaining: TextView
    private lateinit var recyclerViewJournal: RecyclerView
    private lateinit var lottie: LottieAnimationView

    private val journalSessions = mutableListOf<KickSession>()
    private lateinit var journalAdapter: JournalAdapter

    private var isCounting = false
    private var startTime: Long = 0L
    private var elapsedTime: Long = 0L
    private var timer: CountDownTimer? = null
    private var kickCount: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews(view: View) {
        circleButton = view.findViewById(R.id.circleButton)
        timerArea = view.findViewById(R.id.timerArea)
        buttonStop = view.findViewById(R.id.buttonStop)
        textStartTime = view.findViewById(R.id.textStartTime)
        textTimeRemaining = view.findViewById(R.id.textTimeRemaining)
        recyclerViewJournal = view.findViewById(R.id.recyclerViewJournal)
        lottie = view.findViewById(R.id.lottie)
    }

    private fun setupRecyclerView() {
        journalAdapter = JournalAdapter(journalSessions)
        recyclerViewJournal.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewJournal.adapter = journalAdapter
    }

    private fun setupClickListeners() {
        circleButton.setOnClickListener {
            kickCount++
            if (!isCounting) {
                startCounting()
            }
        }

        buttonStop.setOnClickListener {
            if (isCounting) {
                stopCounting()
            }
        }
    }

    private fun startCounting() {
        isCounting = true
        startTime = System.currentTimeMillis()
        buttonStop.text = getString(R.string.stop)
        lottie.playAnimation()

        // Update start time display
        val startTimeFormatted = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(startTime))
        textStartTime.text = getString(R.string.start_time, startTimeFormatted)

        // Start updating the timer display
        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
                updateTimerDisplay(elapsedTime)
            }

            override fun onFinish() {
                // Not used
            }
        }.start()
    }

    private fun stopCounting() {
        isCounting = false
        timer?.cancel()
        lottie.pauseAnimation()
        buttonStop.text = getString(R.string.start)

        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime

        // Add session to journal
        val dateFormat = SimpleDateFormat("MMM dd HH:mm", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val newSession = KickSession(
            dateTime = dateFormat.format(Date(startTime)),
            duration = formatDuration(duration),
            kicks = kickCount
        )

        journalSessions.add(0, newSession)
        journalAdapter.notifyItemInserted(0)
        recyclerViewJournal.scrollToPosition(0)

        // Reset counters
        kickCount = 0
        elapsedTime = 0L
        updateTimerDisplay(0)
    }

    private fun updateTimerDisplay(milliseconds: Long) {
        val formattedTime = formatDuration(milliseconds)
        textTimeRemaining.text = getString(R.string.time_elapsed, formattedTime)
    }

    private fun formatDuration(milliseconds: Long): String {
        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliseconds),
            TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}

data class KickSession(
    val dateTime: String,
    val duration: String,
    val kicks: Int
)

class JournalAdapter(private val sessions: List<KickSession>) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    inner class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDateTime: TextView = itemView.findViewById(R.id.textDateTime)
        val textDuration: TextView = itemView.findViewById(R.id.textDuration)
        val textKicks: TextView = itemView.findViewById(R.id.textKicks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kick_session, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val session = sessions[position]
        holder.textDateTime.text = session.dateTime
        holder.textDuration.text = session.duration
        holder.textKicks.text = session.kicks.toString()
    }

    override fun getItemCount() = sessions.size
}