package com.pedektech.glance.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pedektech.glance.ui.theme.AppColors

@Composable
fun ShareActionButton(
    isPressed: Boolean,
    onPressedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .shadow(
                elevation = if (isPressed) 4.dp else 8.dp,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(AppColors.ShareButtonBlue)
            .clickable {
                onPressedChange(true)
                onClick()
                onPressedChange(false)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share link preview",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}