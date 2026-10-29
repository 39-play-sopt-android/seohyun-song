package org.sopt.play.core.extensions

/**
 * 정수를 천 단위 쉼표가 들어간 가격 문자열로 변환
 *
 * ex) 29930 -> "29,930"
 */
fun Int.toPriceFormat(): String = "%,d".format(this)
