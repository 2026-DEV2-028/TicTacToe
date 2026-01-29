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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun XMark(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        drawLine(
            color = Color.Red,
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = 20f
        )
        drawLine(
            color = Color.Red,
            start = Offset(size.width, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 20f
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
        XMark(modifier = Modifier.padding(12.dp))
    }
}