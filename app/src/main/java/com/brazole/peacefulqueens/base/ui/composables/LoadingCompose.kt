package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

@Composable
fun LoadingCompose() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_crown)
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = AppTheme.color.surface,
                    shape = CircleShape
                )
                .size(120.dp)
                .padding(24.dp)
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )
        }
    }
}

@Preview
@Composable
private fun LoadingComposePreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.background)
        ) {
            Text(
                text = "Content behind loading",
                modifier = Modifier.align(Alignment.Center)
            )
            LoadingCompose()
        }
    }
}
