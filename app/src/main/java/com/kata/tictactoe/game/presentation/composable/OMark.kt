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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OMark(
    modifier: Modifier = Modifier,
    withAnimation: Boolean = false
) {
    val sweepAngle = remember { Animatable(if (withAnimation) 0f else 360f) }

    if (withAnimation) {
        LaunchedEffect(Unit) {
            sweepAngle.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawArc(
            color = Color.Blue,
            startAngle = -90f,
            sweepAngle = sweepAngle.value,
            useCenter = false,
            style = Stroke(
                width = 20f,
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview
@Composable
private fun OMarkPreview() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        OMark(
            modifier = Modifier.padding(12.dp),
            withAnimation = false
        )
    }
}