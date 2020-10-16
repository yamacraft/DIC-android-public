package jp.yamaglo.dic.model

data class DeviceInfo(
    val density: Float,
    val densityDpi: Int,
    val scaledDensity: Float,
    val widthPixels: Int,
    val heightPixels: Int,
    val xdpi: Float,
    val ydpi: Float,
    val versionName: String,
    val deviceName: String,
    val manufacturer: String,
    val osVersion: String,
    val apiLevel: String,
    val maxMemory: Long,
    val webViewUserAgent: String
)
