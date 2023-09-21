package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.network.model.CharactersPage
import com.example.rickandmorty.ui.Destinations.CHARACTERS_LIST
import com.example.rickandmorty.ui.Destinations.CHARACTER_DETAILS
import com.example.rickandmorty.ui.characterdetails.CharacterDetails
import com.example.rickandmorty.ui.characterdetails.CharacterDetailsState
import com.example.rickandmorty.ui.characterdetails.CharacterDetailsViewModel
import com.example.rickandmorty.ui.characterslist.CharactersList
import com.example.rickandmorty.ui.characterslist.CharactersListViewModel
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    RMNavHost()
                }
            }
        }
    }

    @Composable
    private fun RMNavHost() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = CHARACTERS_LIST) {
            composable(CHARACTERS_LIST) {
                val vm: CharactersListViewModel by viewModels()
                val state: CharactersPage by vm.state

                CharactersList(state) {
                    navController.navigate("$CHARACTER_DETAILS/${it.id}")
                }
                LaunchedEffect(Unit) {
                    vm.getCharactersPage()
                }
            }

            composable(
                "$CHARACTER_DETAILS/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id")
                if (id == null || id < 1) {
                    Text("Invalid Id")
                } else {
                    val vm: CharacterDetailsViewModel by viewModels()
                    val state: CharacterDetailsState by vm.state
                    CharacterDetails(state)
                    LaunchedEffect(Unit) {
                        vm.getCharacterDetails(id)
                    }
                }
            }
        }
    }
}

object Destinations {
    const val CHARACTERS_LIST = "characters_list"
    const val CHARACTER_DETAILS = "character_details"
}

