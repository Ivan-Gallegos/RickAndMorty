package com.example.rickandmorty

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.model.CharactersPage
import kotlinx.coroutines.launch

class CharactersListViewModel : ViewModel() {
    private val tag = this::class.simpleName
    private val _state: MutableState<CharactersPage> = mutableStateOf(CharactersPage())
    val state: State<CharactersPage> = _state

    fun getCharactersPage(page: Int = 1) {
        viewModelScope.launch {
            Repo.getCharactersPage(page).run {
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