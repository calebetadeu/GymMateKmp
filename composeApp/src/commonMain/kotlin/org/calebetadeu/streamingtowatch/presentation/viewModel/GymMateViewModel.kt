package org.calebetadeu.streamingtowatch.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.calebetadeu.streamingtowatch.data.ExerciseDao
import org.calebetadeu.streamingtowatch.data.ExerciseEntity

class GymMateViewModel(private val exerciseDao: ExerciseDao): ViewModel() {
    // private val _exercises -> o prefixo _ é uma convenção para indicar que a propriedade é privada e não deve ser acessada diretamente pela UI
    // MutableStateFlow<List<Exercise>>(emptyList()) -> inicializa o fluxo com uma lista vazia de exercícios

    private val _exercises = MutableStateFlow<List<ExerciseEntity>>(emptyList())

    /*val exercises: Propriedade pública que será acessada pela UI para observar as mudanças nos exercícios.
    StateFlow<List<Exercise>>: Define que essa propriedade é um fluxo de estado imutável de uma lista de exercícios.
    get() = _exercises: Retorna o fluxo privado _exercises, mas como um StateFlow imutável.*/

    val exercises: StateFlow<List<ExerciseEntity>> get() = _exercises

    init {
        viewModelScope.launch {
            exerciseDao.getAllExercises().collect { exerciseList ->
                _exercises.value = exerciseList}
        }
    }

    fun addExercise(exercise: ExerciseEntity) { //função para adicionar um exercício
        viewModelScope.launch { //coroutine para que, quando ViewModel seja destruído, a corrotina seja automaticamente cancelada
            exerciseDao.insertExercise(exercise) //chamada do DAO para inserir o exercício no banco de dados
        }
    }

    fun updateExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            exerciseDao.updateExercise(exercise)
        }
    }

    fun deleteExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            exerciseDao.deleteExercise(exercise)
        }
    }

}