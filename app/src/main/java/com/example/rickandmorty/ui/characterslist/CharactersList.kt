package com.example.rickandmorty.ui.characterslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.model.Character
import com.example.network.model.CharactersPage
import com.example.rickandmorty.ui.theme.Dimens.space
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.Typography

@Composable
fun CharactersList(
    state: CharacterListState = CharacterListState(),
    onItemClick: (Character) -> Unit = {},
    onLoadNextPage: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
) = Box(Modifier.fillMaxSize(), Alignment.Center) {
    val characters = state.charactersPage.characters
    if (characters.isEmpty()) {
        CircularProgressIndicator(Modifier.size(200.dp))
    } else {
        Column {
            SearchField(state.searchQuery, onTextChanged)
            val listState = rememberLazyListState()
            LoadNextPageEffect(listState, onLoadNextPage)
            LazyCharacterColumn(listState, characters, onItemClick)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchField(searchQuery: String, onTextChanged: (String) -> Unit) = TextField(
    searchQuery,
    onTextChanged,
    Modifier
        .padding(space)
        .fillMaxWidth(),
    placeholder = { Text(text = "Search Name") })

@Composable
private fun LoadNextPageEffect(listState: LazyListState, onLoadNextPage: () -> Unit) {
    val shouldLoadNextPage by remember {
        derivedStateOf {
            listState.layoutInfo.run {
                (visibleItemsInfo.lastOrNull()?.index ?: -9) >= (totalItemsCount - 6)
            }
        }
    } // True when last visible index is one of last 7 items
    LaunchedEffect(shouldLoadNextPage) {
        if (shouldLoadNextPage) onLoadNextPage()
    }
}

@Composable
private fun LazyCharacterColumn(
    listState: LazyListState, characters: List<Character>, onItemClick: (Character) -> Unit
) = LazyColumn(
    Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(space),
    contentPadding = PaddingValues(space),
    state = listState
) {
    items(characters) {
        Card(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(it) }),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                it.run { "$id. $name\nStatus: $status\nSpecies: $species" },
                Modifier.padding(space),
                MaterialTheme.colorScheme.onPrimary,
                style = Typography.headlineLarge
            )
        }
    }
}

@Preview
@Composable
fun CharactersListLoading() = RickAndMortyTheme {
    // A surface container using the 'background' color from the theme
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        CharactersList()
    }
}

@Preview
@Composable
fun CharactersListPreview() = RickAndMortyTheme {
    // A surface container using the 'background' color from the theme
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        CharactersList(
            CharacterListState(
                CharactersPage(
                    characters = listOf(
                        Character(id = 0, name = "name", status = "status", species = "species")
                    )
                )
            )
        )
    }
}