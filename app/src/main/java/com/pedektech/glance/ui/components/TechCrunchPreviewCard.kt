package com.pedektech.glance.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.pedektech.glance.data.model.LinkPreviewData
import com.pedektech.glance.ui.theme.AppColors
import com.pedektech.glance.ui.theme.AppTypography
import timber.log.Timber

@Composable
fun TechCrunchPreviewCard(
    modifier: Modifier = Modifier,
    previewData: LinkPreviewData?
) {
    Timber.d("Preview data updated: ${previewData?.title}")

    Card(
        modifier = modifier.shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            BrowserMockup(previewData = previewData)

            Spacer(modifier = Modifier.height(20.dp))

            PreviewTitle(previewData?.title)

            Spacer(modifier = Modifier.height(12.dp))

            PreviewDescription(previewData?.description)
        }
    }
}

@Composable
private fun PreviewTitle(title: String?) {
    Text(
        text = title ?: "Glance",
        style = AppTypography.headlineLarge
    )
}

@Composable
private fun PreviewDescription(description: String?) {
    Text(
        text = description ?: "Glance Description",
        style = AppTypography.bodyMedium
    )
}