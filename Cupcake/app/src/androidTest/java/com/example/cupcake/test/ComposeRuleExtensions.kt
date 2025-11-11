package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * Función de extensión para [AndroidComposeTestRule] que permite encontrar un nodo (Composable)
 * en el árbol de la interfaz de usuario utilizando su ID de recurso de cadena (`StringRes`).
 *
 * Esta función es un atajo para evitar escribir `onNodeWithText(activity.getString(R.string.mi_texto))`,
 * simplificando el código del test y haciéndolo más legible y seguro. Al usar IDs de recursos en lugar
 * de cadenas de texto hardcodeadas, los tests no se romperán si el texto cambia o se traduce
 * a otros idiomas.
 *
 * @param A El tipo de [ComponentActivity] que se está probando.
 * @param id El ID del recurso de cadena (`R.string.*`) que se usará para buscar el nodo.
 * @return Un [SemanticsNodeInteraction] que permite realizar aserciones (assertions) o acciones
 *         (como `performClick()`) sobre el nodo encontrado.
 *
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))
