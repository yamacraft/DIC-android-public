package jp.yamaglo.dic

import android.app.Application
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

@Suppress("unused")
class DicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    companion object {
        private class CrashReportingTree : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return
                }

                val crashlytics = FirebaseCrashlytics.getInstance()

                if (priority != Log.ERROR) {
                    crashlytics.log(message)
                    return
                }

                if (t != null) {
                    crashlytics.recordException(t)
                } else if (message.isNotEmpty()) {
                    crashlytics.recordException(Exception(message))
                }
            }
        }
    }
}
