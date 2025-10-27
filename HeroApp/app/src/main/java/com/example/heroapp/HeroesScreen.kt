package com.example.heroapp

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroListItem(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    // 1. CREACIÓN DEL ESTADO DE TRANSICIÓN QUE COMIENZA EN UN ESTADO NO VISIBLE
    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Iniciar la animación inmediatamente
            targetState = true
        }
    }

    // 2. ANIMACIÓN DE VISIBILIDAD PARA TODA LA LISTA
    AnimatedVisibility(
        visibleState = visibleState,
        //Define como aparece el contenido cuando es visible.
        enter = fadeIn( // fadeIn: Suave efecto de fundido
            animationSpec = spring(dampingRatio = DampingRatioLowBouncy) //Efecto de rebote suave y control de "elasticidad"
        ),
        //Define como desaparece el contenido cuando no es visible.
        exit = fadeOut(),
        modifier = modifier
    ) {
        // El contenido que se animará (la LazyColumn)
        // LazyColumn es el componente eficiente para mostrar listas.
        // Pasamos el contentPadding recibido directamente a la LazyColumn para que se aplique
        // al área de contenido, desplazando los items según sea necesario.
        LazyColumn(contentPadding = contentPadding) {
            // Se usa "itemsIndexed" para acceder al "index" de cada elemento para la animacion escalonada.
            // itemsIndexed nos proporciona dos variables en su lambda: el index y el hero (objeto de datos).
            // Necesitamos el index para poder crear el efecto escalonado.
            itemsIndexed(items = heroes) { index, hero ->
                HeroItem(
                    hero = hero,
                    // Aplica el padding primero, y LUEGO, encadena la animación al Modifier.
                    modifier = Modifier
                        .padding(
                        vertical = dimensionResource(R.dimen.padding_small),
                        horizontal = dimensionResource(R.dimen.padding_medium)
                        )
                        // Permite animar la entrada y salida de cada elemento individualmente a medida
                        // que se desplaza y se hace visible en la pantalla
                        .animateEnterExit(
                            //Se define la animación de entrada y salida.
                            // Define que cada HeroItem entrará deslizándose verticalmente.
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                //Cálculo del retraso escalonado para cada elemento.
                                // Usa el "index" proporcionado por "itemsIndexed" para la animacion.
                                /* initialOffsetY: Elparámetro de slideInVertically define la posición vertical inicial desde la cual comenzará a animarse el elemento.
                                 *{ it -> ... }: it representa la altura total del Composable que se está animando. Compose lo calcula por ti.
                                 * * (index + 1): Aquí está la clave. Multiplicamos la altura por el índice del elemento.
                                 */
                                initialOffsetY = { it * (index + 1) } // staggered entrance
                            )
                        )
                )
            }
        }
    }
}

/**
 * Muestra un único elemento de la lista (un Card de héroe), conteniendo tanto la información
 * textual como la imagen.
 *
 * @param hero El objeto [Hero] completo que contiene todos los datos a mostrar (nombre, descripción e imagen).
 * @param modifier El modificador que se aplicará al Card contenedor.
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

