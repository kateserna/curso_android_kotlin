package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.reply.ui.ReplyApp
import com.example.reply.R
import org.junit.Rule
import org.junit.Test

class ReplyAppTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    // Test para ventana compacta
    @Test
    @TestCompactWidth
    fun compactDevice_verifyUsingBottomNavigation() {
        // Configurar ventana compacta
        composeTestRule.setContent {
            ReplyApp(
                windowSize = WindowWidthSizeClass.Compact
            )
        }
        // Verificar que se usa BottomNavigation
        composeTestRule.onNodeWithTagForStringId(
            R.string.navigation_bottom
        ).assertExists()
    }

    // Test para pantallas medianas, verifica que existe el riel de navegacion
    @Test
    @TestMediumWidth
    fun mediumDevice_verifyUsingNavigationRail() {
        // Configurar ventana mediana
        composeTestRule.setContent {
            ReplyApp(
                windowSize = WindowWidthSizeClass.Medium
            )
        }
        // Verificar que se usa NavigationRail
        composeTestRule.onNodeWithTagForStringId(
            R.string.navigation_rail
        ).assertExists()
    }

    // Test para pantallas expandidas, existencia de panel lateral de navegacion
    @Test
    @TestExpandedWidth
    fun expandedDevice_verifyUsingNavigationDrawer() {
        // Configurar ventana expandida
        composeTestRule.setContent {
            ReplyApp(
                windowSize = WindowWidthSizeClass.Expanded
            )
        }
        // Verificar que se usa NavigationDrawer
        composeTestRule.onNodeWithTagForStringId(
            R.string.navigation_drawer
        ).assertExists()
    }

}