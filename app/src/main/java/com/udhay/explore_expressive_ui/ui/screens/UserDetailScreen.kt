package com.udhay.explore_expressive_ui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.udhay.explore_expressive_ui.model.User
import com.udhay.explore_expressive_ui.ui.viewmodel.UserUiState
import com.udhay.explore_expressive_ui.ui.viewmodel.UsersViewModel
import com.udhay.explore_expressive_ui.ui.widgets.AppBarWithSearch
import com.udhay.explore_expressive_ui.ui.widgets.ExpandedFullScreenSearchBar
import com.udhay.explore_expressive_ui.ui.widgets.SampleSearchResults
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UserDetailScreen(
    userId: String,
    onBackClick: () -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUser(userId)
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("") }
            )
        }
    ) { paddingValues ->
        when (val state = userState) {
            is UserUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator()
                }
            }
            is UserUiState.Success -> {
                UserDetailContent(
                    user = state.user,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is UserUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun UserDetailContent(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Name", "${user.firstName} ${user.lastName}")
                DetailRow("Maiden Name", user.maidenName)
                DetailRow("Age", "${user.age}")
                DetailRow("Gender", user.gender)
                DetailRow("Email", user.email)
                DetailRow("Phone", user.phone)
                DetailRow("Birth Date", user.birthDate)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Physical Attributes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Height", "${user.height} cm")
                DetailRow("Weight", "${user.weight} kg")
                DetailRow("Eye Color", user.eyeColor)
                DetailRow("Hair", "${user.hair.color} (${user.hair.type})")
                DetailRow("Blood Group", user.bloodGroup)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Address",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Street", user.address.address)
                DetailRow("City", user.address.city)
                DetailRow("State", user.address.state)
                DetailRow("Postal Code", user.address.postalCode)
                DetailRow("Country", user.address.country)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Work Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Company", user.company.name)
                DetailRow("Department", user.company.department)
                DetailRow("Title", user.company.title)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}