package jp.yamaglo.dic.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.yamaglo.dic.databinding.ItemAppBinding
import jp.yamaglo.dic.model.AppInfo

class AppsListAdapter : ListAdapter<AppInfo, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemAppBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as? ViewHolder)?.bind(item)
    }

    private class ViewHolder(
        private val binding: ItemAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(app: AppInfo) {
            binding.appNameTextView.text = app.name
            binding.appTargetSdkVersionTextView.text = "targetSdkVersion: ${app.targetSdkVersion}"
            binding.appTargetSdkVersionCodeTextView.text =
                "Android ${app.getTargetSdkVersionCode()}"
            binding.appLauncherImageView.setImageDrawable(app.icon)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AppInfo>() {
            override fun areItemsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AppInfo, newItem: AppInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
