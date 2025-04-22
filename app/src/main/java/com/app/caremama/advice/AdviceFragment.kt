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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.caremama.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class  AdviceFragment : Fragment() {
    private lateinit var viewModel: PlacesViewModel
    private lateinit var adapter: PlacesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var savedProgressBar : ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_places, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        savedProgressBar = view.findViewById(R.id.savedProgressBar)

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        setupObservers()
        viewModel.getPlaces()
        return view
    }

    private fun setupObservers() {
        viewModel.placesList.observe(viewLifecycleOwner) { places ->
            adapter = PlacesAdapter(places, requireContext())
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            savedProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }


}

interface ApiService {

    @GET("dev/saved_places/savedPlaces.php")
    suspend fun getPlaces(): List<SavedPlaces>
}


object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://myonlinecertificate.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
