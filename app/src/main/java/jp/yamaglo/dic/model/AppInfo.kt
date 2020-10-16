package jp.yamaglo.dic.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val targetSdkVersion: Int,
    val minSdkVersion: Int,
    val icon: Drawable,
    val versionName: String
) {
    fun getTargetSdkVersionCode(): String = when (this.targetSdkVersion) {
        1 -> "1.0"
        2 -> "1.1"
        3 -> "1.5"
        4 -> "1.6"
        5, 6 -> "2.0"
        7 -> "2.1"
        8 -> "2.2"
        9, 10 -> "2.3"
        11 -> "3.0"
        12 -> "3.1"
        13 -> "3.2"
        14 -> "4.0"
        15 -> "4.0.3"
        16 -> "4.1"
        17 -> "4.2"
        18 -> "4.3"
        19 -> "4.4"
        20 -> "4.4W"
        21, 22 -> "5.0"
        23 -> "6.0"
        24 -> "7.0"
        25 -> "7.1"
        26 -> "8.0"
        27 -> "8.1"
        28 -> "9.0"
        29 -> "10"
        30 -> "11"
        else -> "?.?.?"
    }
}
