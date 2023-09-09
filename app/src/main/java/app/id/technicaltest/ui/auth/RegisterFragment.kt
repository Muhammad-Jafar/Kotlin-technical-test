package app.id.technicaltest.ui.auth

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import app.id.technicaltest.R
import app.id.technicaltest.base.BaseFragment
import app.id.technicaltest.databinding.MainAuthRegisterBinding
import app.id.technicaltest.utils.FormValidator
import app.id.technicaltest.utils.afterInputStringChanged
import app.id.technicaltest.utils.hideSoftKeyboard
import app.id.technicaltest.utils.isNetworkAvailable
import app.id.technicaltest.utils.loadingDialog
import app.id.technicaltest.utils.popupDialog
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : BaseFragment<MainAuthRegisterBinding>(MainAuthRegisterBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private lateinit var loading: AlertDialog

    override fun renderView(savedInstanceState: Bundle?) {
        with(binding) {
            auth = FirebaseAuth.getInstance()
            loading = loadingDialog()
            loading.dismiss()
            validateForm()
            regisFormLoginButton.setOnClickListener { findNavController().navigate(R.id.action_register_to_login) }
        }
    }

    private fun validateForm() = with(binding) {
        inputEmail.apply {
            afterInputStringChanged { email ->
                if (email != null) {
                    regisFormRegisButton.isEnabled = !FormValidator.isEmailValid(email)
                    inputEmailLayout.error = when {
                        email.isEmpty() -> "Tidak boleh kosong"
                        FormValidator.isEmailValid(email.toString()) -> "Email tidak valid"
                        else -> null
                    }
                }
            }
        }
        inputSandi.apply {
            afterInputStringChanged { password ->
                inputSandiLayout.error = when {
                    password.isNullOrEmpty() -> "Tidak boleh kosong"
                    FormValidator.isPasswordValid(password.toString()) -> "Kata sandi < 5"
                    else -> null
                }
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) doLogin()
                false
            }
        }
        regisFormRegisButton.setOnClickListener { doLogin() }
    }

    private fun doLogin() = with(binding) {
        hideSoftKeyboard(binding.root)
        if (isNetworkAvailable()) {
            val email = inputEmail.text.toString()
            val sandi = inputSandi.text.toString()

            if (email.isEmpty()) inputEmailLayout.error = "Tidak boleh kosong"
            else if (FormValidator.isEmailValid(email)) inputEmailLayout.error = "Email tidak valid"
            else if (sandi.isEmpty()) inputSandiLayout.error = "Tidak boleh kosong"
            else if (FormValidator.isPasswordValid(sandi)) inputSandiLayout.error = "Kata sandi < 5"
            else {
                loading.show()
                auth.createUserWithEmailAndPassword(email, sandi)
                    .addOnCompleteListener {
                        loading.dismiss()
                        if (it.isSuccessful) popupDialog(
                            "Registrasi berhasil",
                            "Terimakasih telah mendaftar"
                        ).setPositiveButton("OKE") { dialog, _ ->
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_register_to_login)
                        }.show()
                    }
                    .addOnFailureListener {
                        loading.dismiss()
                        popupDialog(
                            "Regsitrasi gagal",
                            "Telah terjadi kesalahan, silahkan dicoba lagi"
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
