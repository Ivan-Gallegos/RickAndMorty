package com.example.rickandmorty.ui.characterdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.ui.theme.Typography

@Composable
fun CharacterDetails(state: CharacterDetailsState) {
    Card(
        Modifier.padding(PaddingValues(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            state.run {
                character.run {
                    Text(
                        text = name,
                        style = Typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    AsyncImage(
                        model = image,
                        contentDescription = name,
                        modifier = Modifier.size(200.dp),
                    )
                }

                locationDetails.run {
                    Text(
                        text = "Location: $name",
                        style = Typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "Type: $type\nDimension: $dimension\nNo. of residents: ${residents.size}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
