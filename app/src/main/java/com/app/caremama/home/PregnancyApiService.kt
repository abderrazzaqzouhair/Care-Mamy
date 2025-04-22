package com.app.caremama.home

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

object RetrofitInstance {
    private const val BASE_URL = "https://myonlinecertificate.com/"

    val api: PregnancyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PregnancyApiService::class.java)
    }
}



interface PregnancyApiService {
    @GET("dev/grossess.php")
    fun getPregnancyWeeks(): retrofit2.Call<List<PregnancyInfo>>
}
data class PregnancyInfo(
    val week: Int,
    val days: Int,
    val weight: Float,
    val length: Float,
    val advice: String,
    val follow_up: String,
    val image_url: String
)
