package org.calebetadeu.streamingtowatch.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sunnychung.lib.multiplatform.kdatetime.KInstant.Companion.now
import com.sunnychung.lib.multiplatform.kdatetime.KZoneOffset
import org.calebetadeu.streamingtowatch.data.ExerciseDao
import org.calebetadeu.streamingtowatch.data.ExerciseEntity
import org.calebetadeu.streamingtowatch.presentation.viewModel.GymMateViewModel


@Composable
fun GymMateScreen(gymMateViewModel: GymMateViewModel) {

    val exercises by gymMateViewModel.exercises.collectAsState()
    val localZoneOffset = KZoneOffset.local()
    val localDateTime = now().atZoneOffset(localZoneOffset) // or now.atLocalZoneOffset()

    Scaffold(
        floatingActionButton = {
            GymMateFAB {
                val newExerciseId =
                    (exercises.maxByOrNull { it.id.toInt() }?.id?.toIntOrNull() ?: 0) + 1

                val newExercise = ExerciseEntity(
                    id = newExerciseId.toString(),
                    exerciseName = "New Exercise",
                    sets = 0,
                    reps = 0,
                    weight = 0f,
                    date = localDateTime.format("dd MMM yyyy h:mm:ss aa")
                )
                gymMateViewModel.addExercise(newExercise)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "gymmate",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(18.dp),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(exercises.size) { exercise ->
                        ExerciseCard(
                            exercise = exercises[exercise],
                            onUpdateExercise = { updatedExercise ->
                                gymMateViewModel.updateExercise(updatedExercise)
                            },
                            onDeleteExercise = { exerciseToDelete ->
                                gymMateViewModel.deleteExercise(exerciseToDelete)
                            })
                    }
                }
            }

        }
    )
}

@Composable
fun ExerciseCard(
    exercise: ExerciseEntity,
    onUpdateExercise: (ExerciseEntity) -> Unit,
    onDeleteExercise: (ExerciseEntity) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }


    var exerciseName by remember { mutableStateOf(exercise.exerciseName) }
    var exerciseSets by remember { mutableStateOf(exercise.sets.toString()) }
    var exerciseReps by remember { mutableStateOf(exercise.reps.toString()) }
    var exerciseWeight by remember { mutableStateOf(exercise.weight.toString()) }
    var exerciseDate by remember { mutableStateOf(exercise.date) }

    LaunchedEffect(exercise) {
        exerciseName = exercise.exerciseName
        exerciseSets = exercise.sets.toString()
        exerciseReps = exercise.reps.toString()
        exerciseWeight = exercise.weight.toString()
        exerciseDate = exercise.date
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = exercise.exerciseName,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(text = exercise.date)

                IconButton(
                    onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colors.error,
                        modifier = Modifier.size(22.dp),
                        contentDescription = "Delete Exercise"
                    )
                }

                if (showDialog) {
                    ConfirmDeleteDialog(
                        onConfirm = {
                            onDeleteExercise(exercise)
                            showDialog = false
                        },
                        onDismiss = { showDialog = false }
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Show less" else "Show more"

                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                // Campo para o número de sets
                TextField(
                    value = exerciseSets,
                    onValueChange = { exerciseSets = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Campo para o número de reps
                TextField(
                    value = exerciseReps,
                    onValueChange = { exerciseReps = it },
                    label = { Text("Reps") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Campo para o peso
                TextField(
                    value = exerciseWeight,
                    onValueChange = { exerciseWeight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Campo para a data
                TextField(
                    value = exerciseDate,
                    onValueChange = { exerciseDate = it },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Botão para salvar as alterações
                Button(
                    onClick = {
                        val updatedExercise = exercise.copy(
                            exerciseName = exerciseName,
                            sets = exerciseSets.toIntOrNull() ?: 0,
                            reps = exerciseReps.toIntOrNull() ?: 0,
                            weight = exerciseWeight.toFloatOrNull() ?: 0f,
                            date = exerciseDate
                        )

                        // Salva no banco de dados
                        onUpdateExercise(updatedExercise)
//                        scope.launch {
//                            exerciseDao.getAllExercises()
//                        }
                        isExpanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}


@Composable
fun GymMateFAB(onFabClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabClick,
        modifier = Modifier.size(56.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Adicionar Exercício"
        )
    }
}

@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Deletion") },
        text = { Text("Are you sure you want to delete this exercise?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}