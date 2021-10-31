package dev.marshi.jetmemo.ui.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.marshi.jetmemo.R

@Composable
fun Player(
    playerState: PlayerState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onPause: () -> Unit
) {
    Surface {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth()
                    .weight(1f),
                onClick = {
                    if (playerState.isPlay()) {
                        onStart()
                    } else {
                        onPause()
                    }
                }
            ) {
                PlayerIcon(type = playerState.type)
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                onClick = onStop
            ) {
                Text("停止")
            }
        }
    }
}

@Composable
private fun PlayerIcon(type: PlayerState.Type) {
    when (type) {
        PlayerState.Type.PAUSE -> Icon(
            painter = painterResource(id = R.drawable.baseline_pause_24),
            contentDescription = null
        )
        PlayerState.Type.PLAY -> Icon(Icons.Filled.PlayArrow, null)
    }
}

@Preview
@Composable
fun PlayerPreview() {
    Player(
        onStart = {},
        onPause = {},
        onStop = {},
        playerState = PlayerState()
    )
}
