package com.bayutb123.mygithub.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(value = text, onValueChange = {
            text = it
            onSearch(it)
        }, modifier.fillMaxWidth(), maxLines = 1, label = { Text("Search") })
    }
}
