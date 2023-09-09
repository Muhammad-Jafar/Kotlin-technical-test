package app.id.technicaltest.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import app.id.technicaltest.R
import app.id.technicaltest.base.BaseFragment
import app.id.technicaltest.data.model.Hero
import app.id.technicaltest.data.viewmodel.MainViewModel
import app.id.technicaltest.databinding.MainHomeBinding
import app.id.technicaltest.utils.popupDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<MainHomeBinding>(MainHomeBinding::inflate) {
    private lateinit var auth: FirebaseAuth
    private lateinit var rvHeroes: RecyclerView
    private val viewModel: MainViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        val currentUser = auth.currentUser
        binding.apply {
            if (currentUser != null) {
                emptyState.root.visibility = View.GONE
                rvState.root.visibility = View.VISIBLE
                emailUser.text = currentUser.email
            } else {
                emptyState.root.visibility = View.VISIBLE
                rvState.root.visibility = View.GONE
                emailUser.text = "Belum login"
            }
        }
    }

    override fun renderView(savedInstanceState: Bundle?) {
        hasSignIn()
        with(binding) {
            mainToolbar.apply {
                inflateMenu(R.menu.option_menu)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.logoutButton -> doLogout(auth.currentUser)
                        R.id.reminderButton -> setNotification()
                    }
                    true
                }
            }
            emptyState.directToLogin.setOnClickListener { findNavController().navigate(R.id.action_home_to_login) }
        }
    }

    private fun setNotification() {
        popupDialog(
            "Set notifikasi",
            "Klik OK untuk membuat notifikasi pada waktu saat ini."
        ).setPositiveButton("OKE") { dialog, _ ->
            viewModel.scheduleReminderNotification(7, 30)
            dialog.dismiss()
        }.show()
    }

    private fun doLogout(user: FirebaseUser?) = with(binding) {
        if (user != null) {
            popupDialog(
                "Keluar dari akun",
                "Anda yakin ingin keluar dari akun ?"
            ).setPositiveButton("OKE") { dialog, _ ->
                Firebase.auth.signOut()
                emptyState.root.visibility = View.VISIBLE
                rvState.root.visibility = View.GONE
                Snackbar.make(root, "Anda telah keluar", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.homeFragment)
            }.setNegativeButton("TIDAK JADI") { dialog, _ -> dialog.dismiss() }.show()
        } else Snackbar.make(root, "Anda belum masuk", Snackbar.LENGTH_SHORT).show()
    }

    private fun hasSignIn() {

        val list = ArrayList<Hero>()
        list.addAll(listHeroes)

        val heroAdapter = HeroAdapter(list)
        heroAdapter.submitList(list)

        rvHeroes = binding.rvState.rvHeroes
        rvHeroes.adapter = heroAdapter
    }

    private val listHeroes: ArrayList<Hero>
        get() {
            val dataId = resources.getIntArray(R.array.data_id)
            val dataAvatar = resources.obtainTypedArray(R.array.data_avatar)
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)

            val listHero = ArrayList<Hero>()
            for (i in dataName.indices) {
                val hero = Hero(
                    dataId[i],
                    dataAvatar.getResourceId(i, -1),
                    dataName[i],
                    dataDescription[i],
                )
                listHero.add(hero)
            }
            dataAvatar.recycle()
            return listHero

        }
}
