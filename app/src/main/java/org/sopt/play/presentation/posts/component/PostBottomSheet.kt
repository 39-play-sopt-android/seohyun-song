package org.sopt.play.presentation.posts.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.bottomsheet.PlaySoptBottomSheet
import org.sopt.play.core.designsystem.component.button.PlaySoptButton
import org.sopt.play.core.designsystem.component.textfield.PlaySoptTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.core.designsystem.type.PlaySoptButtonType

private const val TITLE_MAX_LENGTH = 20
private const val CONTENT_MAX_LENGTH = 1000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBottomSheet(
    titleState: TextFieldState,
    contentState: TextFieldState,
    isCompleteEnabled: Boolean,
    onCompleteClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaySoptBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        PlaySoptTextField(
            label = "제목",
            state = titleState,
            placeholder = "제목을 입력하세요.",
            inputTransformation = InputTransformation.maxLength(TITLE_MAX_LENGTH),
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlaySoptTextField(
            label = "본문",
            state = contentState,
            placeholder = "본문을 입력하세요.",
            inputTransformation = InputTransformation.maxLength(CONTENT_MAX_LENGTH),
        )

        Spacer(modifier = Modifier.height(20.dp))

        PlaySoptButton(
            text = "완료",
            onClick = onCompleteClick,
            modifier = Modifier.fillMaxWidth(),
            type = if (isCompleteEnabled) PlaySoptButtonType.PRIMARY else PlaySoptButtonType.DISABLED,
        )
    }
}

@Preview(name = "초기 상태", showBackground = true)
@Composable
private fun PostBottomSheetEmptyPreview() {
    PlaySoptTheme {
        PostBottomSheet(
            titleState = rememberTextFieldState(),
            contentState = rememberTextFieldState(),
            isCompleteEnabled = false,
            onCompleteClick = {},
            onDismissRequest = {},
        )
    }
}

@Preview(name = "입력 중", showBackground = true)
@Composable
private fun PostBottomSheetFilledPreview() {
    PlaySoptTheme {
        PostBottomSheet(
            titleState = rememberTextFieldState("제목은 최대 20자 글자 수 제한"),
            contentState = rememberTextFieldState("본문은 최대 1000자 글자 수 제한"),
            isCompleteEnabled = true,
            onCompleteClick = {},
            onDismissRequest = {},
        )
    }
}
