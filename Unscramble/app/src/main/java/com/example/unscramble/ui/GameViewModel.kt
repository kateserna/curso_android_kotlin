package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.update


class GameViewModel: ViewModel() {

    // Game UI estado
    private val _uiState = MutableStateFlow(GameUiState())

    // Propiedad copia de seguridad de solo lectura, para evitar actualizaciones de estado de otras clases
    // asStateFlow() hace que este flujo de estado mutable sea de solo lectura.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Palabra ingresada por el usuario
    var userGuess by mutableStateOf("")
        private set

    // Conjunto de palabras utilizadas en el juego
    private var usedWords: MutableSet<String> = mutableSetOf()

    // lateinit: se usa cuando una propiedad no puede ser inicializada en el constructor, pero su valor será asignado más tarde.
    private lateinit var currentWord: String

    // Inicializa el juego
    init {
        resetGame()
    }

    // Reinicia el juego, borra las palabras utilizadas en el conjunto useWords y actualiza el estado de la UI _uiState.
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    // Actualiza el estado de la UI con la palabra ingresada por el usuario.
    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    // Verifica si la palabra ingresada por el usuario es correcta.
    fun checkUserGuess() {

        if (userGuess.equals(currentWord, ignoreCase = true)) {
        } else {
            // La respuesta del usuario es incorrecta; mostrar un error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Restablecer la suposición del usuario
        updateUserGuess("")
    }


    // Metodo auxiliar para desordenar la palabra actual
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Desordena la palabra
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        //Sigue eligiendo una palabra al azar hasta que encuentres una que no se haya usado antes.
        currentWord = allWords.random()

        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

}