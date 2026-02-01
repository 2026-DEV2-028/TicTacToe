package com.kata.tictactoe.game.presentation.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun XMark(
    modifier: Modifier = Modifier,
    withAnimation: Boolean = false
) {
    val progress = remember { Animatable(if (withAnimation) 0f else 1f) }

    if (withAnimation) {
        LaunchedEffect(Unit) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        drawLine(
            color = Color.Red,
            start = Offset(0f, 0f),
            end = Offset(
                size.width * progress.value,
                size.height * progress.value
            ),
            strokeWidth = 20f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Red,
            start = Offset(size.width, 0f),
            end = Offset(
                size.width * (1f - progress.value),
                size.height * progress.value
            ),
            strokeWidth = 20f,
            cap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
private fun XMarkPreview() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        XMark(
            modifier = Modifier.padding(12.dp),
            withAnimation = false
        )
    }
}