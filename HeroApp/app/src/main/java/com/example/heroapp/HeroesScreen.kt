package com.example.heroapp

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.heroapp.model.Hero
import com.example.heroapp.model.HeroesRepository.heroes
import com.example.heroapp.ui.theme.HeroAppTheme

/**
 * Muestra una lista de héroes en una LazyColumn.
 * Este Composable está diseñado para ser reutilizable y desacoplado de la estructura principal
 * de la pantalla (como un Scaffold).
 *
 * @param heroes La lista de objetos [Hero] para mostrar.
 * @param modifier El modificador que se aplicará a la LazyColumn.
 * @param contentPadding Padding que se aplicará al contenido de la LazyColumn. Este es el
 * mecanismo clave para que un componente padre (como un Scaffold) pueda insertar padding
 * para evitar que el contenido se solape con las barras del sistema (ej. TopAppBar).
 * Por defecto es 0.dp.
 */
@Composable
fun HeroListItem(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)

) {
    // LazyColumn es el componente eficiente para mostrar listas.
    // Pasamos el contentPadding recibido directamente a la LazyColumn para que se aplique
    // al área de contenido, desplazando los items según sea necesario.
    LazyColumn(contentPadding = contentPadding) {
        items(items = heroes) { hero ->
            HeroItem(
                hero = hero,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_small),
                    horizontal = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

/**
 * Muestra un único elemento de la lista (un Card de héroe).
 *
 * @param hero El objeto [Hero] a mostrar.
 * @param modifier El modificador que se aplicará al Card.
 *
 * Muestra la información textual de un héroe (nombre y descripción).
 * @param hero.nameRes ID del recurso de string para el nombre.
 * @param hero.descriptionRes ID del recurso de string para la descripción.
 *
 * Muestra la imagen de un héroe.
 * @param imageRes ID del recurso de imagen para la imagen.
 * @param modifier Modificador para la columna que contiene los textos.
 */
@Composable
fun HeroItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        //shape = MaterialTheme.shapes.medium, no es necesario el card por defecto aplica un shape medium del theme
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
                .sizeIn(minHeight = dimensionResource(R.dimen.height_size)),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(hero.nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(dimensionResource(R.dimen.padding_medium)))
            Box( modifier = Modifier
                .size(dimensionResource(R.dimen.height_size))
                .clip(MaterialTheme.shapes.small)
            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroPreview() {
    val hero = Hero(
        R.string.hero1,
        R.string.description1,
        R.drawable.android_superhero1
    )
    HeroAppTheme {
        HeroItem(hero = hero)
    }
}

@Preview("Heroes List")
@Composable
fun HeroesPreview() {
    HeroAppTheme(darkTheme = false) {
        Surface (
            color = MaterialTheme.colorScheme.background
        ) {
            /* Important: It is not a good practice to access data source directly from the UI.
            In later units you will learn how to use ViewModel in such scenarios that takes the
            data source as a dependency and exposes heroes.
            */
           HeroListItem(heroes)
        }
    }
}

