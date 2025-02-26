package com.example.myapplication.tasklist

import com.example.myapplication.shared.tasklist.TaskListComponent
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.myapplication.shared.tasklist.PreviewTaskListComponent
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskDomain
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskCategoryType
import dev.pango.ohmylife.features.sharedkernel.domain.entity.TaskPriority
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskListContent(
    component: TaskListComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.subscribeAsState()
    var showBottomSheet by remember { mutableStateOf(false) }


    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "TaskList Screen") },
//                navigationIcon = {
//                    IconButton(onClick = component::onBackClicked) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
//                            contentDescription = "Back button",
//                        )
//                    }
//                },
            )
        },
    ) {

            paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            if (showBottomSheet) {
                CreateTaskBottomSheet(
                    onDismiss = { showBottomSheet = false },
                    onCreateTask = { title, description, priority ->
                        println("Tarea creada: $title, $description, Prioridad: $priority")
                        component.onCreateTaskButtonClicked(taskTitle = title, taskDescription = description, taskPriority  =priority)
                        // Aquí puedes hacer la llamada al backend para crear la tarea
                    }
                )
            }

            Button(onClick = { showBottomSheet = true }) {
                Text("Nueva Tarea")
            }
            IconButton(onClick = {component.onRefreshButtonClicked()}) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(model.taskList) { task ->
                    TaskItem(task, onPlay = { component.onPlayTaskButtonClicked(it)}, onPause = {
                        component.onPauseTaskButtonClicked(it)
                    }, onStop = {

                        component.onStopTaskButtonClicked(it)
                    })
                }
            }
        }
    }
}




@Composable
fun TaskItem(task: TaskDomain, onPlay: (taskId: String) -> Unit, onPause: (taskId: String) -> Unit, onStop: (taskId:String) -> Unit) {
    val elapsedTime = remember { mutableStateOf(task.elapsedTimeInMillis) }
    val startedAt = task.startedAt?.toEpochMilliseconds()
    val isRunning = startedAt != null && task.finishedAt == null

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                val now = Clock.System.now().toEpochMilliseconds()
                elapsedTime.value = task.elapsedTimeInMillis + (now - startedAt!!)
                delay(1000L)
            }
        }
    }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Título y descripción
            Text(text = task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            task.description?.let {
                Text(text = it, fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))
            // Tiempo transcurrido
            Text(
                text = "Tiempo: ${formatElapsedTime(elapsedTime.value)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Prioridad y tiempo transcurrido
            Row(verticalAlignment = Alignment.CenterVertically) {
                PriorityChip(priority = task.priority)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = formatElapsedTime(task.elapsedTimeInMillis), fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Categoría con icono
            task.categoryType?.let { category ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(
                        onClick = { },
                        label = { Text(getCategoryName(category)) },
                        colors = AssistChipDefaults.assistChipColors(containerColor = Color.Gray)
                    )
//                    Icon(
//                        painter = painterResource(id = getCategoryIcon(category)),
//                        contentDescription = category.name,
//                        tint = Color.Black
//                    )
//                navigationIcon = {
//                    IconButton(onClick = component::onBackClicked) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
//                            contentDescription = "Back button",
//                        )
//                    }
//                },
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = category.name, fontSize = 14.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Recompensas (dinero, XP, dificultad)
            Row {
                task.rewardMoney?.let {
                    RewardChip(icon = Icons.Default.ThumbUp, text = "$it")
                }
                task.experiencePoints?.let {
                    RewardChip(icon = Icons.Default.Star, text = "$it XP")
                }
                task.difficultyPoints?.let {
                    RewardChip(icon = Icons.Default.Warning, text = "$it")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {onPlay(task.id)}) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
                }
                IconButton(onClick = {onPause(task.id)}) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Pause")
                }
                IconButton(onClick = {onStop(task.id)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Stop")
                }
            }
        }
    }
}

// ---------------------------- COMPONENTES AUXILIARES ----------------------------

@Composable
fun PriorityChip(priority: TaskPriority) {
    val color = when (priority) {
        TaskPriority.CRITICAL -> Color.Red
        TaskPriority.HIGH -> Color(0xFFFFA500) // Naranja
        TaskPriority.MEDIUM -> Color.Yellow
        TaskPriority.LOW -> Color.Green
        TaskPriority.OPTIONAL -> Color.Gray
    }

    AssistChip(
        onClick = { },
        label = { Text(priority.name) },
        colors = AssistChipDefaults.assistChipColors(containerColor = color)
    )
}

@Composable
fun RewardChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    AssistChip(
        onClick = { },
        label = { Text(text) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        colors = AssistChipDefaults.assistChipColors(containerColor = Color.LightGray)
    )
}


fun formatElapsedTime(elapsedTimeMillis: Long): String {
    val duration = elapsedTimeMillis.toDuration(DurationUnit.MILLISECONDS)
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60
    return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"


}

//fun getCategoryIcon(category: TaskCategoryType): Int {
//    return when (category) {
//        TaskCategoryType.KNOWLEDGE -> R.drawable.ic_knowledge
//        TaskCategoryType.WORK -> R.drawable.ic_work
//        TaskCategoryType.CREATIVITY -> R.drawable.ic_creativity
//        TaskCategoryType.PHYSICAL -> R.drawable.ic_physical
//        TaskCategoryType.SOCIAL -> R.drawable.ic_social
//        TaskCategoryType.DISCIPLINE -> R.drawable.ic_discipline
//        TaskCategoryType.FINANCE -> R.drawable.ic_finance
//        TaskCategoryType.LEISURE -> R.drawable.ic_leisure
//    }
//}
fun getCategoryName(category: TaskCategoryType): String {
    return when (category) {
        TaskCategoryType.KNOWLEDGE -> "Conocimiento"
        TaskCategoryType.WORK -> "Trabajo"
        TaskCategoryType.CREATIVITY -> "Creatividad"
        TaskCategoryType.PHYSICAL -> "Ejercicio"
        TaskCategoryType.SOCIAL -> "Social"
        TaskCategoryType.DISCIPLINE -> "Disciplina"
        TaskCategoryType.FINANCE -> "Finanzas"
        TaskCategoryType.LEISURE -> "Ocio"
    }
}

@Preview
@Composable
fun TaskListPreview() {
    TaskListContent(PreviewTaskListComponent)
}
