package jp.yamaglo.dic.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.analytics.FirebaseAnalytics
import jp.yamaglo.dic.databinding.ActivityAppsListBinding
import jp.yamaglo.dic.viewmodel.AppsListViewModel

class AppsListActivity : AppCompatActivity() {

    private val viewModel: AppsListViewModel by viewModels {
        AppsListViewModel.Factory(application)
    }

    private lateinit var binding: ActivityAppsListBinding
    private lateinit var listAdapter: AppsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
        ActivityAppsListBinding.inflate(layoutInflater).apply {
            binding = this
            setContentView(binding.root)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listAdapter = AppsListAdapter()

        binding.appsListRecyclerView.apply {
            swapAdapter(listAdapter, false)
            val dividerItemDeclaration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDeclaration)
        }

        viewModel.appsList.observe(this) {
            binding.progress.isVisible = false
            listAdapter.submitList(it)
        }

        viewModel.fetchAppsInfo()
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
}
