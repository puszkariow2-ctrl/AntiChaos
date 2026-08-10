package com.antichaos.app

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.inject
import hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            AntiChaosApp()
        }
    }
}

@Composable
fun AntiChaosApp() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("AntiChaos") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(viewModel.tasks) { task ->
                    TaskRow(task = task, onClick = viewModel::toggleTaskStatus)
                }
            }

            if (viewModel.tasks.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Доброї дні! Задачі ще не створені", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = {}) {
                        Text("Додати першу задачу")
                    }
                }
            }
        }
    }

    @Composable
    fun ShowAddDialog(viewModel: MainViewModel, onDismiss: () -> Unit) {
        var title by remember { mutableStateOf("") }
        
        AlertDialog(
            onDismissRequest = onDismiss,
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Назва задачі") })
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { 
                            viewModel.addTask(title.trim()) 
                            title = "" 
                        }) { Text("Додати")}
                        Spacer(Modifier.width(4.dp))
                        Button(onDismiss) { Text("Отметити")}
                    }
                }
            }
        )
    }

    @Composable
    fun MainViewModel(viewModel: MainViewModel = viewModel()) {} // Placeholder to inject ViewModel in Compose scope if needed, but we use viewModel() below.
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("AntiChaos") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks, key = it.id) { task ->
                    TaskRow(task = task, onClick = viewModel::toggleTaskStatus)
                }
            }

            if (tasks.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Доброї дні! Задачі ще не створені", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { showAddDialog = true }) {
                        Text("Додати першу задачу")
                    }
                }
            }
        }

        if (showAddDialog) {
            ShowAddDialog(viewModel, onDismiss = { showAddDialog = false })
        }
    }
}

@Composable
fun TaskRow(task: com.antichaos.app.core.domain.model.Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = CircleShape),
                contentAlignment = ContentAlignment.Center
            ) {}

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.CenterVertically
            ) {
                Text(text = task.title, style = MaterialTheme.typography.headlineSmall.copy(weight = FontWeight.Bold))
                if (task.description != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = task.description!!, style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Chip(label = { Text(task.isCompleted ? "✅" else "⏳") })
        }
    }
}

object NavType {
    const val Home: NavType = MainRoute.Home
}

sealed class MainRoute {
    object Home : MainRoute()
}