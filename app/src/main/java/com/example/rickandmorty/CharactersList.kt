package com.example.rickandmorty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.network.model.Character
import com.example.network.model.CharactersPage

@Composable
fun CharactersList(
    state: CharactersPage = CharactersPage(),
    onItemClick: (Character) -> Unit = {},
) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize(),
) {
    if (state.characters.isEmpty()) {
        CircularProgressIndicator(Modifier.size(100.dp))
    } else {
        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(state.characters) {
                Text(
                    it.run { "$id. $name\nStatus: $status\nSpecies: $species" },
                    Modifier.clickable(onClick = { onItemClick(it) }),
                )
            }
        }
    }
}

@Preview
@Composable
fun CharactersListLoading() {
    CharactersList()
}