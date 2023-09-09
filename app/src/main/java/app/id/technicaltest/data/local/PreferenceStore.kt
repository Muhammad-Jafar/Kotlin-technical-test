package app.id.technicaltest.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import app.id.technicaltest.utils.Constanta.SHARED_PREF_REMINDER_HOUR
import app.id.technicaltest.utils.Constanta.SHARED_PREF_REMINDER_MINUTE

class PreferenceStore(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("transactions_shared_pref", Context.MODE_PRIVATE)

    fun getReminderTime(): Pair<Int, Int> = Pair(
        sharedPref.getInt(SHARED_PREF_REMINDER_HOUR, 22),
        sharedPref.getInt(SHARED_PREF_REMINDER_MINUTE, 0)
    )

    fun setReminderTime(hour: Int, minute: Int) = sharedPref.edit {
        putInt(SHARED_PREF_REMINDER_HOUR, hour)
        putInt(SHARED_PREF_REMINDER_MINUTE, minute)
    }

    fun cancelReminder() = sharedPref.edit {
        putInt(SHARED_PREF_REMINDER_HOUR, -1)
        putInt(SHARED_PREF_REMINDER_MINUTE, -1)
    }

    fun isDefaultReminderSet() = sharedPref.getBoolean("is_default_reminder_set", false)

    fun saveDefaultReminderIsSet() = sharedPref.edit { putBoolean("is_default_reminder_set", true) }
}
