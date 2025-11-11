package com.example.cupcake.test

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

/**
 * Verifica que el valor actual de la pila de navegación del NavController sea [expectedRouteName]
 */
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}
