package com.example.heroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.heroapp.model.HeroesRepository
import com.example.heroapp.ui.theme.HeroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HeroAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SuperHeroApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
/**
 * Composable raíz que define la estructura de la pantalla de la aplicación utilizando un Scaffold.
 * El Scaffold proporciona una estructura estándar con una barra de aplicación superior (TopAppBar).
 */
@Composable
fun SuperHeroApp(modifier: Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroTopBar()
        }
    ) { it ->
        val heroes = HeroesRepository.heroes
        HeroListItem(
            contentPadding = it,
            heroes = heroes,
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tittle_app),
                style = MaterialTheme.typography.displayLarge,
            )
        },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun HeroAppPreview() {
    HeroAppTheme {
        SuperHeroApp(modifier = Modifier)
    }
}