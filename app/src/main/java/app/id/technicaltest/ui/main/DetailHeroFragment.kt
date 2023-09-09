package app.id.technicaltest.ui.main

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.id.technicaltest.base.BaseFragment
import app.id.technicaltest.databinding.MainHomeListDetailBinding

class DetailHeroFragment :
    BaseFragment<MainHomeListDetailBinding>(MainHomeListDetailBinding::inflate) {
    private val args: DetailHeroFragmentArgs by navArgs()

    override fun renderView(savedInstanceState: Bundle?) {
        with(binding) {
            detailToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            args.itemHero?.let {
                avatarHero.setImageResource(it.avatar)
                nameHero.text = it.name
                descHero.text = it.desc
            }
        }
    }

}
