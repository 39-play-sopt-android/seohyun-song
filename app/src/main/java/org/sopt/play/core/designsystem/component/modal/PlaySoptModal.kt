package org.sopt.play.core.designsystem.component.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PlaySoptModal(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
                .background(
                    color = PlaySoptTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(all = 20.dp),
            content = content,
        )
    }
}
