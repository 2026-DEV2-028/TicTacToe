package com.kata.tictactoe.game.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OMark(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            color = Color.Blue,
            style = Stroke(width = 20f)
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
        OMark(modifier = Modifier.padding(12.dp))
    }
}