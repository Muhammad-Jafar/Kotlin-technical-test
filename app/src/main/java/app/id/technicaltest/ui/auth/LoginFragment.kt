package app.id.technicaltest.ui.auth

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import app.id.technicaltest.R
import app.id.technicaltest.base.BaseFragment
import app.id.technicaltest.databinding.MainAuthLoginBinding
import app.id.technicaltest.utils.FormValidator.isEmailValid
import app.id.technicaltest.utils.FormValidator.isPasswordValid
import app.id.technicaltest.utils.afterInputStringChanged
import app.id.technicaltest.utils.hideSoftKeyboard
import app.id.technicaltest.utils.isNetworkAvailable
import app.id.technicaltest.utils.loadingDialog
import app.id.technicaltest.utils.popupDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : BaseFragment<MainAuthLoginBinding>(MainAuthLoginBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private lateinit var loading: AlertDialog

    override fun renderView(savedInstanceState: Bundle?) {
        with(binding) {
            auth = Firebase.auth
            loading = loadingDialog()
            loading.dismiss()
            validateForm()
            loginFormLRegisterButton.setOnClickListener { findNavController().navigate(R.id.action_login_to_register) }
        }
    }

    private fun validateForm() = with(binding) {
        inputEmail.apply {
            afterInputStringChanged { email ->
                if (email != null) {
                    loginFormLoginButton.isEnabled = !isEmailValid(email)
                    inputEmailLayout.error = when {
                        email.isEmpty() -> "Tidak boleh kosong"
                        isEmailValid(email.toString()) -> "Email tidak valid"
                        else -> null
                    }
                }
            }
        }
        inputSandi.apply {
            afterInputStringChanged { sandi ->
                inputSandiLayout.error = when {
                    sandi.isNullOrEmpty() -> "Tidak boleh kosong"
                    isPasswordValid(sandi.toString()) -> "Kata sandi < 5"
                    else -> null
                }
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) doLogin()
                false
            }
        }
        loginFormLoginButton.setOnClickListener { doLogin() }
    }

    private fun doLogin() = with(binding) {
        hideSoftKeyboard(binding.root)
        if (isNetworkAvailable()) {
            val email = inputEmail.text.toString()
            val sandi = inputSandi.text.toString()

            if (email.isEmpty()) inputEmailLayout.error = "Tidak boleh kosong"
            else if (isEmailValid(email)) inputEmailLayout.error = "Email tidak valid"
            else if (sandi.isEmpty()) inputSandiLayout.error = "Tidak boleh kosong"
            else if (isPasswordValid(sandi)) inputSandiLayout.error = "Kata sandi < 5"
            else {
                loading.show()
                auth.signInWithEmailAndPassword(email, sandi)
                    .addOnCompleteListener {
                        loading.dismiss()
                        if (it.isSuccessful) findNavController().navigate(R.id.action_login_to_home)
                    }
                    .addOnFailureListener {
                        loading.dismiss()
                        popupDialog(
                            "Login gagal",
                            it.message.toString()
                        ).setPositiveButton("OKE") { dialog, _ -> dialog.dismiss() }.show()
                    }
            }
        } else {
            loading.dismiss()
            popupDialog(
                "Tidak ada koneksi",
                "Periksa status koneksi anda"
            ).setPositiveButton("OKE") { dialog, _ -> dialog.dismiss() }.show()
        }
    }

}