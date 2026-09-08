package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.db.AppDatabase
import com.example.data.repository.SankalpaRepository
import com.example.ui.SankalpaViewModel
import com.example.ui.screens.SankalpaScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: SankalpaViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = SankalpaRepository(database.sankalpaDao(), applicationContext)
        SankalpaViewModel.provideFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                SankalpaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
