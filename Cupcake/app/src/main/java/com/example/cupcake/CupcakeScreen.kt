/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

// Rutas de navegación de la aplicación
enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

/**
 * Composable que muestra la barra de aplicación superior (TopAppBar) y un botón de retroceso
 * si la navegación hacia atrás es posible.
 */
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Composable principal que configura la estructura de la aplicación, incluyendo la barra superior
 * y el grafo de navegación.
 *
 * @param viewModel La instancia de [OrderViewModel] que mantiene el estado del pedido.
 * @param navController El controlador [NavHostController] que gestiona la navegación entre pantallas.
 */
@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    // Instancia de NavHostController para la navegación, es el responsable de navegar entre rutas y
    // mantener el historial de navegación.
    navController: NavHostController = rememberNavController()
) {
    // Obtiene la entrada actual de la pila de navegación para saber en qué pantalla estamos.
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Determina el enum `CupcakeScreen` actual basándose en la ruta de navegación.
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                // El botón de retroceso solo se muestra si hay una pantalla anterior en la pila.
                canNavigateBack = navController.previousBackStackEntry != null,
                // La acción de retroceso es manejada por el NavController.
                navigateUp = {navController.navigateUp()}
            )
        }
    ) { innerPadding ->
        // Recolecta el estado de la UI desde el ViewModel.
        val uiState by viewModel.uiState.collectAsState()

/*
 * NavHost es el componente central para la navegación en Compose. Actúa como un contenedor
 * que muestra el destino de navegación actual. Configura el grafo de navegación de la aplicación.
 * Define cada una de las "rutas" (pantallas) que el usuario puede visitar y asocia un Composable a
 * cada una de ellas.
 *
 * - navController: La instancia de NavHostController que gestiona la pila de navegación
 *   y las transiciones entre pantallas. Controla a qué pantalla ir, cómo volver atrás y mantiene
 *   el historial de navegación.
 * - startDestination: La ruta del destino que se muestra inicialmente al lanzar la app.
 *   En este caso, es la pantalla "Start". Especifica cuál de todas las pantallas definidas es la
 *   primera que se debe mostrar.
 * - modifier: Se aplica un padding interno (innerPadding) proporcionado por el Scaffold
 *   para asegurar que el contenido no se solape con la barra superior (TopAppBar).
 * - composable(route = ...): Cada bloque composable define una pantalla. La route es un String que
 *   actúa como su identificador único. Cuando se navega a esa ruta, el contenido dentro de este
 *   bloque se muestra en el NavHost.
 */
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            /*
             * Define la pantalla de inicio de la orden.
             * - route: El identificador único para esta pantalla, obtenido del enum CupcakeScreen.
             * - composable: El contenido de la pantalla que se va a renderizar.
             */
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    // Define la acción para el botón "Siguiente".
                    onNextButtonClicked = {
                        // Actualiza la cantidad de cupcakes en el ViewModel.
                        viewModel.setQuantity(it)
                        // Navega a la siguiente pantalla (Flavor).
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
            /*
             * Define la pantalla para seleccionar el sabor de los cupcakes.
             * Se obtienen los sabores desde DataSource y se convierten a String.
             * La selección actualiza el ViewModel.
             */
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    // Navega a la pantalla de fecha de recogida al pulsar "Siguiente"
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name)},
                    // Define la acción para el botón "Cancelar".
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            /*
             * Define la pantalla para seleccionar la fecha de recogida.
             * Las opciones de fecha se obtienen del estado de la interfaz de usuario (uiState).
             * La selección actualiza el ViewModel.
             */
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    // Navega a la pantalla de resumen al pulsar "Siguiente".
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name)},
                    // Permite cancelar el pedido desde esta pantalla.
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            /*
             * Define la pantalla de resumen del pedido.
             * Muestra toda la información del pedido contenida en uiState.
             */
            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    // Permite cancelar el pedido desde la pantalla de resumen.
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    // Define la acción para enviar el pedido a otra app.
                    onSendButtonClicked = {
                        subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }
    }
}

// Crea un Intent para compartir el resumen de la orden de cupcakes a otra app.
/**
 * Crea un Intent implícito para compartir los detalles del pedido con otra aplicación.
 *
 * Esta función construye un Intent con la acción`ACTION_SEND`, que indica que se desea enviar
 * datos. Se especifica que el tipo de datos es texto plano (`"text/plain"`). Luego, se adjuntan
 * el asunto y el cuerpo del mensaje como extras en el Intent.
 *
 * Finalmente, se invoca `Intent.createChooser`, que muestra al usuario un diálogo para que
 * elija la aplicación con la que desea compartir el contenido. Esto evita que el usuario
 * establezca una aplicación predeterminada por accidente y le da control sobre a dónde se
 * envían los datos del pedido en cada ocasión.
 *
 * @param context El contexto de la aplicación, necesario para crear y lanzar el Intent.
 * @param subject El texto que se usará como asunto del mensaje (por ejemplo, en un correo electrónico).
 * @param summary El cuerpo principal del mensaje, que contiene el resumen del pedido.
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Se crea un Intent con la acción ACTION_SEND para enviar contenido.
    val intent = Intent(Intent.ACTION_SEND).apply {
        // Se especifica que el tipo de contenido es texto plano.
        type = "text/plain"
        // Se añade el asunto del mensaje.
        putExtra(Intent.EXTRA_SUBJECT, subject)
        // Se añade el cuerpo principal del mensaje (el resumen del pedido).
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    // Se inicia una actividad utilizando un selector (chooser).
    // Esto le permite al usuario elegir a qué aplicación enviar el pedido.
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}

// Metodo para cancelar la orden y restablecer la pantalla inicial
/**
* El método popBackStack() tiene dos parámetros obligatorios.
*  - route: Es la cadena que representa la ruta del destino al que deseas volver.
*  - inclusive: Es un valor booleano que, si es verdadero, también muestra (quita) la ruta
*    especificada. Si es falso, popBackStack() quitará todos los destinos que se encuentren
*    sobre el de inicio (pero no este último), lo que hará que sea la pantalla superior visible
*    para el usuario.
 *
 * Restablece el estado del pedido en el ViewModel y navega de regreso a la pantalla de inicio.
 *
 * Esta función auxiliar encapsula la lógica para cancelar un pedido, asegurando que tanto el
 * estado de los datos como el estado de la navegación se reinicien correctamente.
 *
 * @param viewModel La instancia del [OrderViewModel] cuyo estado se restablecerá.
 * @param navController El [NavHostController] utilizado para volver a la pantalla de inicio.
*/
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    // 1. Limpia todos los datos del pedido actual en el ViewModel.
    viewModel.resetOrder()
    // 2. Navega hacia atrás en la pila hasta la pantalla "Start".
    // El parámetro `inclusive = false` asegura que la pantalla "Start" no se elimine
    // de la pila, dejándola como el destino visible actual.
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)

}
