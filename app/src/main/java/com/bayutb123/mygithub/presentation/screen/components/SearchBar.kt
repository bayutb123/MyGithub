package com.bayutb123.mygithub.presentation.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp

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
        TextField(
            value = text, onValueChange = {
                text = it
            },
            modifier = modifier.fillMaxWidth().height(56.dp),
            maxLines = 1,
            singleLine = true,
            label = { Text("Search") },
            leadingIcon = {
                IconButton(onClick = {
                    onSearch(text)
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.height(24.dp))
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(text)
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorCursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            shape = CircleShape,
        )
    }
}
