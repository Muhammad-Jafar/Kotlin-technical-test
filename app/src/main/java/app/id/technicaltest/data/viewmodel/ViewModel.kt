package app.id.technicaltest.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import app.id.technicaltest.data.local.PreferenceStore
import app.id.technicaltest.service.ReminderNotificationWorker


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    private val prefStore = PreferenceStore(application)

    fun scheduleReminderNotification(hourOfDay: Int, minute: Int) {
        prefStore.setReminderTime(hourOfDay, minute)
        ReminderNotificationWorker.schedule(app, hourOfDay, minute)
    }

//    fun getReminderTime() = prefStore.getReminderTime()
//
//    fun cancelReminderNotification() {
//        prefStore.cancelReminder()
//        WorkManager.getInstance(app).cancelAllWorkByTag(TAG_REMINDER_WORKER)
//    }
//
//    /**
//     * This sets the default time at the first launch of the app
//     */
//    private fun checkAndSetDefaultReminder() {
//        if (!prefStore.isDefaultReminderSet()) {
//            scheduleReminderNotification(DEFAULT_REMINDER_HOUR, DEFAULT_REMINDER_MINUTE)
//            prefStore.saveDefaultReminderIsSet()
//        }
//    }
}
