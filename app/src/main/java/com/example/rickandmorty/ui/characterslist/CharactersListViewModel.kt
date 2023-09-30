package com.example.rickandmorty.ui.characterslist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.logErrorResponse
import com.example.network.logSuccessResponse
import com.example.network.model.Character
import com.example.network.model.CharactersPage
import com.example.rickandmorty.Repo
import com.example.rickandmorty.launchWith
import kotlinx.coroutines.Job
import retrofit2.Response

class CharactersListViewModel : ViewModel() {
    private val tag = this::class.simpleName
    private val _state: MutableState<CharacterListState> = mutableStateOf(CharacterListState())
    val state: State<CharacterListState> = _state

    private fun getCharactersPage(
        page: Int = 1,
        name: String = "",
        onSuccess: ((body: CharactersPage?) -> Unit) = {
            it?.let {
                logSuccessResponse(tag, it)
                updateCharacters(it.characters)
            }
        },
        onError: (Response<CharactersPage>.(tag: String?) -> Unit) = { logErrorResponse(tag) },
    ): Job = viewModelScope.launchWith(state.value) {
        _state.value = it.copy(lastPage = page, searchQuery = name)
        Repo.getCharactersPage(page, name).run {
            if (isSuccessful) onSuccess(body())
            else onError(tag)
        }
    }

    fun getFirstPageIfEmpty() = viewModelScope.launchWith(state.value) {
        if (it.charactersPage.characters.isEmpty()) getCharactersPage()
    }

    fun onSearchChanged(name: String) = viewModelScope.launchWith(state.value) {
        if (name != it.searchQuery) getCharactersPage(name = name)
    }

    fun onLoadNextPage() = viewModelScope.launchWith(state.value) { state ->
        getCharactersPage(state.lastPage + 1, state.searchQuery, onSuccess = {
            it?.let {
                logSuccessResponse(tag, it)
                updateCharacters(state.charactersPage.characters.toMutableList().apply {
                    addAll(it.characters)
                })
            }
        })
    }

    private fun updateCharacters(characters: List<Character>) = state.value.run {
        _state.value = copy(
            charactersPage = charactersPage.copy(
                characters = characters
            )
        )
    }
}

data class CharacterListState(
    val charactersPage: CharactersPage = CharactersPage(),
    val lastPage: Int = 1,
    val searchQuery: String = ""
)
