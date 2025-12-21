package com.udhay.explore_expressive_ui.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.udhay.explore_expressive_ui.R
import com.udhay.explore_expressive_ui.model.User
import com.udhay.explore_expressive_ui.ui.viewmodel.UsersUiState
import com.udhay.explore_expressive_ui.ui.viewmodel.UsersViewModel
import com.udhay.explore_expressive_ui.ui.widgets.ExpandedFullScreenSearchBar
import com.udhay.explore_expressive_ui.ui.widgets.SampleSearchResults
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UsersListScreen(
    onUserClick: (String) -> Unit,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val usersState by viewModel.usersState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllUsers()
    }


    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
    val appBarWithSearchColors = SearchBarDefaults.appBarWithSearchColors()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            modifier = Modifier,
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            placeholder = {
                if (searchBarState.currentValue == SearchBarValue.Collapsed) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clearAndSetSemantics {},
                        text = "Search",
                        textAlign = TextAlign.Center,
                    )
                }
            },
            leadingIcon = {
                if (searchBarState.currentValue == SearchBarValue.Expanded) {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                            TooltipAnchorPosition.Above
                        ),
                        tooltip = { PlainTooltip { Text("Back") } },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(
                            onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                } else {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            },
            trailingIcon = null,
        )
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppBarWithSearch(
                scrollBehavior = scrollBehavior,
                state = searchBarState,
                inputField = inputField,
                navigationIcon = null,
                actions = {
                    IconButton(onClick = { viewModel.fetchAllUsers() }) {
                        Icon(Icons.Filled.Refresh, "Trigger Refresh")
                    }
                },
                colors = appBarWithSearchColors,
            )
            ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
                SampleSearchResults(
                    onResultClick = { result ->
                        textFieldState.setTextAndPlaceCursorAtEnd(result)
                        scope.launch { searchBarState.animateToCollapsed() }
                    }
                )
            }
        }
    ) { paddingValues ->
        var isRefreshing by remember { mutableStateOf(false) }

        LaunchedEffect(usersState) {
            if (usersState !is UsersUiState.Loading || !isRefreshing) {
                isRefreshing = false
            }
        }

        PullToRefreshBox(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.fetchAllUsers()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            indicator = {
                PullToRefreshDefaults.LoadingIndicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            },
        ) {
            when (val state = usersState) {
                is UsersUiState.Loading -> {
                    if (!isRefreshing) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingIndicator()
                        }
                    }
                }

                is UsersUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.users.users) { user ->
                            println(user.image)
                            UserTile(
                                user = user,
                                onClick = { onUserClick(user.id.toString()) }
                            )
                        }
                    }
                }

                is UsersUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
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
}

@Composable
fun UserTile(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.image)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.avatar_placeholder),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape),
                )
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }


    }
}