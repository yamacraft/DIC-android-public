package jp.yamaglo.dic.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.DisplayMetrics
import android.webkit.WebView
import androidx.lifecycle.*
import jp.yamaglo.dic.R
import jp.yamaglo.dic.model.DeviceInfo

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _deviceInfo = MutableLiveData<DeviceInfo>()
    val deviceInfo: LiveData<DeviceInfo> = _deviceInfo

    private val app: Application
        get() = getApplication()

    fun setDeviceInfo(metrics: DisplayMetrics) {
        val versionName = try {
            val info = app.packageManager.getPackageInfo(app.packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "???"
        }

        val maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024
        val deviceInfo = DeviceInfo(
            density = metrics.density,
            densityDpi = metrics.densityDpi,
            scaledDensity = metrics.scaledDensity,
            widthPixels = metrics.widthPixels,
            heightPixels = metrics.heightPixels,
            xdpi = metrics.xdpi,
            ydpi = metrics.ydpi,
            versionName = versionName,
            deviceName = Build.DEVICE,
            manufacturer = Build.MANUFACTURER,
            osVersion = Build.VERSION.RELEASE,
            apiLevel = "${Build.VERSION.SDK_INT}",
            maxMemory = maxMemory,
            webViewUserAgent = WebView(app.applicationContext).settings.userAgentString
        )
        _deviceInfo.value = deviceInfo
    }

    fun getDeviceInfoText(): String {
        return _deviceInfo.value?.let {
            "端末名: ${it.deviceName} (${it.manufacturer})\n" +
                    "搭載OS: ${it.osVersion} (API ${it.apiLevel})\n" +
                    "density: ${it.density} px\n" +
                    "scaledDensity: ${it.scaledDensity} px\n" +
                    "widthPixels: ${it.widthPixels} px\n" +
                    "heightPixels: ${it.heightPixels} px\n" +
                    "densityDpi: ${it.densityDpi}\n" +
                    "xDpi: ${it.xdpi}\n" +
                    "yDpi: ${it.ydpi}\n" +
                    "category: ${app.resources.getString(R.string.dpi_category)}\n" +
                    "ヒープ最大容量(Dalvik VM): ${it.maxMemory} MB\n" +
                    "WebViewのUserAgent: ${it.webViewUserAgent}\n" +
                    "D.I.C.のバージョン: ${it.versionName}"
        }.orEmpty()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}
