package app.id.technicaltest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.id.technicaltest.R
import app.id.technicaltest.databinding.DialogLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

/* CUSTOM ALERT DIALOG */
fun Fragment.loadingDialog(): AlertDialog {
    val binding = DialogLoadingBinding.inflate(LayoutInflater.from(requireContext()), null, false)
    return MaterialAlertDialogBuilder(requireContext(), R.style.CustomDialogLoading)
        .setView(binding.root).setCancelable(false).show()
}

/* EDIT TEXT VALIDATION TO STRING */
fun TextInputEditText.afterInputStringChanged(afterTextChanged: (String?) -> Unit) =
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })

/*HIDE SOFT KEYBOARD*/
fun Fragment.hideSoftKeyboard(view: View) {
    (this.context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(view.windowToken, 0)
}

/* CHECK NETWORK STATE */
fun Fragment.isNetworkAvailable(): Boolean {
    if (context == null) return false
    val connectivityManager =
        requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
    }
    return false
}

/* CUSTOM POPUP DIALOG */
fun Fragment.popupDialog(title: String, message: String): MaterialAlertDialogBuilder =
    MaterialAlertDialogBuilder(requireContext()).setTitle(title).setMessage(message)
