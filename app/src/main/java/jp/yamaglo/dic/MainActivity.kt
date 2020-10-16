package jp.yamaglo.dic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.analytics.FirebaseAnalytics
import jp.yamaglo.dic.databinding.ActivityMainBinding
import jp.yamaglo.dic.ui.AboutActivity
import jp.yamaglo.dic.ui.AppsListActivity
import jp.yamaglo.dic.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(application)
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
        ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }
        supportActionBar?.subtitle = BuildConfig.VERSION_NAME

        val realMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            baseContext.display?.getRealMetrics(realMetrics)
        } else {
            windowManager.defaultDisplay.getRealMetrics(realMetrics)
        }

        viewModel.setDeviceInfo(realMetrics)

        viewModel.deviceInfo.observe(this, {
            binding.deviceNameText.text = "${it.deviceName} (${it.manufacturer})"
            binding.osVersionText.text = "${it.osVersion} (API ${it.apiLevel})"
            binding.densityText.text = "${it.density} px"
            binding.scaleDensityText.text = "${it.scaledDensity} px"
            binding.widthPixelsText.text = "${it.widthPixels} px"
            binding.heightPixelsText.text = "${it.heightPixels} px"
            binding.densityDpiText.text = "${it.densityDpi}"
            binding.xdpiText.text = "${it.xdpi}"
            binding.ydpiText.text = "${it.ydpi}"
            binding.categoryText.text = resources.getString(R.string.dpi_category)
            binding.maxMemoryText.text = "${it.maxMemory} MB"
            binding.webViewUserAgentText.text = it.webViewUserAgent
        })

        binding.shareFab.setOnClickListener {
            val chooser = resources.getString(R.string.chooser_app)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.getDeviceInfoText())
                type = "text/plain"
            }
            startActivityForResult(Intent.createChooser(intent, chooser), 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_transition_app_list -> {
                startActivity(Intent(this, AppsListActivity::class.java))
                true
            }
            R.id.action_transition_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            R.id.action_transition_oss -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
