package com.fit2081.kenzierivanwiguna_34726896_a1.data.GenAI

sealed interface UiState {
    object Initial: UiState
    object Loading: UiState
    data class Success(val outputText: String) : UiState
    data class Error(val errorMessage: String) : UiState
}