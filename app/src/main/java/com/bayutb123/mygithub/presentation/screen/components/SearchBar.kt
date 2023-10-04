package com.bayutb123.mygithub.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    Box(modifier = modifier.fillMaxWidth()) {
        TextField(value = text, onValueChange = {
            text = it
        }, modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            label = { Text("Search") },
            trailingIcon = {
                IconButton(onClick = {
                    onSearch(text)
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(text)
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )
    }
}
