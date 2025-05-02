package com.app.caremama.advice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
//
//class PlacesViewModel : ViewModel() {
//    private val apiService = ApiClient.apiService
//    val placesList = MutableLiveData<List<SavedPlaces>>()
//    val isLoading = MutableLiveData<Boolean>()
//    val errorMessage = MutableLiveData<String>()
//
//    fun getPlaces(city: String? = null, minRating: Int? = null) {
//        viewModelScope.launch {
//            try {
//                isLoading.value = true
//                val response = apiService.getPlaces()
//                placesList.value = response
//                isLoading.value = false
//            } catch (e: Exception) {
//                isLoading.value = false
//                errorMessage.value = e.message ?: "Unknown error occurred"
//            }
//        }
//    }
//}