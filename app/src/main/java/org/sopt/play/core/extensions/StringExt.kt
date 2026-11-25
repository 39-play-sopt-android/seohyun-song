package org.sopt.play.core.extensions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * 날짜 표현 형식을 YYYY.MM.DD 형태로 가공하는 확장 함수
 *
 * ex) "2026-11-24T15:41:00.318Z" → "2026.11.25"
 */

private val displayDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

fun String.toDisplayDate(zoneId: ZoneId = ZoneId.systemDefault()): String =
    runCatching {
        Instant.parse(this).atZone(zoneId).format(displayDateFormatter)
    }.getOrDefault("")