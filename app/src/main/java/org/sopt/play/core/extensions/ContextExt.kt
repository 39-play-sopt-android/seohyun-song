package org.sopt.play.core.extensions

import android.content.Context
import android.widget.Toast

/**
 * 토스트 메시지를 띄우는 확장 함수
 */
fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
) = Toast.makeText(this, message, duration).show()
