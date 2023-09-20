package com.example.rickandmorty

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.CharactersPage
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state: MutableState<CharactersPage> = mutableStateOf(CharactersPage())
    val state: State<CharactersPage> = _state

    fun getCharactersPage(page: Int = 1) {
        viewModelScope.launch {
            Repo.getCharactersPage(page).run {
                if (isSuccessful) {
                    val body = body()
                    Log.d("MainViewModel", "$body")
                    body?.let {
                        _state.value = it
                    }
                } else {
                    Log.e("MainViewModel", errorBody().toString())
                }
            }

        }

    }

}