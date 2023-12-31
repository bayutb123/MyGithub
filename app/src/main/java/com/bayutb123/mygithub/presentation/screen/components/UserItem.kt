package com.bayutb123.mygithub.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.bayutb123.mygithub.domain.model.User


@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector = Icons.Default.BookmarkAdd,
    user: User,
    onClick: () -> Unit,
    onSaveClick: (User) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape),
            model = user.avatarUrl,
            contentDescription = user.login
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }
        Column(modifier = modifier
            .padding(start = 16.dp)
            .weight(1f)) {
            Text(
                text = user.login,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
        UserListAction(onClick = {
            onSaveClick(user)
        }, actionIcon = actionIcon)
    }
}

@Composable
fun UserListAction(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = actionIcon,
            contentDescription = "Remove from saved",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}