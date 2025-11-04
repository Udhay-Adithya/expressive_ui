package com.udhay.explore_expressive_ui.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithSearch(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    state: SearchBarState = rememberSearchBarState(),
    inputField: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    colors: AppBarWithSearchColors = SearchBarDefaults.appBarWithSearchColors(),
) {
    TopAppBar(
        title = { inputField() },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExpandedFullScreenSearchBar(
    state: SearchBarState,
    inputField: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    if (state.currentValue == SearchBarValue.Expanded) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                inputField()
                content()
            }
        }
    }
}

@Composable
fun SampleSearchResults(
    onResultClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sampleResults = listOf(
        "Search result 1",
        "Search result 2",
        "Search result 3",
        "Search result 4",
        "Search result 5",
        "Sample query",
        "Example search",
        "Test item",
        "Demo result",
        "Another result"
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleResults) { result ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onResultClick(result) },
                tonalElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                    Text(result)
                }
            }
        }
    }
}

