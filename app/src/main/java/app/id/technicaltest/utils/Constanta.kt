package app.id.technicaltest.utils

import androidx.recyclerview.widget.DiffUtil
import app.id.technicaltest.data.model.Hero

object Constanta {
    /* Diff CALLBACK FOR TRANSACTION ITEM ENTITY*/
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hero>() {
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean = oldItem.id == newItem.id
    }

    const val TAG_REMINDER_WORKER = "TAG REMINDER WORKER"

    const val SHARED_PREF_REMINDER_HOUR = "Waktu menunjukkan Jam"
    const val SHARED_PREF_REMINDER_MINUTE = "Waktu menunjukkan Menit"

    const val DEFAULT_REMINDER_HOUR = 0
    const val DEFAULT_REMINDER_MINUTE = 0
}
