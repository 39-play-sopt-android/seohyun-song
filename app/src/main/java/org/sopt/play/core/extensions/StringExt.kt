package org.sopt.play.core.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * 날짜 표현 형식을 YYYY.MM.DD 형태로 가공하는 확장 함수
 *
 * ex) "2026-11-25T15:24:56.894136" (UTC) → "2026.11.26" (KST)
 */

private val displayDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

fun String.toDisplayDate(zoneId: ZoneId = ZoneId.systemDefault()): String =
    runCatching {
        val instant = runCatching { Instant.parse(this) }
            .getOrElse { LocalDateTime.parse(this).toInstant(ZoneOffset.UTC) }

        instant.atZone(zoneId).format(displayDateFormatter)
    }.getOrDefault("")
