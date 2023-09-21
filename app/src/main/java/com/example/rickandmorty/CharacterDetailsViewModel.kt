package com.example.rickandmorty

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.Character
import kotlinx.coroutines.launch

class CharacterDetailsViewModel : ViewModel() {
    private val tag = this::class.simpleName
    private val _state: MutableState<Character> = mutableStateOf(Character())
    val state: State<Character> = _state

    fun getCharacterDetails(id: Int) {
        viewModelScope.launch {
            Repo.getCharacterDetails(id).run {
                if (isSuccessful) {
                    val body = body()
                    Log.d(tag, "$body")
                    body?.let {
                        _state.value = it
                    }
                } else {
                    Log.e(tag, errorBody().toString())
                }
            }

        }

    }

}