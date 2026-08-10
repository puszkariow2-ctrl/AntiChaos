package com.antichaos.app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.inject
import kotlinx.coroutines.flow.collectAsStateWithLifecycle
import com.antichaos.app.di.AppModule // Для DI
import com.antichaos.app.database.EnergyLevel
import com.antichaos.app.database.Task
import com.antichaos.app.repository.TaskRepository

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var addText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("AntiChaos") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати задачу")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Спісок задач
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(tasks, key = it.id) { task ->
                    TaskRow(task = task, onClick = { viewModel.toggleTaskStatus(task.id) })
                }
            }

            // Quick Capture / Inbox
            if (viewModel.pendingCaptures.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.pendingCaptures) { capture ->
                        QuickCaptureItem(capture = capture, onClick = { 
                            viewModel.addTask(addText, null) 
                            addText = "" 
                            showAddDialog = false 
                        })
                    }
                }
            }

            // Пустий экран
            if (tasks.isEmpty() && viewModel.pendingCaptures.isEmpty()) {
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
    }

    // Dialog для додавання задачі
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            content = {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(value = addText, onValueChange = { addText = it }, label = { Text("Назва задачі") })
                }
            }
        )
    }
}

@Composable
fun TaskRow(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), shape = CircleShape),
                contentAlignment = ContentAlignment.Center
            ) {
                Icon(Icons.Default.AddCircle, tint = MaterialTheme.colorScheme.onPrimary)
            }

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
            Chip(label = { Text(task.energyLevel.name) })
        }
    }
}

@Composable
fun QuickCaptureItem(capture: MainViewModel.PendingCapture, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.CenterHorizontally) {
            Text(capture.text, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.Delete, contentDescription = "Удалити")
        }
    }
}

@Composable
fun AddTaskDialog(viewModel: MainViewModel, onDismiss: () -> Unit) {
    var title by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Назва задачі") })
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = { viewModel.addTask(title.trim()) }) {
                        Text("Додати")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(onClick = onDismiss) { Text("Отметити")}
                }
            }
        }
    )
}

@Composable
fun MainNavigation(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    
    NavHost(
        startDestination = "home",
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        enabled = true
    ) {
        composable("home") { backStackEntry ->
            HomeScreen(viewModel)
                .onBackButton { navController.popBackStack() }
        }
        
        composable("journal") { backStackEntry ->
            JournalScreen(viewModel)
                .onBackButton { navController.popBackStack() }
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
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

            if (viewModel.pendingCaptures.isNotEmpty()) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.pendingCaptures) { capture ->
                        QuickCaptureItem(capture = capture, onClick = { 
                            viewModel.addTask(addText, null) 
                            addText = "" 
                            showAddDialog = false 
                        })
                    }
                }
            }

            if (viewModel.tasks.isEmpty() && viewModel.pendingCaptures.isEmpty()) {
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
    }

    var showAddDialog by remember { mutableStateOf(false) }
    
    if (showAddDialog) {
        AddTaskDialog(viewModel, onDismiss)
    }
}

@Composable
fun JournalScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Жарналь") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Нова записи")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // JournalScreen content
        }
    }
}

@Composable
fun InsightsScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Статистика") }) },
        floatingActionButton = {}
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Insights content (charts, stats)
        }
    }
}

object NavType {
    const val Home: NavType = MainRoute.Home
    const val Journal: NavType = MainRoute.Journal
    const val Insights: NavType = MainRoute.Insights
}