package jp.yamaglo.dic.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.google.firebase.analytics.FirebaseAnalytics
import jp.yamaglo.dic.BuildConfig
import jp.yamaglo.dic.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
        ActivityAboutBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.appVersionText.text = BuildConfig.VERSION_NAME

        binding.linkYamagloWebButton.setOnClickListener {
            CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
                .launchUrl(this, Uri.parse(URL_WEB_YAMAGLO))
        }

        binding.linkYamagloTwitterButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_TWITTER)))
        }

        binding.linkYamagloStoreButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_STORE)))
        }

        binding.linkSupportYamagloButton.setOnClickListener {
            CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
                .launchUrl(this, Uri.parse(URL_SUPPORT))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val URL_STORE =
            "https://play.google.com/store/apps/dev?id=6492916758018679778"
        private const val URL_TWITTER = "https://twitter.com/yamaglo_yge"
        private const val URL_WEB_YAMAGLO = "https://yamaglo.jp"
        private const val URL_SUPPORT = "https://yamacraft.github.io/grants/"
    }
}
