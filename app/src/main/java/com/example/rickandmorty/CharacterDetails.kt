package com.example.rickandmorty

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.network.model.Character

@Composable
fun CharacterDetails(character: Character) {
    Text(text = character.toString())
}