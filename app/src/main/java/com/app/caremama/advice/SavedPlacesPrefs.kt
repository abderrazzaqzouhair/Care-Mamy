package com.app.caremama.advice

import android.content.Context
import android.content.SharedPreferences

class  SavedPlacesPrefs(val context: Context) {
    private val PREFS_NAME = "saved_places_prefs"
    private val KEY_SAVED_PLACES = "saved_places_indices"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun savePlace(placeId: String) {
        val savedPlaces = getSavedPlaces().toMutableSet()
        savedPlaces.add(placeId)
        getSharedPreferences(context).edit()
            .putStringSet(KEY_SAVED_PLACES, savedPlaces)
            .apply()
    }
    fun removePlace(placeId: String) {
        val savedPlaces = getSavedPlaces().toMutableSet()
        savedPlaces.remove(placeId)
        getSharedPreferences(context).edit()
            .putStringSet(KEY_SAVED_PLACES, savedPlaces)
            .apply()
    }

    fun getSavedPlaces(): Set<String> {
        return getSharedPreferences(context)
            .getStringSet(KEY_SAVED_PLACES, emptySet()) ?: emptySet()
    }

    fun isPlaceSaved(placeId: String): Boolean {
        return getSavedPlaces().contains(placeId)
    }
}