package jp.yamaglo.dic.viewmodel

import android.app.Application
import android.content.Intent
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import jp.yamaglo.dic.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppsListViewModel(application: Application) : AndroidViewModel(application) {

    private val _appsList = MutableLiveData<List<AppInfo>>()
    val appsList: LiveData<List<AppInfo>> = _appsList

    private var job: Job? = null

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

    fun fetchAppsInfo() {
        job = viewModelScope.launch(Dispatchers.IO) {
            getAppsList()
        }
    }

    @WorkerThread
    private fun getAppsList() {
        val packageManager = getApplication<Application>().packageManager

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val activities = packageManager.queryIntentActivities(intent, 0)
        val items = activities.map {
            AppInfo(
                name = it.loadLabel(packageManager).toString(),
                targetSdkVersion = it.activityInfo.applicationInfo.targetSdkVersion,
                minSdkVersion = -1,
                icon = it.loadIcon(packageManager),
                versionName = "???"
            )
        }.sortedByDescending {
            it.targetSdkVersion
        }
        _appsList.postValue(items)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java).newInstance(application)
        }
    }
}
