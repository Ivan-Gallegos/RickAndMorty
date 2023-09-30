package com.example.rickandmorty.ui.characterdetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.network.model.Character
import com.example.network.model.LocationDetails
import com.example.rickandmorty.ui.theme.Dimens.space
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.ui.theme.Typography

@Composable
fun CharacterDetails(state: CharacterDetailsState) = when (LocalConfiguration.current.orientation) {
    Configuration.ORIENTATION_LANDSCAPE -> CharacterDetailsRow(state)

    else -> CharacterDetailsColumn(state)
}

@Composable
fun CharacterDetailsColumn(state: CharacterDetailsState) = CharacterDetailsVerticalCard(
    state, Modifier.padding(PaddingValues(space))
) {
    AsyncImage(
        model = image,
        contentDescription = name,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun CharacterDetailsRow(state: CharacterDetailsState) = CharacterDetailsHorizontalCard(
    state, Modifier.padding(PaddingValues(space))
) {
    AsyncImage(
        model = image,
        contentDescription = name,
        modifier = Modifier.fillMaxHeight(),
        contentScale = ContentScale.FillHeight,
    )
}

@Composable
fun CharacterDetailsVerticalCard(
    state: CharacterDetailsState,
    modifier: Modifier = Modifier,
    image: @Composable Character.() -> Unit = {}
) = Card(
    modifier, colors = CardDefaults.cardColors(containerColor = colorScheme.primary),
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(space),
        verticalArrangement = Arrangement.spacedBy(space),
    ) {
        state.run {
            character.run {
                Title(Modifier.fillMaxWidth())
                image()
                CharacterDetails()
            }
            locationDetails.run {
                Title(Modifier.fillMaxWidth())
                LocationDetails()
            }
        }
    }
}

@Composable
fun CharacterDetailsHorizontalCard(
    state: CharacterDetailsState,
    modifier: Modifier = Modifier,
    image: @Composable Character.() -> Unit = {}
) = Card(
    modifier, colors = CardDefaults.cardColors(containerColor = colorScheme.primary),
) {
    Row(
        Modifier.padding(space),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space)
    ) {
        state.run {
            character.run {
                image()
                Column {
                    Title()
                    CharacterDetails()
                    locationDetails.run {
                        Title()
                        LocationDetails()
                    }
                }
            }
        }
    }
}

@Composable
private fun Character.Title(modifier: Modifier = Modifier) = Title("$id. $name", modifier)

@Composable
fun Character.CharacterDetails() = Text(
    "Species: $species\nGender: $gender\nStatus: $status\nType: $type",
    color = colorScheme.onPrimary,
    fontWeight = FontWeight.SemiBold,
)

@Composable
private fun LocationDetails.Title(modifier: Modifier = Modifier) =
    Title("Location:\n$displayId. $name", modifier)

@Composable
private fun LocationDetails.LocationDetails() = Text(
    text = "Type: $type\nDimension: $dimension\nNo. of residents: ${residents.size}",
    color = colorScheme.onPrimary,
    fontWeight = FontWeight.SemiBold
)

@Composable
private fun Title(
    title: String,
    modifier: Modifier = Modifier,
) = Text(
    title, modifier, colorScheme.onPrimary,
    style = Typography.titleLarge,
    textAlign = TextAlign.Center,
    fontWeight = FontWeight.ExtraBold,
)

//<editor-fold desc="Preview">
val previewState = CharacterDetailsState(
    Character(
        id = 1, name = "name", species = "species", image = ""
    ),
    LocationDetails(
        id = 1, name = "name", type = "type", dimension = "dimension"
    ),
)


@Preview(device = "spec:parent=pixel_5")
@Composable
fun PreviewCharacterDetailsColumn() = RickAndMortyTheme {
    Surface(modifier = Modifier.padding(space)) {
        CharacterDetailsVerticalCard(
            previewState,
            Modifier.padding(PaddingValues(space)),
        ) { Box(modifier = Modifier.size(200.dp)) }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun PreviewCharacterDetailsRow() = RickAndMortyTheme {
    Surface(modifier = Modifier.padding(space)) {
        CharacterDetailsHorizontalCard(
            previewState,
            Modifier.padding(PaddingValues(space)),
        ) { Box(modifier = Modifier.size(200.dp)) }
    }
}
//</editor-fold>
