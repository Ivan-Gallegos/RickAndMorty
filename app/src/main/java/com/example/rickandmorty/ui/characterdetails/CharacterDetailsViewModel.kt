package com.example.rickandmorty.ui.characterdetails

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.Character
import com.example.network.model.LocationDetails
import com.example.rickandmorty.Repo
import kotlinx.coroutines.launch

class CharacterDetailsViewModel : ViewModel() {
    private val tag = this::class.simpleName
    private val _state: MutableState<CharacterDetailsState> = mutableStateOf(CharacterDetailsState())
    val state: State<CharacterDetailsState> = _state

    fun getCharacterDetails(id: Int) {
        viewModelScope.launch {
            Repo.getCharacterDetails(id).run {
                if (isSuccessful) {
                    val body = body()
                    Log.d(tag, "$body")
                    body?.let {
                        _state.value = state.value.copy(character = it)
                        getLocationDetails(it.location.url)
                    }
                } else {
                    Log.e(tag, errorBody().toString())
                }
            }

        }

    }

    private fun getLocationDetails(url: String) {
        viewModelScope.launch {
            Repo.getLocationDetails(url).run {
                if (isSuccessful) {
                    val body = body()
                    Log.d(tag, "$body")
                    body?.let {
                        _state.value = state.value.copy(locationDetails = it)
                    }
                } else {
                    Log.e(tag, errorBody().toString())
                }
            }
        }
    }

}

data class CharacterDetailsState(
    val character: Character = Character(),
    val locationDetails: LocationDetails = LocationDetails(),
)