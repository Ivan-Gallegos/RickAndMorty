package com.example.rickandmorty.ui.characterslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.model.Character
import com.example.network.model.CharactersPage
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.Typography

@Composable
fun CharactersList(
    state: CharactersPage = CharactersPage(),
    onItemClick: (Character) -> Unit = {},
) = Box(Modifier.fillMaxSize(), Alignment.Center) {
    if (state.characters.isEmpty()) {
        CircularProgressIndicator(Modifier.size(200.dp))
    } else {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(state.characters) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemClick(it) }),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        it.run { "$id. $name\nStatus: $status\nSpecies: $species" },
                        Modifier.padding(16.dp),
                        MaterialTheme.colorScheme.onPrimary,
                        style = Typography.headlineLarge
                    )
                }
            }
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
            CharactersPage(
                characters = listOf(
                    Character(id = 0, name = "name", status = "status", species = "species")
                )
            )
        )
    }
}