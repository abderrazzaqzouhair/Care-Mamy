package com.app.caremama.advice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.annotation.SuppressLint
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class  AdviceFragment : Fragment() {

        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: TipAdapter

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_places, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            recyclerView = view.findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val tips = RetrofitClient.api.getTips()
                    adapter = TipAdapter(tips)
                    view.findViewById<ProgressBar>(R.id.savedProgressBar).visibility = View.GONE
                    recyclerView.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




interface TipsApiService {
    @GET("dev/pregnancy.php")
    suspend fun getTips(): List<Tip>
}



object RetrofitClient {
    private const val BASE_URL = "https://myonlinecertificate.com/"

    val api: TipsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TipsApiService::class.java)
    }
}

