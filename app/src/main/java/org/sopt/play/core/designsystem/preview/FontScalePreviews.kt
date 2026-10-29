package org.sopt.play.core.designsystem.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Font 1.0x", device = Devices.PHONE, fontScale = 1.0f, showBackground = true)
@Preview(name = "Font 1.5x", device = Devices.PHONE, fontScale = 1.5f, showBackground = true)
@Preview(name = "Font 2.0x", device = Devices.PHONE, fontScale = 2.0f, showBackground = true)
annotation class FontScalePreviews
