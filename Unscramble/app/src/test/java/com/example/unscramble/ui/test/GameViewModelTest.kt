package com.example.unscramble.ui.test

import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.getUnscrambledWord
import com.example.unscramble.ui.GameViewModel
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.assertNotEquals

class GameViewModelTest {
    private val viewModel = GameViewModel()

    // Ruta de exito
    // Prueba para verificar que la puntuación se actualice correctamente después de una respuesta
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset()  {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        // Asegúrese de que el método checkUserGuess() actualiza isGuessedWordWrong correctamente.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Verificar que la puntuación se actualice correctamente.
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    //Ruta de Error
    // Prueba para verificar que el método checkUserGuess() actualiza correctamente isGuessedWordWrong
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Dar un valor incorrecto como entrada
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value
        // Verificar que la puntuación no ha cambiado
        assertEquals(0, currentGameUiState.score)
        // Verificar que el método checkUserGuess() actualiza correctamente isGuessedWordWrong
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    //Caso Límite:
    //Prueba de inicializacion de ViewModel:
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Afirma que la palabra actual está desordenada.
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Asegurar que el contador de palabras actual esté establecido en 1.
        assertTrue(gameUiState.currentWordCount == 1)
        // Afirmar que inicialmente la puntuación es 0.
        assertTrue(gameUiState.score == 0)
        // Afirmar que la palabra adivinada incorrectamente es falsa.
        assertFalse(gameUiState.isGuessedWordWrong)
        // Afirma que el juego no ha terminado.
        assertFalse(gameUiState.isGameOver)
    }

    // 2 Caso limite:
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()

            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Asegurar que después de cada respuesta correcta, la puntuación se actualiza correctamente.
            assertEquals(expectedScore, currentGameUiState.score)
        }

        // Asegurar que, una vez respondidas todas las preguntas, el recuento de palabras actual esté actualizado.
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // Afirma que después de que se respondan 10 preguntas, el juego habrá terminado.
        assertTrue(currentGameUiState.isGameOver)
    }
}