package com.example.rickandmorty.ui.characterdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.ui.theme.Typography

@Composable
fun CharacterDetails(state: CharacterDetailsState) = Card(
    Modifier.padding(PaddingValues(16.dp)),
    colors = CardDefaults.cardColors(containerColor = colorScheme.primary)
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        state.run {
            character.run {
                Title(name)
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            locationDetails.run {
                Title("Location: $name")
                Text(
                    text = "Type: $type\nDimension: $dimension\nNo. of residents: ${residents.size}",
                    color = colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun Title(title: String) = Text(
    text = title,
    style = Typography.titleLarge,
    color = colorScheme.onPrimary,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.ExtraBold
)
